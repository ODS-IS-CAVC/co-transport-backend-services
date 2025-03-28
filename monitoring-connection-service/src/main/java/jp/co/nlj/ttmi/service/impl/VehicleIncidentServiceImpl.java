package jp.co.nlj.ttmi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import jp.co.nlj.ttmi.constant.APIConstant;
import jp.co.nlj.ttmi.constant.MessageConstant;
import jp.co.nlj.ttmi.constant.MessageConstant.Validate;
import jp.co.nlj.ttmi.constant.ParamConstant;
import jp.co.nlj.ttmi.domain.IncidentInfo;
import jp.co.nlj.ttmi.dto.commonBody.request.CommonRequestDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspIncidentDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspSrvcYamatoDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.request.TTMIVehicleIncidentInfoRequestDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.request.TTMIVehicleIncidentInfoRequestYamatoDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.request.VehicleIncidentInfoRequestDTO;
import jp.co.nlj.ttmi.repository.IncidentRepository;
import jp.co.nlj.ttmi.repository.VehicleDiagramItemTrackingRepository;
import jp.co.nlj.ttmi.service.TokenDataChannelCacheService;
import jp.co.nlj.ttmi.service.VehicleIncidentService;
import lombok.RequiredArgsConstructor;
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

/**
 * <PRE>
 * 車両事故サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleIncidentServiceImpl implements VehicleIncidentService {

    private final RestTemplate restTemplate;
    private final NljUrlProperties urlProperties;

    private final IncidentRepository incidentRepository;
    private final VehicleDiagramItemTrackingRepository vehicleDiagramItemTrackingRepository;
    private final ObjectMapper objectMapper;
    private final ValidateUtil validateUtil;

    @Resource(name = "userContext")
    private final UserContext userContext;

    @Autowired
    private TokenDataChannelCacheService tokenCacheService;

    @Autowired
    private NljAuthProperties authProperties;

    /**
     * 車両事故登録。<BR>
     *
     * @param servletRequest                    HttpServletRequest
     * @param ttmiVehicleIncidentInfoRequestDTO TTMIVehicleIncidentInfoRequestDTO
     */
    @Override
    @Transactional
    public void registerVehicleIncident(HttpServletRequest servletRequest,
        TTMIVehicleIncidentInfoRequestDTO ttmiVehicleIncidentInfoRequestDTO) {
        String trspNormalAbnmClsSrvcRqrmCd = ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc()
            .getTrspNormalAbnmClsSrvcRqrmCd();
        if (trspNormalAbnmClsSrvcRqrmCd.equals("2")) {
            if ((ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident() == null ||
                isEmpty(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident())) &&
                (ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspDelay() == null ||
                    isEmpty(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspDelay())) &&
                (ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop() == null ||
                    isEmpty(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop()))) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_NOT_NULL),
                        "trsp_incident or trsp_delay or trsp_vehicle_stop")
                );
            }
        }
        boolean isIncident = false;
        String incidentCategory = null;
        String incidentType = null;
        String trspIncidentGs1Id = null;
        if (ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc() != null
            && ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident() != null) {
            incidentCategory = String.valueOf(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().
                getTrspIncident().getTrspIncidentCategory());
            trspIncidentGs1Id = ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc()
                .getTrspIncident().getTrspIncidentGs1Id();
            incidentType = ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc()
                .getTrspIncident().getTrspIncidentType();
            isIncident = true;
            if (!BaseUtil.isNull(incidentCategory) && !ParamConstant.INCIDENT_CATEGORY.contains(incidentCategory)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_INCIDENT_CATEGORY),
                        "trsp_incident_category"));
            }
            if (trspIncidentGs1Id != null && (trspIncidentGs1Id.equals("true") || trspIncidentGs1Id.equals("false"))) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_BOOLEAN),
                        "trsp_incident_gs1_id", "not boolean")
                );
            }
            if (!BaseUtil.isNull(trspIncidentGs1Id) && trspIncidentGs1Id.length() > 30) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                        "trsp_incident_gs1_id", 30)
                );
            }
            if (!BaseUtil.isNull(String.valueOf(incidentType)) && !ParamConstant.INCIDENT_TYPE.contains(incidentType)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_INCIDENT_TYPE),
                        "trsp_incident_type"));
            }
        }
        if (BaseUtil.isNull(trspIncidentGs1Id)) {
            trspIncidentGs1Id = BaseUtil.generateId();
        }
        IncidentInfo incident = new IncidentInfo();
        incident.setIncidentJson(mapRequestObjectToString(ttmiVehicleIncidentInfoRequestDTO));
        incident.setInstructionId(ttmiVehicleIncidentInfoRequestDTO.getTrspIsr().getTrspInstructionId());
        incident.setIncidentId(BaseUtil.generateId());
        if (userContext.getUser().getCompanyId() != null) {
            incident.setOperatorId(userContext.getUser().getCompanyId());
        }
        incidentRepository.save(incident);
        //HttpHeaders headers = setHeader(servletRequest);
        String endPointYamato = urlProperties.isMockData()
            ? urlProperties.getIncidentInfo()
            : urlProperties.getIncidentInfoYamato();

        VehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO = new VehicleIncidentInfoRequestDTO();
        TTMIVehicleIncidentInfoRequestYamatoDTO requestYamatoDTO = new TTMIVehicleIncidentInfoRequestYamatoDTO();

        TrspSrvcYamatoDTO trspSrvcYamatoDTO = new TrspSrvcYamatoDTO();
        trspSrvcYamatoDTO.setTrspNormalAbnmClsSrvcRqrmCd(
            ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspNormalAbnmClsSrvcRqrmCd());
        if (isIncident) {
            trspSrvcYamatoDTO.setTrspIncident(
                Collections.singletonList(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident()));
        }

        if (trspSrvcYamatoDTO.getTrspIncident() != null && !trspSrvcYamatoDTO.getTrspIncident().isEmpty()) {
            trspSrvcYamatoDTO.getTrspIncident().get(0).setTrspIncidentGs1Id(trspIncidentGs1Id);
        } else {
            TrspIncidentDTO trspIncidentDTO = new TrspIncidentDTO();
            trspIncidentDTO.setTrspIncidentGs1Id(trspIncidentGs1Id);
            trspSrvcYamatoDTO.setTrspIncident(Collections.singletonList(trspIncidentDTO));
        }

        trspSrvcYamatoDTO.setTrspDelay(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspDelay());
        trspSrvcYamatoDTO.setTrspVehicleStop(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop());
        trspSrvcYamatoDTO.setTrspNormalAbnmDate(
            ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspNormalAbnmDate());
        trspSrvcYamatoDTO.setTrspNormalAbnmTime(
            ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspNormalAbnmTime());
        trspSrvcYamatoDTO.setTrspLocation(ttmiVehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspLocation());
        requestYamatoDTO.setTrspSrvc(trspSrvcYamatoDTO);
        requestYamatoDTO.setTrspIsr(ttmiVehicleIncidentInfoRequestDTO.getTrspIsr());

        vehicleIncidentInfoRequestDTO.setDataModelType(APIConstant.DATA_MODEL_TYPE);
        vehicleIncidentInfoRequestDTO.setAttribute(requestYamatoDTO);

        callAPIDataChannelAsync(endPointYamato, vehicleIncidentInfoRequestDTO, servletRequest);
    }

    /**
     * データチャンネル非同期呼び出し。<BR>
     *
     * @param endPointYamato                エンドポイント
     * @param vehicleIncidentInfoRequestDTO VehicleIncidentInfoRequestDTO
     * @param servletRequest                HttpServletRequest
     */
    private void callAPIDataChannelAsync(String endPointYamato,
        VehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO, HttpServletRequest servletRequest) {
        CompletableFuture.runAsync(() -> {
            HttpHeaders headers = setHeader(servletRequest);
            HttpEntity<VehicleIncidentInfoRequestDTO> requestEntity =
                new HttpEntity<>(vehicleIncidentInfoRequestDTO, headers);

            try {
                log.info("Start TTMI-Service call Data-channel {}", vehicleIncidentInfoRequestDTO);
                ResponseEntity<String> dataChannelResponse = restTemplate.exchange(
                    endPointYamato,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
                );
                log.info("Response  TTMI-Service call Data-channel: {}", dataChannelResponse);
            } catch (Exception e) {
                log.error(" TTMI-Service call Data-channel ERROR: {}", e.getMessage());
            }

            try {
                log.info("Start TTMI-Service  Insert Tracking {}", vehicleIncidentInfoRequestDTO);
                ResponseEntity<String> response = restTemplate.exchange(
                    urlProperties.getIncidentInfoYamatoTracking(),
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
                );
                log.info("End TTMI-Service  Insert Tracking: {}", response);
            } catch (Exception e) {
                log.error(" TTMI-Service Insert Tracking ERROR: {}", e.getMessage());
            }
        });
    }

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
     * ヘッダー作成。<BR>
     *
     * @param httpServletRequest HttpServletRequest
     * @return HttpHeaders
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        //get access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String accessToken = APIConstant.BEARER;
        try {
            accessToken = accessToken + tokenCacheService.getCacheToken();
        } catch (Exception e) {
            log.error("VehicleIncidentInfoServiceImpl Get accessToken ERROR: {}", e.getMessage());
        }
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, accessToken);
        return headers;
    }

    /**
     * リクエスト属性を車両情報リクエストDTOに変換。<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @return 車両情報リクエストDTO
     * @throws NextWebException 変換エラー時
     */
    private VehicleIncidentInfoRequestDTO convertRequestAttribute(CommonRequestDTO commonRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.convertValue(commonRequestDTO.getAttribute(), VehicleIncidentInfoRequestDTO.class);
        } catch (Exception e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
            return null;
        }
    }

    /**
     * オブジェクトが空かどうかをチェック。<BR>
     *
     * @param obj オブジェクト
     * @return 空の場合はtrue、それ以外はfalse
     */
    private boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                return false;
            }
        }
        return true;
    }
}
