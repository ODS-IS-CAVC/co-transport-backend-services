package nlj.business.carrier.link.service.impl;

import static nlj.business.carrier.link.constant.ParamConstant.CAR_LICENSE_PLT_NUM_ID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.mail.util.NextMailUtil;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.constant.ParamConstant.IncidentCategory;
import nlj.business.carrier.link.domain.Incident;
import nlj.business.carrier.link.domain.VehicleDiagramItemOperationTracking;
import nlj.business.carrier.link.domain.VehicleDiagramItemTracking;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.incident.info.AttributeRequestDmpDTO;
import nlj.business.carrier.link.dto.incident.info.IncidentRequestDmpDTO;
import nlj.business.carrier.link.dto.incident.info.VehicleIncidentInfoRequestDmpDTO;
import nlj.business.carrier.link.dto.vehicleIncident.TrspIsVehicleStopSucceededDTO;
import nlj.business.carrier.link.dto.vehicleIncident.TrspSrvcDTO;
import nlj.business.carrier.link.dto.vehicleIncident.request.VehicleIncidentInfoRequestDTO;
import nlj.business.carrier.link.mapper.DateTimeMapper;
import nlj.business.carrier.link.repository.IncidentRepository;
import nlj.business.carrier.link.repository.VehicleDiagramItemOperationTrackingRepository;
import nlj.business.carrier.link.repository.VehicleDiagramItemTrackingRepository;
import nlj.business.carrier.link.repository.VehicleInfoCarrierCustomRepository;
import nlj.business.carrier.link.service.VehicleIncidentInfoService;
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
 * 車両事故情報サービス実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleIncidentInfoServiceImpl implements VehicleIncidentInfoService {

    private final IncidentRepository incidentRepository;
    private final RestTemplate restTemplate;
    private final NljUrlProperties urlProperties;
    private final NljAuthProperties authProperties;
    private final VehicleDiagramItemTrackingRepository vehicleDiagramItemTrackingRepository;
    private final VehicleDiagramItemOperationTrackingRepository vehicleDiagramItemOperationTrackingRepository;
    private final VehicleInfoCarrierCustomRepository vehicleInfoCarrierCustomRepository;
    private final ValidateUtil validateUtil;
    private final NextMailUtil nextMailUtil;
    private final EntityManager entityManager;

    @Resource
    private UserContext userContext;

    /**
     * 車両事故情報を登録.<BR>
     *
     * @param httpServletRequest HTTPリクエスト
     * @param commonRequestDTO   共通リクエストDTO
     * @throws JsonProcessingException エラー
     */
    @Override
    @Transactional
    public void registerIncident(HttpServletRequest httpServletRequest,
        CommonRequestDTO commonRequestDTO) throws JsonProcessingException {
        try {
            HttpHeaders headers = setHeader(httpServletRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            VehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO = convertRequestAttribute(commonRequestDTO);
            String incidentJson = null;
            if (vehicleIncidentInfoRequestDTO != null) {
                incidentJson = objectMapper.writeValueAsString(
                    vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident());
            }

            String trspInstructionId = vehicleIncidentInfoRequestDTO.getTrspIsr().getTrspInstructionId();
            String incidentId = BaseUtil.generateId();
            String incidentCategory = "";
            String incidentTypeStr = "";
            int incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_0;
            boolean isIncident = false;
            if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident() != null) {
                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0).getTrspIncidentGs1Id()
                    != null) {
                    incidentId = vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0)
                        .getTrspIncidentGs1Id();
                }

                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0).getTrspIncidentCategory()
                    != null) {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1;
                    incidentCategory = String.valueOf(vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident()
                        .get(0).getTrspIncidentCategory());
                    incidentTypeStr = vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident()
                        .get(0).getTrspIncidentType();
                    isIncident = true;
                }
            }
            if (!isIncident) {
                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop() != null &&
                    vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop().getTrspIsVehicleStopSucceeded()) {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_4;
                    incidentCategory = IncidentCategory.TRAFFIC_INCIDENT;
                    incidentTypeStr = "他停止車両";
                } else {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_5;
                }
            }

            Incident incident = new Incident();
            String giai = null;
            String truckId = "";
            String vehicleInfoIdSql = "SELECT vehicle_info_id FROM c_vehicle_diagram_allocation WHERE vehicle_diagram_id = (SELECT vehicle_diagram_id FROM c_vehicle_diagram_item WHERE id = :vehicle_diagram_item_id) AND vehicle_type = :vehicle_type";
            Long vehicleInfoId = (Long) entityManager.createNativeQuery(vehicleInfoIdSql)
                .setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId))
                .setParameter("vehicle_type", ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR).getResultList().stream()
                .findFirst().orElse(null);
            if (vehicleInfoId != null) {
                String getGiaiSql = "SELECT giai FROM c_vehicle_info WHERE id = :vehicle_info_id";
                Object giaiResult = entityManager.createNativeQuery(getGiaiSql)
                    .setParameter("vehicle_info_id", vehicleInfoId).getResultList().stream().findFirst().orElse(null);
                giai = giaiResult != null ? giaiResult.toString() : null;
            }

            VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalTime adjustedTime = currentTime.plusHours(9);
            LocalDate adjustedDate = currentDate;
            if (adjustedTime.isBefore(currentTime)) {
                adjustedDate = currentDate.plusDays(1);
            }
            vehicleDiagramItemTracking.setOperationDate(adjustedDate);
            vehicleDiagramItemTracking.setOperationTime(adjustedTime);
            vehicleDiagramItemTracking.setStatus(1);
            vehicleDiagramItemTracking.setVehicleDiagramItemId(
                Long.valueOf(vehicleIncidentInfoRequestDTO.getTrspIsr().getTrspInstructionId()));
            TrspSrvcDTO trspSrvc = vehicleIncidentInfoRequestDTO.getTrspSrvc();
            String errorMessage = "";
            if (trspSrvc != null) {
                TrspIsVehicleStopSucceededDTO trsIsVSS = trspSrvc.getTrspVehicleStop();
                List<String> message = new ArrayList<>();
                //trsp_is_vehicle_stop_succeeded = true
                if (trsIsVSS != null && trsIsVSS.getTrspIsVehicleStopSucceeded()) {
                    vehicleDiagramItemTracking.setLabel(MessageConstant.VehicleDiagramItemTracking.STOP_LABEL);
                    message.add(
                        String.format(MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_TRUE,
                            trspSrvc.getTrspLocation().getLat(), trspSrvc.getTrspLocation().getLon(), incidentId));
                } else {
                    vehicleDiagramItemTracking.setLabel(MessageConstant.VehicleDiagramItemTracking.INCIDENT_LABEL);
                    if (trspSrvc.getTrspIncident() != null && isIncident) {
                        message.add(validateUtil.getErrorMessage(incidentCategory, incidentTypeStr));
                    }
                    if (trspSrvc.getTrspDelay() != null) {
                        LocalTime tmpTime = DateTimeMapper.stringToLocalTime(trspSrvc.getTrspDelay().getTrspEtaTime());
                        message.add(String.format(
                            MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_WITH_DELAY,
                            tmpTime.getHour(), tmpTime.getMinute()));
                    }
                }

                message.add(String.format(
                    MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_ID, incidentId));
                errorMessage = BaseUtil.listToString(message, "。");
                vehicleDiagramItemTracking.setMessage(errorMessage);
            }
            vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

            String getTripNameSql = "SELECT trip_name FROM c_vehicle_diagram_item WHERE id = :vehicle_diagram_item_id";
            Object tripNameResult = entityManager.createNativeQuery(getTripNameSql)
                .setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId)).getResultList().stream()
                .findFirst().orElse(null);
            String tripName = tripNameResult != null ? tripNameResult.toString() : null;
            // update incident vehicle diagram item
            String sqlUpdateVehicleDiagramItem = "UPDATE c_vehicle_diagram_item SET incident_type = :incident_type, " +
                "incident_msg = :incident_msg WHERE id = :vehicle_diagram_item_id";
            entityManager.createNativeQuery(sqlUpdateVehicleDiagramItem).setParameter("incident_msg",
                    errorMessage).setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId))
                .setParameter("incident_type", incidentType)
                .executeUpdate();

            incident.setIncidentJson(incidentJson);
            incident.setIncidentId(incidentId);
            incident.setOperatorId(userContext.getUser().getCompanyId());
            incident.setInstructionId(trspInstructionId);
            incident.setVehicleId(giai);

            incidentRepository.save(incident);

            //Do not set when ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_5
            if (incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1 ||
                incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_4) {
                try {
                    updateVehicleIncidentInfoDmp(commonRequestDTO, headers, incident, vehicleIncidentInfoRequestDTO,
                        tripName, incidentTypeStr, incidentCategory);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * 車両事故情報を登録.<BR>
     *
     * @param httpServletRequest HTTPリクエスト
     * @param commonRequestDTO   共通リクエストDTO
     * @throws JsonProcessingException エラー
     */
    @Override
    @Transactional
    public void registerIncidentOperation(HttpServletRequest httpServletRequest,
        CommonRequestDTO commonRequestDTO) throws JsonProcessingException {
        try {
            VehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO = convertRequestAttribute(commonRequestDTO);
            String trspInstructionId = vehicleIncidentInfoRequestDTO.getTrspIsr().getTrspInstructionId();
            String incidentId = BaseUtil.generateId();
            String incidentCategory = "";
            String incidentTypeStr = "";
            boolean isIncident = false;
            int incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_0;
            if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident() != null
                && !vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().isEmpty()) {

                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0).getTrspIncidentGs1Id()
                    != null) {
                    incidentId = vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0)
                        .getTrspIncidentGs1Id();
                }

                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident().get(0).getTrspIncidentCategory()
                    != null) {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1;
                    incidentCategory = String.valueOf(vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident()
                        .get(0).getTrspIncidentCategory());
                    incidentTypeStr = vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspIncident()
                        .get(0).getTrspIncidentType();
                    isIncident = true;
                }
            }
            if (!isIncident) {
                if (vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop() != null &&
                    vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspVehicleStop().getTrspIsVehicleStopSucceeded()) {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_4;
                    incidentCategory = IncidentCategory.TRAFFIC_INCIDENT;
                    incidentTypeStr = "他停止車両";
                } else {
                    incidentType = ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_5;
                }
            }

            VehicleDiagramItemOperationTracking vehicleDiagramItemTracking = new VehicleDiagramItemOperationTracking();

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalTime adjustedTime = currentTime.plusHours(9);
            LocalDate adjustedDate = currentDate;
            if (adjustedTime.isBefore(currentTime)) {
                adjustedDate = currentDate.plusDays(1);
            }
            vehicleDiagramItemTracking.setOperationDate(adjustedDate);
            vehicleDiagramItemTracking.setOperationTime(adjustedTime);
            vehicleDiagramItemTracking.setStatus(1);
            vehicleDiagramItemTracking.setVehicleDiagramItemId(
                Long.valueOf(vehicleIncidentInfoRequestDTO.getTrspIsr().getTrspInstructionId()));
            TrspSrvcDTO trspSrvc = vehicleIncidentInfoRequestDTO.getTrspSrvc();
            String errorMessage = "";
            if (trspSrvc != null) {
                TrspIsVehicleStopSucceededDTO trsIsVSS = trspSrvc.getTrspVehicleStop();
                List<String> message = new ArrayList<>();
                //trsp_is_vehicle_stop_succeeded = true
                if (trsIsVSS != null && trsIsVSS.getTrspIsVehicleStopSucceeded()) {
                    vehicleDiagramItemTracking.setLabel(MessageConstant.VehicleDiagramItemTracking.STOP_LABEL);
                    message.add(
                        String.format(MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_TRUE,
                            trspSrvc.getTrspLocation().getLat(), trspSrvc.getTrspLocation().getLon(), incidentId));
                } else {
                    vehicleDiagramItemTracking.setLabel(MessageConstant.VehicleDiagramItemTracking.INCIDENT_LABEL);
                    if (trspSrvc.getTrspIncident() != null && isIncident) {
                        message.add(validateUtil.getErrorMessage(incidentCategory, incidentTypeStr));
                    }
                    if (trspSrvc.getTrspDelay() != null) {
                        LocalTime tmpTime = DateTimeMapper.stringToLocalTime(trspSrvc.getTrspDelay().getTrspEtaTime());
                        message.add(String.format(
                            MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_WITH_DELAY,
                            tmpTime.getHour(), tmpTime.getMinute()));
                    }
                }

                message.add(String.format(
                    MessageConstant.VehicleDiagramItemTracking.INCIDENT_MESSAGE_ID, incidentId));
                errorMessage = BaseUtil.listToString(message, "。");
                vehicleDiagramItemTracking.setMessage(errorMessage);
            }
            vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemTracking);
            try {
                // send mail to police
                String truckId = "";
                String vehicleInfoIdSql = "SELECT vehicle_info_id FROM c_vehicle_diagram_allocation WHERE vehicle_diagram_id = (SELECT vehicle_diagram_id FROM c_vehicle_diagram_item WHERE id = :vehicle_diagram_item_id) AND vehicle_type = :vehicle_type";
                Long vehicleInfoId = (Long) entityManager.createNativeQuery(vehicleInfoIdSql)
                    .setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId))
                    .setParameter("vehicle_type", ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR).getResultList()
                    .stream()
                    .findFirst().orElse(null);
                if (vehicleInfoId != null) {
                    String getGiaiSql = "SELECT giai FROM c_vehicle_info WHERE id = :vehicle_info_id";
                    Object giaiResult = entityManager.createNativeQuery(getGiaiSql)
                        .setParameter("vehicle_info_id", vehicleInfoId).getResultList().stream().findFirst()
                        .orElse(null);
                    truckId = vehicleInfoCarrierCustomRepository.getTruckId(vehicleInfoId);
                }

                String getTripNameSql = "SELECT trip_name FROM c_vehicle_diagram_item WHERE id = :vehicle_diagram_item_id";
                Object tripNameResult = entityManager.createNativeQuery(getTripNameSql)
                    .setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId)).getResultList().stream()
                    .findFirst().orElse(null);
                String tripName = tripNameResult != null ? tripNameResult.toString() : null;

                List<String> data = new ArrayList<>();
                data.add(tripName);
                String getDataSQL = "SELECT day, departure_time, departure_From, arrival_to FROM c_vehicle_diagram_item WHERE id = :vehicle_diagram_item_id";
                Object[] getDataResult = (Object[]) entityManager.createNativeQuery(getDataSQL)
                    .setParameter("vehicle_diagram_item_id", Long.valueOf(trspInstructionId)).getResultList().stream()
                    .findFirst().orElse(null);
                if (getDataResult != null) {
                    String departureFrom = getDataResult[2] != null ? getDataResult[2].toString() : "";
                    String arrivalTo = getDataResult[3] != null ? getDataResult[3].toString() : "";
                    if (!BaseUtil.isNull(departureFrom) && !BaseUtil.isNull(arrivalTo)) {
                        String departureFromSql = "SELECT name FROM location_master WHERE code = :departure_from";
                        Object departureFromResult = entityManager.createNativeQuery(departureFromSql)
                            .setParameter("departure_from", departureFrom).getResultList().stream().findFirst()
                            .orElse(null);
                        String departureFromName = departureFromResult != null ? departureFromResult.toString() : "";
                        String arrivalToSql = "SELECT name FROM location_master WHERE code = :arrival_to";
                        Object arrivalToResult = entityManager.createNativeQuery(arrivalToSql)
                            .setParameter("arrival_to", arrivalTo).getResultList().stream().findFirst().orElse(null);
                        String arrivalToName = arrivalToResult != null ? arrivalToResult.toString() : "";
                        data.add(departureFromName + " ～ " + arrivalToName);
                    } else {
                        data.add("");
                    }
                    if (getDataResult[0] instanceof java.sql.Date && getDataResult[1] instanceof java.sql.Time) {
                        LocalDate date = ((java.sql.Date) getDataResult[0]).toLocalDate();
                        LocalTime time = ((java.sql.Time) getDataResult[1]).toLocalTime();
                        data.add(DateTimeMapper.localDateTimeToJapaneseFormat(LocalDateTime.of(date, time)));
                    } else {
                        data.add("");
                    }
                }
                data.add(truckId);
                data.add(incidentId);
                //Do not set when ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_5
                if (incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1 ||
                    incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_4) {
                    String mailType = incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1
                        ? ParamConstant.EMAIL_POLICE_INCIDENT : ParamConstant.EMAIL_POLICE_INCIDENT_STOP;
                    nextMailUtil.sendMailByType(ParamConstant.EMAIL_POLICE, mailType, data);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * 車両事故情報をDMPに送信.<BR>
     *
     * @param commonRequestDTO              共通リクエストDTO
     * @param headers                       HTTPヘッダー
     * @param incident                      事故情報
     * @param vehicleIncidentInfoRequestDTO 車両事故情報リクエストDTO
     * @param tripName                      旅程名
     * @param incidentType                  事故タイプ
     * @param incidentCategory              事故カテゴリ
     */
    private void updateVehicleIncidentInfoDmp(CommonRequestDTO commonRequestDTO, HttpHeaders headers, Incident incident,
        VehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO, String tripName,
        String incidentType, String incidentCategory) {
        String endPointIncidentInfoDmp = urlProperties.getIncidentInfo();
        VehicleIncidentInfoRequestDmpDTO vehicleIncidentInfoRequestDmpDTO = new VehicleIncidentInfoRequestDmpDTO();
        vehicleIncidentInfoRequestDmpDTO.setDataModelType(commonRequestDTO.getDataModelType());
        AttributeRequestDmpDTO attributeRequestDmpDTO = new AttributeRequestDmpDTO();
        attributeRequestDmpDTO.setIncidentID(incident.getIncidentId());
        attributeRequestDmpDTO.setDeliveryName(tripName);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(
            vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspNormalAbnmDate() +
                vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspNormalAbnmTime(), inputFormatter);
        attributeRequestDmpDTO.setDateTime(dateTime.format(outputFormatter));
        attributeRequestDmpDTO.setLat(vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspLocation().getLat());
        attributeRequestDmpDTO.setLon(vehicleIncidentInfoRequestDTO.getTrspSrvc().getTrspLocation().getLon());
        attributeRequestDmpDTO.setVehicleID(CAR_LICENSE_PLT_NUM_ID);
        attributeRequestDmpDTO.setVehicleName(CAR_LICENSE_PLT_NUM_ID);

        if (BaseUtil.isNull(incident.getOperatorId())) {
            attributeRequestDmpDTO.setOperatorID("793647dd-b372-49d4-a48d-8d62ada95428");
        } else {
            attributeRequestDmpDTO.setOperatorID(incident.getOperatorId());
        }
        IncidentRequestDmpDTO incidentRequestDmpDTO = new IncidentRequestDmpDTO();
        List<String> incidentTypes = Arrays.asList(incidentType);
        if (IncidentCategory.VEHICLE_CONTROL_INCIDENT.equals(incidentCategory)) {
            incidentRequestDmpDTO.setVehicleControlIncident(new ArrayList<>(incidentTypes));
        } else if (IncidentCategory.OPERATIONAL_BASIC_INCIDENT.equals(incidentCategory)) {
            incidentRequestDmpDTO.setOperationalBasicIncident(new ArrayList<>(incidentTypes));
        } else if (IncidentCategory.ACCIDENT.equals(incidentCategory)) {
            incidentRequestDmpDTO.setAccidentIncident(new ArrayList<>(incidentTypes));
        } else if (IncidentCategory.WEATHER_INCIDENT.equals(incidentCategory)) {
            incidentRequestDmpDTO.setWeatherIncident(new ArrayList<>(incidentTypes));
        } else if (IncidentCategory.TRAFFIC_INCIDENT.equals(incidentCategory)) {
            incidentRequestDmpDTO.setTrafficIncident(new ArrayList<>(incidentTypes));
        }
        attributeRequestDmpDTO.setIncidents(incidentRequestDmpDTO);

        vehicleIncidentInfoRequestDmpDTO.setAttribute(attributeRequestDmpDTO);
        HttpEntity<VehicleIncidentInfoRequestDmpDTO> requestEntity =
            new HttpEntity<>(vehicleIncidentInfoRequestDmpDTO, headers);
        callDmpGatewayAsync(endPointIncidentInfoDmp, requestEntity, vehicleIncidentInfoRequestDmpDTO);
    }

    /**
     * DMPに送信.<BR>
     *
     * @param endPointIncidentInfoDmp          エンドポイント
     * @param requestEntity                    リクエストエンティティ
     * @param vehicleIncidentInfoRequestDmpDTO 車両事故情報リクエストDMPDTO
     */
    private void callDmpGatewayAsync(String endPointIncidentInfoDmp,
        HttpEntity<VehicleIncidentInfoRequestDmpDTO> requestEntity,
        VehicleIncidentInfoRequestDmpDTO vehicleIncidentInfoRequestDmpDTO) {

        CompletableFuture.runAsync(() -> {
            try {
                log.info("Start carrier link call gateway dmp " + vehicleIncidentInfoRequestDmpDTO);
                ResponseEntity<Void> response = restTemplate.exchange(
                    endPointIncidentInfoDmp,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
                );
                log.info("Response carrier link call gateway dmp: " + response);
            } catch (Exception e) {
                log.error("Carrier link call gateway DMP ERROR: {}", e.getMessage());
            }
        });
    }

    /**
     * ヘッダーを設定.<BR>
     *
     * @param httpServletRequest HTTPリクエスト
     * @return ヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * リクエスト属性を車両情報リクエストDTOに変換
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @return 車両情報リクエストDTO
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
}
