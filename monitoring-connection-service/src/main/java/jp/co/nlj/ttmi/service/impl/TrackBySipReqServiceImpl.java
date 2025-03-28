package jp.co.nlj.ttmi.service.impl;

import static java.util.Map.entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.AppConstant;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import com.next.logistic.util.ObjectMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Map.Entry;
import jp.co.nlj.ttmi.configuration.OAuth2TTMIConfig;
import jp.co.nlj.ttmi.constant.APIConstant;
import jp.co.nlj.ttmi.constant.MessageConstant;
import jp.co.nlj.ttmi.constant.MessageConstant.APIResponses;
import jp.co.nlj.ttmi.constant.ParamConstant.DefaultValue;
import jp.co.nlj.ttmi.domain.TrackBySipReq;
import jp.co.nlj.ttmi.dto.commonBody.request.CommonRequestDTO;
import jp.co.nlj.ttmi.dto.response.ResponseAPI;
import jp.co.nlj.ttmi.dto.response.TrackBySipResponse;
import jp.co.nlj.ttmi.dto.trackBySip.TrspSrvcDTO;
import jp.co.nlj.ttmi.dto.trackBySip.request.DataChannelTrackBySipReqDTO;
import jp.co.nlj.ttmi.dto.trackBySip.request.TTMITrackBySipRequestDTO;
import jp.co.nlj.ttmi.dto.trackBySip.request.TrackBySipReqDTO;
import jp.co.nlj.ttmi.mapper.DateTimeMapper;
import jp.co.nlj.ttmi.mapper.TTMIReqMapper;
import jp.co.nlj.ttmi.repository.TrackBySipReqRepository;
import jp.co.nlj.ttmi.service.TokenTTMIService;
import jp.co.nlj.ttmi.service.TrackBySipReqService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 運送SIPリクエストサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TrackBySipReqServiceImpl implements TrackBySipReqService {

    private final RestTemplate restTemplate;
    private final TrackBySipReqRepository trackBySipReqRepository;
    private final NljUrlProperties urlProperties;
    private final NljAuthProperties authProperties;
    private final TokenTTMIService tokenTTMIService;
    private final OAuth2TTMIConfig oAuth2TTMIConfig;
    private final TTMIReqMapper ttmiReqMapper;
    private final ObjectMapper objectMapper;

    private final ObjectMapper mapper = ObjectMapperUtil.getInstance();
    private final Logger logger = LoggerFactory.getLogger(TrackBySipReqServiceImpl.class);

    /**
     * 運送SIPデータチャンネル更新。<BR>
     *
     * @param httpServletRequest HttpServletRequest
     * @param commonRequestDTO   CommonRequestDTO
     * @return ResponseAPI
     */
    @Override
    @Transactional
    public ResponseAPI updateTrackByShipDataChannel(HttpServletRequest httpServletRequest,
        CommonRequestDTO commonRequestDTO) {
        DataChannelTrackBySipReqDTO dataChannelTrackBySipReqDTO = convertRequestAttribute(commonRequestDTO);

        TrackBySipReqDTO trackBySipReqDTO = new TrackBySipReqDTO();

        if (dataChannelTrackBySipReqDTO != null) {
            trackBySipReqDTO.setPost(dataChannelTrackBySipReqDTO.isCreate());
            trackBySipReqDTO.setTrspIsr(dataChannelTrackBySipReqDTO.getTrspIsr());
            trackBySipReqDTO.setShipFromPrty(dataChannelTrackBySipReqDTO.getShipFromPrty());
            trackBySipReqDTO.setShipToPrty(dataChannelTrackBySipReqDTO.getShipToPrty());
            trackBySipReqDTO.setLogsSrvcPrv(dataChannelTrackBySipReqDTO.getLogsSrvcPrv());

            TrspSrvcDTO trspSrvcDTO = getTrspSrvcDTO(dataChannelTrackBySipReqDTO);
            trackBySipReqDTO.setTrspSrvc(trspSrvcDTO);
        }
        return updateTrackByShipReq(httpServletRequest, trackBySipReqDTO);
    }

    /**
     * 運送SIPリクエスト更新。<BR>
     *
     * @param httpServletRequest HttpServletRequest
     * @param trackBySipReqDTO   TrackBySipReqDTO
     * @return ResponseAPI
     */
    @Override
    @Transactional
    public ResponseAPI updateTrackByShipReq(HttpServletRequest httpServletRequest, TrackBySipReqDTO trackBySipReqDTO) {
        if (trackBySipReqDTO == null) {
            NextWebUtil.throwCustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "soa.code.resources_err_001"
            );
        }
        TrackBySipReq trackBySipReq = new TrackBySipReq();
        // Check if the arrival date is before the departure date
        if (trackBySipReqDTO.getTrspSrvc() != null) {
            LocalTime avbFromTimeOfCllTime = DateTimeMapper.stringToLocalTime(
                trackBySipReqDTO.getTrspSrvc().getAvbFromTimeOfCllTime());
            LocalTime apedArrToTimePrfmDttm = DateTimeMapper.stringToLocalTime(
                trackBySipReqDTO.getTrspSrvc().getApedArrToTimePrfmDttm());
            LocalDate avbDateCllDate = DateTimeMapper.stringToLocalDate(
                trackBySipReqDTO.getTrspSrvc().getAvbDateCllDate());
            LocalDate apedArrToDate = DateTimeMapper.stringToLocalDate(
                trackBySipReqDTO.getTrspSrvc().getApedArrToDate());

            if (avbFromTimeOfCllTime != null && apedArrToTimePrfmDttm != null && avbDateCllDate != null
                && apedArrToDate != null) {
                if (apedArrToTimePrfmDttm.isBefore(avbFromTimeOfCllTime) && !apedArrToDate.isAfter(avbDateCllDate)) {
                    trackBySipReqDTO.getTrspSrvc()
                        .setApedArrToDate(DateTimeMapper.dateToString(apedArrToDate.plusDays(1)));
                }
            }
        }
        String mapObject = mapRequestObjectToString(trackBySipReqDTO);
        trackBySipReq.setRequest(mapObject);
        trackBySipReqRepository.save(trackBySipReq);
        HttpHeaders headers = setHeader();
        TTMITrackBySipRequestDTO ttmiRequest = ttmiReqMapper.toTTMIRequest(trackBySipReqDTO);
        //TO-DO
        //Set value default for ship_from_prty_brnc_off_id and ship_to_prty_brnc_off_id
        ttmiRequest.getShipFromPrty()
            .setShipFromPrtyBrncOffId(BaseUtil.getGlnFrom(ttmiRequest.getShipFromPrty().getShipFromPrtyBrncOffId()));
        ttmiRequest.getShipToPrty()
            .setShipToPrtyBrncOffId(BaseUtil.getGlnTo(ttmiRequest.getShipToPrty().getShipToPrtyBrncOffId()));
        ttmiRequest.getTrspSrvc().setCarLicensePltNumId(DefaultValue.CAR_LICENSE_PLT_NUM_ID);

        HttpEntity<TTMITrackBySipRequestDTO> entity = new HttpEntity<>(ttmiRequest, headers);
        ResponseEntity<String> ttmiResponse;
        ResponseAPI responseAPI = new ResponseAPI();
        if (!urlProperties.isMockData()) {
            try {
                HttpMethod httpMethod = trackBySipReqDTO.isPost() ? HttpMethod.POST : HttpMethod.PUT;
                ttmiResponse = restTemplate.exchange(urlProperties.getTrackBySipTtmi(),
                    httpMethod, entity, String.class);
                logger.info("API TrackByShipReq response: {}", ttmiResponse);
                mapResponse(responseAPI, ttmiResponse.getStatusCode());
                if (ttmiResponse.getBody() != null) {
                    TrackBySipResponse trackBySipResponse = objectMapper.readValue(ttmiResponse.getBody(),
                        TrackBySipResponse.class);
                    responseAPI.setId(trackBySipResponse.getId());
                }
            } catch (HttpClientErrorException e) {
                responseAPI.setMessage(e.getMessage());
                responseAPI = handleErrorMessage(e.getStatusText());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            ttmiResponse = ResponseEntity.ok().build();
            mapResponse(responseAPI, ttmiResponse.getStatusCode());
            responseAPI.setId("mock-id-123456");
        }
        return responseAPI;
    }

    /**
     * ヘッダー作成。<BR>
     *
     * @return HttpHeaders
     */
    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, "Bearer " + tokenTTMIService.getValidToken());
        headers.set(AppConstant.TENANT_ID, oAuth2TTMIConfig.getTenantId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * レスポンスマッピング。<BR>
     *
     * @param response   ResponseAPI
     * @param statusCode HttpStatusCode
     */
    public static void mapResponse(ResponseAPI response, HttpStatusCode statusCode) {
        Map<String, String> codeMessageMap = STATUS_MAP.get(statusCode);
        if (codeMessageMap != null && !codeMessageMap.isEmpty()) {
            Entry<String, String> entry = codeMessageMap.entrySet().iterator().next();
            response.setCode(Integer.parseInt(entry.getKey()));
            response.setMessage(entry.getValue());
        }
    }

    /**
     * エラーメッセージハンドリング。<BR>
     *
     * @param bodyString ボディ文字列
     * @return ResponseAPI
     */
    public ResponseAPI handleErrorMessage(String bodyString) {
        ResponseAPI response = new ResponseAPI();
        if (!BaseUtil.isNull(bodyString)) {
            try {
                JsonNode bodyError = mapper.readValue(bodyString, JsonNode.class);
                int code = bodyError.get("error").get("code").asInt();
                String message = bodyError.get("error").get("message").asText();
                response.setCode(code);
                response.setMessage(message);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
        }
        return response;
    }

    /**
     * ステータスマップ。<BR>
     *
     * @author Next Logistics Japan
     */
    private static final Map<HttpStatus, Map<String, String>> STATUS_MAP = Map.ofEntries(
        entry(HttpStatus.CREATED,
            Map.of(APIResponses.SUCCESS_CODE, APIResponses.SUCCESS_MSG)
        ),
        entry(HttpStatus.OK,
            Map.of(APIResponses.SUCCESS_CODE, APIResponses.SUCCESS_MSG)
        ),
        entry(HttpStatus.BAD_REQUEST,
            Map.of(APIResponses.BAD_REQUEST_CODE, APIResponses.BAD_REQUEST_MSG)
        ),
        entry(HttpStatus.UNAUTHORIZED,
            Map.of(APIResponses.UNAUTHORIZED_CODE, APIResponses.UNAUTHORIZED_MSG)
        ),
        entry(HttpStatus.FORBIDDEN,
            Map.of(APIResponses.FORBIDDEN_CODE, APIResponses.FORBIDDEN_MSG)
        ),
        entry(HttpStatus.NOT_FOUND,
            Map.of(APIResponses.NOT_FOUND_CODE, APIResponses.NOT_FOUND_MSG)
        ),
        entry(HttpStatus.INTERNAL_SERVER_ERROR,
            Map.of(APIResponses.ERROR_CODE, APIResponses.SERVER_ERROR_MSG)
        )
    );

    /**
     * リクエストオブジェクトをJSON文字列にマッピング。<BR>
     *
     * @param request リクエストオブジェクト
     * @return JSON文字列
     */
    private String mapRequestObjectToString(Object request) {
        if (request == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing request object to JSON string", e);
        }
    }

    /**
     * 運送サービスDTO取得。<BR>
     *
     * @param dataChannelTrackBySipReqDTO DataChannelTrackBySipReqDTO
     * @return TrspSrvcDTO
     */
    private static TrspSrvcDTO getTrspSrvcDTO(DataChannelTrackBySipReqDTO dataChannelTrackBySipReqDTO) {
        TrspSrvcDTO trspSrvcDTO = new TrspSrvcDTO();
        if (dataChannelTrackBySipReqDTO.getTrspSrvc() != null) {
            trspSrvcDTO.setCarLicensePltNumId(dataChannelTrackBySipReqDTO.getMhReservationId());
            trspSrvcDTO.setAvbDateCllDate(dataChannelTrackBySipReqDTO.getTrspSrvc().getDsedCllFromDate());
            trspSrvcDTO.setAvbFromTimeOfCllTime(dataChannelTrackBySipReqDTO.getTrspSrvc().getDsedCllFromTime());
            trspSrvcDTO.setApedArrToDate(dataChannelTrackBySipReqDTO.getTrspSrvc().getApedArrFromDate());
            trspSrvcDTO.setApedArrToTimePrfmDttm(
                dataChannelTrackBySipReqDTO.getTrspSrvc().getApedArrFromTimePrfmDttm());
        }
        return trspSrvcDTO;
    }

    /**
     * リクエスト属性をDataChannelTrackBySipReqDTOに変換。<BR>
     *
     * @param commonRequestDTO CommonRequestDTO
     * @return DataChannelTrackBySipReqDTO
     */
    private DataChannelTrackBySipReqDTO convertRequestAttribute(CommonRequestDTO commonRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.convertValue(commonRequestDTO.getAttribute(), DataChannelTrackBySipReqDTO.class);
        } catch (Exception e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
            return null;
        }
    }
}
