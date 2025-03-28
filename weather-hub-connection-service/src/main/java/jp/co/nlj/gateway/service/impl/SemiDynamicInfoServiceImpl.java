package jp.co.nlj.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.ObjectMapperUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.domain.RoadInfo;
import jp.co.nlj.gateway.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import jp.co.nlj.gateway.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;
import jp.co.nlj.gateway.repository.RoadInfoRepository;
import jp.co.nlj.gateway.service.SemiDynamicInfoService;
import jp.co.nlj.gateway.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <PRE>
 * SemiDynamicInfoServiceImplクラスは、セミダイナミック情報サービス実装クラスを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
public class SemiDynamicInfoServiceImpl implements SemiDynamicInfoService {

    private static final ObjectMapper objectMapper = ObjectMapperUtil.getInstance();
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NljUrlProperties nljUrlProperties;
    @Autowired
    private RoadInfoRepository roadInfoRepository;
    @Resource
    private UserContext userContext;

    @Autowired
    private TokenCacheService tokenCacheService;

    /**
     * セミダイナミック情報を取得する。
     *
     * @param semiDynamicInfoRequestDTO セミダイナミック情報リクエストDTO
     * @param httpServletRequest        HTTPリクエスト
     * @return セミダイナミック情報レスポンスDTO
     */
    @Transactional
    @Override
    public SemiDynamicInfoResponseDTO getSemiDynamicInfo(SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO,
        HttpServletRequest httpServletRequest) {
        log.info("Start getSemiDynamicInfo");
        if (nljUrlProperties.isMockData()) {
            String responseStr = mockDataResponseJson();
            SemiDynamicInfoResponseDTO response;
            try {
                response = objectMapper.readValue(responseStr, SemiDynamicInfoResponseDTO.class);
            } catch (JsonProcessingException e) {
                throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                    SoaResponsePool.get("soa.code.resources_err_001")));
            }
            RoadInfo roadInfo = RoadInfo.builder()
                .targetData(semiDynamicInfoRequestDTO.getRequestInfo().get(0).getTargetData())
                .responseData(responseStr).companyId(userContext.getUser().getCompanyId()).build();
            roadInfoRepository.save(roadInfo);
            return response;
        } else {
            try {
                String jsonData = objectMapper.writeValueAsString(semiDynamicInfoRequestDTO);

                String url = UriComponentsBuilder.fromHttpUrl(nljUrlProperties.getSemiDynamicInfo())
                    .queryParam(APIConstant.SemiDynamicInfo.GET_SEMI_DYNAMIC_INFO_PARAM, jsonData).toUriString();

                HttpHeaders headers = setHeader(httpServletRequest);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                log.info("Request: " + jsonData);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    String.class);

                log.info("Response: " + response);

                if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                    SemiDynamicInfoResponseDTO semiDynamicInfoResponseDTO = objectMapper.readValue(response.getBody(),
                        SemiDynamicInfoResponseDTO.class);

                    log.info("Success getSemiDynamicInfo");
                    return semiDynamicInfoResponseDTO;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                    SoaResponsePool.get("soa.code.resources_err_001")));
            }
        }
        return null;

    }

    /**
     * モックデータのレスポンスJSONを取得する。
     *
     * @return モックデータのレスポンスJSON
     */
    private String mockDataResponseJson() {
        return "{\"dataModelType\":\"test1\",\"attribute\":{\"result\":0," +
            "\"targetTime\":\"2024\\/12\\/18 14:00:00\",\"level1\":[{\"spatialID\":\"15\\/0\\/29019\\/12962\"," +
            "\"bbox\":[138.812255859375,35.14686290675629,138.8232421875,35.15584570226543]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.8," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:33\"}},{\"spatialID\":\"15\\/0\\/29018\\/12962\",\"bbox\":[138" +
            ".80126953125,35.14686290675629,138.812255859375,35.15584570226543]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.6," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:33\"}},{\"spatialID\":\"15\\/0\\/29017\\/12961\",\"bbox\":[138" +
            ".790283203125,35.15584570226543,138.80126953125,35.164827506050266]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":10.7," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:32\"}},{\"spatialID\":\"15\\/0\\/29017\\/12962\",\"bbox\":[138" +
            ".790283203125,35.14686290675629,138.80126953125,35.15584570226543]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.6," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:33\"}},{\"spatialID\":\"15\\/0\\/29016\\/12962\",\"bbox\":[138" +
            ".779296875,35.14686290675629,138.790283203125,35.15584570226543]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":12.1," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:32\"}},{\"spatialID\":\"15\\/0\\/29016\\/12961\",\"bbox\":[138" +
            ".779296875,35.15584570226543,138.790283203125,35.164827506050266]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.3," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:32\"}},{\"spatialID\":\"15\\/0\\/29015\\/12961\",\"bbox\":[138" +
            ".768310546875,35.15584570226543,138.779296875,35.164827506050266]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.6," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:32\"}},{\"spatialID\":\"15\\/0\\/29013\\/12961\",\"bbox\":[138" +
            ".746337890625,35.15584570226543,138.75732421875,35.164827506050266]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":12.1," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:31\"}},{\"spatialID\":\"15\\/0\\/29014\\/12961\",\"bbox\":[138" +
            ".75732421875,35.15584570226543,138.768310546875,35.164827506050266]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.6," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:32\"}},{\"spatialID\":\"15\\/0\\/29014\\/12960\",\"bbox\":[138" +
            ".75732421875,35.164827506050266,138.768310546875,35.173808317999594]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":10.7," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:31\"}},{\"spatialID\":\"15\\/0\\/29013\\/12960\",\"bbox\":[138" +
            ".746337890625,35.164827506050266,138.75732421875,35.173808317999594]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":11.2," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:31\"}},{\"spatialID\":\"15\\/0\\/29012\\/12960\",\"bbox\":[138" +
            ".7353515625,35.164827506050266,138.746337890625,35.173808317999594]," +
            "\"weatherInfo\":{\"dateTime\":\"2024\\/12\\/18 14:00:00\",\"humidity\":65535.0,\"temperature\":12.0," +
            "\"weather\":\"65535.0\",\"windDirection\":65535.0,\"windSpeed\":65535.0,\"rainfall\":0.0," +
            "\"updatedAt\":\"2024\\/12\\/18 14:25:31\"}}]}}";
    }

    /**
     * HTTPリクエストのヘッダーを設定する。
     *
     * @param httpServletRequest HTTPリクエスト
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        //get access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String accessToken = APIConstant.BEARER;
        try {
            accessToken = accessToken + tokenCacheService.getCacheToken();
        } catch (Exception e) {
            log.error("SemiDynamicInfoServiceImpl Get accessToken ERROR: {}", e.getMessage());
        }
        headers.set(APIConstant.API_KEY, nljUrlProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, accessToken);
        return headers;
    }
}
