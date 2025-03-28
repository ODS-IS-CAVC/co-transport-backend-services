package jp.co.nlj.gateway.service.impl;

import static jp.co.nlj.gateway.constant.ParamConstant.CAR_LICENSE_PLT_NUM_ID;
import static jp.co.nlj.gateway.constant.ParamConstant.DATA_MODEL_TYPE;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.constant.ParamConstant;
import jp.co.nlj.gateway.dto.trackBySip.DataChannelTrspSrvcDTO;
import jp.co.nlj.gateway.dto.trackBySip.request.AttributeRequestDTO;
import jp.co.nlj.gateway.dto.trackBySip.request.DataChannelRequestDTO;
import jp.co.nlj.gateway.dto.trackBySip.request.TrackBySipRequestDTO;
import jp.co.nlj.gateway.dto.trackBySip.response.RestTemplateApiResponse;
import jp.co.nlj.gateway.service.TokenCacheService;
import jp.co.nlj.gateway.service.TrackBySipService;
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
 * TrackBySipServiceImplクラスは、トラックバイSIPサービス実装クラスを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Service
public class TrackBySipServiceImpl implements TrackBySipService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NljUrlProperties urlProperties;

    @Autowired
    private TokenCacheService tokenCacheService;

    /**
     * トラックバイSIPを作成する。
     *
     * @param httpServletRequest   HTTPリクエスト
     * @param trackBySipRequestDTO トラックバイSIPリクエストDTO
     * @return RESTテンプレートAPIレスポンス
     */
    @Override
    public RestTemplateApiResponse createTrackBySIP(HttpServletRequest httpServletRequest,
        TrackBySipRequestDTO trackBySipRequestDTO) {
        return dataChannelUpdateTrackBySIP(httpServletRequest, trackBySipRequestDTO);
    }

    /**
     * トラックバイSIPを更新する。
     *
     * @param httpServletRequest   HTTPリクエスト
     * @param trackBySipRequestDTO トラックバイSIPリクエストDTO
     * @return RESTテンプレートAPIレスポンス
     */
    @Override
    public RestTemplateApiResponse updateTrackBySIP(HttpServletRequest httpServletRequest,
        TrackBySipRequestDTO trackBySipRequestDTO) {
        return dataChannelUpdateTrackBySIP(httpServletRequest, trackBySipRequestDTO);
    }

    /**
     * データチャンネルを更新するトラックバイSIPを処理する。
     *
     * @param httpServletRequest   HTTPリクエスト
     * @param trackBySipRequestDTO トラックバイSIPリクエストDTO
     * @return RESTテンプレートAPIレスポンス
     */
    private RestTemplateApiResponse dataChannelUpdateTrackBySIP(HttpServletRequest httpServletRequest,
        TrackBySipRequestDTO trackBySipRequestDTO) {
        HttpHeaders headers = setHeader(httpServletRequest);

        DataChannelRequestDTO dataChannelRequestDTO = new DataChannelRequestDTO();
        AttributeRequestDTO attributeRequestDTO = new AttributeRequestDTO();

        attributeRequestDTO.setCreate(trackBySipRequestDTO.isPost());
        attributeRequestDTO.setTrspIsr(trackBySipRequestDTO.getTrspIsr());

        attributeRequestDTO.setLogsSrvcPrv(trackBySipRequestDTO.getLogsSrvcPrv());
        attributeRequestDTO.setShipFromPrty(trackBySipRequestDTO.getShipFromPrty());
        attributeRequestDTO.setShipToPrty(trackBySipRequestDTO.getShipToPrty());

        DataChannelTrspSrvcDTO dataChannelTrspSrvcDTO = new DataChannelTrspSrvcDTO();
        if (trackBySipRequestDTO.getTrspSrvc() != null) {
            if (trackBySipRequestDTO.getTrspSrvc().getCarLicensePltNumId() != null) {
                attributeRequestDTO.setMhReservationId(trackBySipRequestDTO.getTrspSrvc().getCarLicensePltNumId());
            } else {
                attributeRequestDTO.setMhReservationId(CAR_LICENSE_PLT_NUM_ID);
            }
            dataChannelTrspSrvcDTO.setApedArrFromTimePrfmDttm(
                trackBySipRequestDTO.getTrspSrvc().getApedArrToTimePrfmDttm());
            dataChannelTrspSrvcDTO.setApedArrFromDate(trackBySipRequestDTO.getTrspSrvc().getApedArrToDate());
            dataChannelTrspSrvcDTO.setDsedCllFromDate(trackBySipRequestDTO.getTrspSrvc().getAvbDateCllDate());
            dataChannelTrspSrvcDTO.setDsedCllFromTime(trackBySipRequestDTO.getTrspSrvc().getAvbFromTimeOfCllTime());
        }
        attributeRequestDTO.setTrspSrvc(dataChannelTrspSrvcDTO);

        dataChannelRequestDTO.setDataModelType(DATA_MODEL_TYPE);
        dataChannelRequestDTO.setAttribute(attributeRequestDTO);

        HttpEntity<DataChannelRequestDTO> request = new HttpEntity<>(dataChannelRequestDTO, headers);
        try {
            ResponseEntity<RestTemplateApiResponse> responseEntity = restTemplate.exchange(
                urlProperties.getTrackBySipGateway(), HttpMethod.PUT, request, RestTemplateApiResponse.class);
            log.info("TrackBySIP Response: {}", responseEntity.getBody());
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR, SoaResponsePool.get("soa.code.resources_err_001")));
        }
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
            log.error("TrackBySipServiceImpl Get accessToken ERROR: {}", e.getMessage());
        }

        headers.set(APIConstant.API_KEY, urlProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, accessToken);
        headers.set(ParamConstant.X_TENANT_ID_KEY, UUID.randomUUID().toString());

        return headers;
    }
}
