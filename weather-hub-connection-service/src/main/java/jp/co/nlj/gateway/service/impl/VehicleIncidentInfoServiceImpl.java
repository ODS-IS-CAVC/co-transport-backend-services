package jp.co.nlj.gateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.ObjectMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.dto.vehicleIncidentInfo.request.VehicleIncidentInfoRequestDTO;
import jp.co.nlj.gateway.service.TokenCacheService;
import jp.co.nlj.gateway.service.VehicleIncidentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * VehicleIncidentInfoServiceImplクラスは、車両事故情報サービス実装クラスを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Service
public class VehicleIncidentInfoServiceImpl implements VehicleIncidentInfoService {

    private static final ObjectMapper objectMapper = ObjectMapperUtil.getInstance();
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NljUrlProperties urlProperties;
    @Autowired
    private TokenCacheService tokenCacheService;

    /**
     * 車両事故情報を登録するメソッド。
     *
     * @param vehicleIncidentInfo 車両事故情報のDTO
     * @param httpServletRequest  HTTPリクエスト
     */
    @Override
    public void registerVehicleIncidentInfo(VehicleIncidentInfoRequestDTO vehicleIncidentInfo,
        HttpServletRequest httpServletRequest) {
        if (!urlProperties.isMockData()) {
            try {
                String bodyStr = objectMapper.writeValueAsString(vehicleIncidentInfo);
                HttpHeaders headers = setHeader();
                HttpEntity<String> httpEntity = new HttpEntity<>(bodyStr, headers);
                ResponseEntity<Void> response = restTemplate.exchange(urlProperties.getIncidentInfo(), HttpMethod.PUT,
                    httpEntity, Void.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                        SoaResponsePool.get("soa.code.resources_err_001")));
                }
            } catch (Exception e) {
                throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                    SoaResponsePool.get("soa.code.resources_err_001")));
            }
        }
    }

    /**
     * HTTPヘッダーを設定するメソッド。
     *
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader() {
        //get access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String accessToken = APIConstant.BEARER;
        try {
            accessToken = accessToken + tokenCacheService.getCacheToken();
        } catch (Exception e) {
            log.error("VehicleIncidentInfoServiceImpl Get accessToken ERROR: {}", e.getMessage());
        }

        headers.set(APIConstant.API_KEY, urlProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, accessToken);
        return headers;
    }
}
