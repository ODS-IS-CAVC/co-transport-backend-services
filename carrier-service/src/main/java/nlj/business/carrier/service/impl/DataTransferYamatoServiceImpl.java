package nlj.business.carrier.service.impl;

import static nlj.business.carrier.constant.DataBaseConstant.TRACTOR_TYPE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.CarrierOperator;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.VehicleDiagramItemTractorResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.VehicleDiagramItemTrailerResponseDTO;
import nlj.business.carrier.mapper.DateTimeMapper;
import nlj.business.carrier.repository.CarrierRepository;
import nlj.business.carrier.service.CarrierOperatorService;
import nlj.business.carrier.service.DataTransferYamatoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DataTransferYamatoServiceImpl implements DataTransferYamatoService {

    @Resource(name = "userContext")
    private final UserContext userContext;


    private final EntityManager entityManager;
    private final CarrierRepository carrierRepository;
    private final ObjectMapper objectMapper;
    private final CarrierOperatorService carrierOperatorService;

    /**
     * データをヤマトに転送する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     */
    @Override
    public void transferDataToYamato(Long vehicleDiagramId) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            log.error("companyId not found");
        }

        CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(user.getCompanyId());
        if (carrierOperator == null) {
            carrierOperatorService.createCarrierOperator(user.getCompanyId());
        }

        List<VehicleDiagramItemTrailerResponseDTO> vehicleDiagramItemTrailers = getVehicleDiagramItemTrailers(
            vehicleDiagramId);
        if (vehicleDiagramItemTrailers.isEmpty()) {
            log.error("vehicleDiagramItemTrailers not found");
            return;
        }
        Set<Date> days = new HashSet<>();
        vehicleDiagramItemTrailers.forEach(item -> {
            try {
                if (item != null) {
                    Long transportAbilityLineItemId = insertTransportAbilityLineItem(user);
                    Long carInfoId = insertCarInfo(vehicleDiagramId, item, user, transportAbilityLineItemId);
                    Long vehicleAvbResourceId = insertVehicleAvbResource(item, user, carInfoId);
                    insertCutOffInfo(item, user, vehicleAvbResourceId, carInfoId, vehicleDiagramId, carrierOperator);
                    if (!days.contains(item.getServiceStrtDate())) {
                        VehicleDiagramItemTractorResponseDTO vehicleDiagramItemTractorResponseDTO = getVehicleDiagramItemTractor(
                            vehicleDiagramId);
                        insertCarInfoTractor(vehicleDiagramId, vehicleDiagramItemTractorResponseDTO, user,
                            transportAbilityLineItemId, item.getServiceStrtDate());
                    }
                    days.add(item.getServiceStrtDate());
                }
            } catch (Exception e) {
                log.error("Error processing vehicleDiagramItemTrailers transfer data: ", e);
            }
        });
    }

    /**
     * 車両ダイアグラムトラクターを取得する。
     *
     * @param vehicleDiagramID 車両ダイアグラムID
     * @return 車両ダイアグラムトラクター
     */
    private VehicleDiagramItemTractorResponseDTO getVehicleDiagramItemTractor(Long vehicleDiagramID) {
        try {
            String vehicleDiagramItemTrailersSql =
                "SELECT "
                    + "vd.departure_time, "
                    + "vd.arrival_time, "
                    + "vd.trip_name, "
                    + "vi.total_length, "
                    + "vi.total_width, "
                    + "vi.total_height, "
                    + "vi.max_payload, "
                    + "vi.giai, "
                    + "vdh.start_date, "
                    + "vdh.end_date, "
                    + "vi.registration_group_number, "
                    + "vi.registration_character, "
                    + "vi.registration_number_1, "
                    + "vi.registration_number_2, "
                    + "vi.registration_area_code "
                    + "FROM c_vehicle_diagram_allocation vda "
                    + "JOIN c_vehicle_diagram vd ON vda.vehicle_diagram_id = vd.id "
                    + "JOIN c_vehicle_diagram_head vdh ON vdh.id = vd.diagram_head_id "
                    + "JOIN c_vehicle_info vi ON vda.vehicle_info_id = vi.id "
                    + "WHERE vd.id = :vehicleDiagramId AND vda.vehicle_type = 1";

            List<Object[]> results = entityManager.createNativeQuery(vehicleDiagramItemTrailersSql)
                .setParameter("vehicleDiagramId", vehicleDiagramID)
                .getResultList();

            return results.stream()
                .map(VehicleDiagramItemTractorResponseDTO::fromResult)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両ダイアグラム明細のリスト
     */
    private List<VehicleDiagramItemTrailerResponseDTO> getVehicleDiagramItemTrailers(Long vehicleDiagramId) {
        try {
            String vehicleDiagramItemTrailersSql =
                "SELECT "
                    + "vdi.id, "
                    + "vdit.day, "
                    + "vdit.departure_time, "
                    + "vdit.day, "
                    + "vdit.arrival_time, "
                    + "vdit.price, "
                    + "vdi.trip_name, "
                    + "vi.total_length, "
                    + "vi.total_width, "
                    + "vi.total_height, "
                    + "vi.max_payload, "
                    + "vdi.departure_from, "
                    + "vdi.arrival_to, "
                    + "vdit.day, "
                    + "vdit.departure_time, "
                    + "vdit.day, "
                    + "vdit.arrival_time, "
                    + "vdit.cut_off_price, "
                    + "vi.vehicle_type, "
                    + "vi.registration_group_number, "
                    + "vi.registration_character, "
                    + "vi.registration_number_1, "
                    + "vi.registration_number_2, "
                    + "cc.operator_code, "
                    + "vdi.departure_from, "
                    + "vdi.arrival_to, "
                    + "vdit.id, "
                    + "vdi.id, "
                    + "vi.giai, "
                    + "vi.temperature_range, "
                    + "vi.vehicle_name, "
                    + "vi.registration_area_code, "
                    + "vda.display_order "
                    + "FROM c_vehicle_diagram_item_trailer vdit "
                    + "JOIN c_vehicle_diagram_item vdi ON vdit.vehicle_diagram_item_id = vdi.id "
                    + "JOIN c_vehicle_diagram_allocation vda ON vdit.vehicle_diagram_allocation_id = vda.id "
                    + "JOIN c_vehicle_diagram vd ON vdit.vehicle_diagram_id = vd.id "
                    + "JOIN c_vehicle_info vi ON vda.vehicle_info_id = vi.id "
                    + "JOIN c_carrier_operator cc ON vdit.operator_id = cc.id "
                    + "WHERE vd.id = :vehicleDiagramId";

            List<Object[]> results = entityManager.createNativeQuery(vehicleDiagramItemTrailersSql)
                .setParameter("vehicleDiagramId", vehicleDiagramId)
                .getResultList();

            return results.stream()
                .map(VehicleDiagramItemTrailerResponseDTO::fromResult)
                .toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 運輸能力行項目を挿入する。
     *
     * @param user ユーザー
     * @return 運輸能力行項目ID
     */
    private Long insertTransportAbilityLineItem(User user) {
        try {
            String operatorId = user.getCompanyId();
            CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(operatorId);
            if (carrierOperator == null) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
            }
            String sqlTransportAbilityLineItem =
                "INSERT INTO trsp_ability_line_item (trsp_cli_prty_head_off_id, trsp_cli_prty_brnc_off_id, "
                    + "trsp_cli_prty_name_txt, road_carr_depa_sped_org_name_txt, trsp_cli_tel_cmm_cmp_num_txt, operator_id, "
                    + "created_user, created_date) VALUES (:trspCliPrtyHeadOffId, :trspCliPrtyBrncOffId, :trspCliPrtyNameTxt, "
                    + ":roadCarrDepaSpedOrgNameTxt, :trspCliTelCmmCmpNumTxt, :operatorId, "
                    + ":createdUser, :createdDate) RETURNING id";
            return (Long) entityManager.createNativeQuery(sqlTransportAbilityLineItem)
                .setParameter("trspCliPrtyHeadOffId", carrierOperator.getOperatorCode())
                .setParameter("trspCliPrtyBrncOffId", carrierOperator.getOperatorCode())
                .setParameter("trspCliPrtyNameTxt", carrierOperator.getOperatorName())
                .setParameter("roadCarrDepaSpedOrgNameTxt", carrierOperator.getManagerName())
                .setParameter("trspCliTelCmmCmpNumTxt", carrierOperator.getPhoneNumber())
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("createdUser", user.getUsername())
                .setParameter("createdDate", LocalDateTime.now())
                .getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 車両情報を挿入する。
     *
     * @param vehicleDiagramId           車両ダイアグラムID
     * @param item                       車両ダイアグラム明細
     * @param user                       ユーザー
     * @param transportAbilityLineItemId 運輸能力行項目ID
     * @return 車両情報ID
     */
    private Long insertCarInfo(Long vehicleDiagramId, VehicleDiagramItemTrailerResponseDTO item, User user,
        Long transportAbilityLineItemId) {
        try {
            String carCtrlNumId =
                (item.getRegistrationAreaCode() != null ? item.getRegistrationAreaCode() : "")
                    + (item.getRegistrationGroupNumber() != null ? item.getRegistrationGroupNumber() : "")
                    + (item.getRegistrationCharacter() != null ? item.getRegistrationCharacter() : "")
                    + (item.getRegistrationNumber1() != null ? item.getRegistrationNumber1() : "")
                    + (item.getRegistrationNumber2() != null ? item.getRegistrationNumber2() : "");

            // Check if record exists
            String checkSql =
                "SELECT id FROM car_info WHERE operator_id = :operatorId "
                    + "AND service_no = :serviceNo "
                    + "AND service_strt_date = :serviceStrtDate "
                    + "AND giai = :giai "
                    + "AND tractor_idcr = '2'";

            List<Object> existingIds = entityManager.createNativeQuery(checkSql)
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("serviceNo", String.valueOf(vehicleDiagramId))
                .setParameter("serviceStrtDate", item.getServiceStrtDate())
                .setParameter("giai", String.valueOf(item.getGiai() != null ? item.getGiai() : 0))
                .getResultList();

            if (!existingIds.isEmpty()) {
                // Update existing record
                String updateSql =
                    "UPDATE car_info SET "
                        + "service_strt_time = :serviceStrtTime, "
                        + "service_end_date = :serviceEndDate, "
                        + "service_end_time = :serviceEndTime, "
                        + "freight_rate = :freightRate, "
                        + "service_name = :serviceName, "
                        + "car_lngh_meas = :carLnghMeas, "
                        + "car_wid_meas = :carWidMeas, "
                        + "car_hght_meas = :carHghtMeas, "
                        + "car_max_load_capacity1_meas = :carMaxLoadCapacity1Meas, "
                        + "trsp_ability_line_item_id = :trspAbilityLineItemId, "
                        + "tractor_idcr = :tractorIdcr, "
                        + "car_license_plt_num_id = :carLicensePltNumId, "
                        + "trailer_license_plt_num_id = :trailerLicensePltNumId, "
                        + "updated_user = :updatedUser, "
                        + "updated_date = :updatedDate "
                        + "WHERE id = :id RETURNING id";

                return (Long) entityManager.createNativeQuery(updateSql)
                    .setParameter("serviceStrtTime", item.getServiceStrtTime())
                    .setParameter("serviceEndDate", item.getServiceEndDate())
                    .setParameter("serviceEndTime", item.getServiceEndTime())
                    .setParameter("freightRate", item.getFreightRate())
                    .setParameter("serviceName", item.getServiceName())
                    .setParameter("carLnghMeas", item.getCarLnghMeas())
                    .setParameter("carWidMeas", item.getCarWdthMeas())
                    .setParameter("carHghtMeas", item.getCarHghtMeas())
                    .setParameter("carMaxLoadCapacity1Meas",
                        item.getCarMaxLoadCapacity1Meas() != null ? item.getCarMaxLoadCapacity1Meas() : 3)
                    .setParameter("trspAbilityLineItemId", transportAbilityLineItemId)
                    .setParameter("tractorIdcr", item.getTractorIdcr())
                    .setParameter("carLicensePltNumId",
                        TRACTOR_TYPE.equals(item.getTractorIdcr()) ? carCtrlNumId : null)
                    .setParameter("trailerLicensePltNumId",
                        !TRACTOR_TYPE.equals(item.getTractorIdcr()) ? carCtrlNumId : null)
                    .setParameter("updatedUser", user.getUsername())
                    .setParameter("updatedDate", LocalDateTime.now())
                    .setParameter("id", existingIds.get(0))
                    .getSingleResult();
            } else {
                // Insert new record
                String insertSql =
                    "INSERT INTO car_info (operator_id, created_user, created_date, service_no, service_strt_date, "
                        + "service_strt_time, service_end_date, service_end_time, freight_rate, service_name, "
                        + "car_lngh_meas, car_wid_meas, car_hght_meas, car_max_load_capacity1_meas, giai, "
                        + "trsp_ability_line_item_id, tractor_idcr, car_license_plt_num_id, trailer_license_plt_num_id) "
                        + "VALUES (:operatorId, :createdUser, :createdDate, :serviceNo, :serviceStrtDate, "
                        + ":serviceStrtTime, :serviceEndDate, :serviceEndTime, :freightRate, :serviceName, "
                        + ":carLnghMeas, :carWidMeas, :carHghtMeas, :carMaxLoadCapacity1Meas, :giai, "
                        + ":trspAbilityLineItemId, :tractorIdcr, :carLicensePltNumId, :trailerLicensePltNumId) RETURNING id";

                return (Long) entityManager.createNativeQuery(insertSql)
                    .setParameter("operatorId", user.getCompanyId())
                    .setParameter("createdUser", user.getUsername())
                    .setParameter("createdDate", LocalDateTime.now())
                    .setParameter("serviceNo", vehicleDiagramId)
                    .setParameter("serviceStrtDate", item.getServiceStrtDate())
                    .setParameter("serviceStrtTime", item.getServiceStrtTime())
                    .setParameter("serviceEndDate", item.getServiceEndDate())
                    .setParameter("serviceEndTime", item.getServiceEndTime())
                    .setParameter("freightRate", item.getFreightRate())
                    .setParameter("serviceName", item.getServiceName())
                    .setParameter("carLnghMeas", item.getCarLnghMeas())
                    .setParameter("carWidMeas", item.getCarWdthMeas())
                    .setParameter("carHghtMeas", item.getCarHghtMeas())
                    .setParameter("carMaxLoadCapacity1Meas",
                        item.getCarMaxLoadCapacity1Meas() != null ? item.getCarMaxLoadCapacity1Meas() : 3)
                    .setParameter("giai", item.getGiai() != null ? item.getGiai() : 0)
                    .setParameter("trspAbilityLineItemId", transportAbilityLineItemId)
                    .setParameter("tractorIdcr", item.getTractorIdcr())
                    .setParameter("carLicensePltNumId",
                        TRACTOR_TYPE.equals(item.getTractorIdcr()) ? carCtrlNumId : null)
                    .setParameter("trailerLicensePltNumId",
                        !TRACTOR_TYPE.equals(item.getTractorIdcr()) ? carCtrlNumId : null)
                    .getSingleResult();
            }
        } catch (Exception e) {
            log.error("data transfer insertCarInfo: " + e);
            return null;
        }
    }

    /**
     * 車両情報を挿入する。
     *
     * @param vehicleDiagramId           車両ダイアグラムID
     * @param item                       車両ダイアグラム明細
     * @param user                       ユーザー
     * @param transportAbilityLineItemId 運輸能力行項目ID
     * @param date                       日付
     */
    private void insertCarInfoTractor(Long vehicleDiagramId, VehicleDiagramItemTractorResponseDTO item, User user,
        Long transportAbilityLineItemId, Date date) {
        try {
            String carCtrlNumId =
                (item.getRegistrationAreaCode() != null ? item.getRegistrationAreaCode() : "")
                    + (item.getRegistrationGroupNumber() != null ? item.getRegistrationGroupNumber() : "")
                    + (item.getRegistrationCharacter() != null ? item.getRegistrationCharacter() : "")
                    + (item.getRegistrationNumber1() != null ? item.getRegistrationNumber1() : "")
                    + (item.getRegistrationNumber2() != null ? item.getRegistrationNumber2() : "");

            // Check if record exists
            String checkSql =
                "SELECT id FROM car_info WHERE operator_id = :operatorId "
                    + "AND service_no = :serviceNo "
                    + "AND service_strt_date = :serviceStrtDate "
                    + "AND giai = :giai "
                    + "AND tractor_idcr = '1'";

            List<Object> existingIds = entityManager.createNativeQuery(checkSql)
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("serviceNo", String.valueOf(vehicleDiagramId))
                .setParameter("serviceStrtDate", date)
                .setParameter("giai", String.valueOf(item.getGiai() != null ? item.getGiai() : 0))
                .getResultList();

            if (!existingIds.isEmpty()) {
                // Update existing record
                String updateSql =
                    "UPDATE car_info SET "
                        + "service_strt_time = :serviceStrtTime, "
                        + "service_end_date = :serviceEndDate, "
                        + "service_end_time = :serviceEndTime, "
                        + "freight_rate = :freightRate, "
                        + "service_name = :serviceName, "
                        + "car_lngh_meas = :carLnghMeas, "
                        + "car_wid_meas = :carWidMeas, "
                        + "car_hght_meas = :carHghtMeas, "
                        + "car_max_load_capacity1_meas = :carMaxLoadCapacity1Meas, "
                        + "trsp_ability_line_item_id = :trspAbilityLineItemId, "
                        + "tractor_idcr = :tractorIdcr, "
                        + "car_license_plt_num_id = :carLicensePltNumId, "
                        + "updated_user = :updatedUser, "
                        + "updated_date = :updatedDate "
                        + "WHERE id = :id RETURNING id";

                entityManager.createNativeQuery(updateSql)
                    .setParameter("serviceStrtTime", item.getDepartureTime())
                    .setParameter("serviceEndDate", item.getEndDate())
                    .setParameter("serviceEndTime", item.getArrivalTime())
                    .setParameter("freightRate", 0)
                    .setParameter("serviceName", item.getTripName())
                    .setParameter("carLnghMeas", item.getTotalLength())
                    .setParameter("carWidMeas", item.getTotalWidth())
                    .setParameter("carHghtMeas", item.getTotalHeight())
                    .setParameter("carMaxLoadCapacity1Meas", item.getMaxPayload() != null ? item.getMaxPayload() : 3)
                    .setParameter("trspAbilityLineItemId", transportAbilityLineItemId)
                    .setParameter("tractorIdcr", 1)
                    .setParameter("carLicensePltNumId", carCtrlNumId)
                    .setParameter("updatedUser", user.getUsername())
                    .setParameter("updatedDate", LocalDateTime.now())
                    .setParameter("id", existingIds.get(0))
                    .getSingleResult();
            } else {
                // Insert new record
                String insertSql =
                    "INSERT INTO car_info (operator_id, created_user, created_date, service_no, service_strt_date, "
                        + "service_strt_time, service_end_date, service_end_time, freight_rate, service_name, "
                        + "car_lngh_meas, car_wid_meas, car_hght_meas, car_max_load_capacity1_meas, giai, "
                        + "trsp_ability_line_item_id, tractor_idcr, car_license_plt_num_id) "
                        + "VALUES (:operatorId, :createdUser, :createdDate, :serviceNo, :serviceStrtDate, "
                        + ":serviceStrtTime, :serviceEndDate, :serviceEndTime, :freightRate, :serviceName, "
                        + ":carLnghMeas, :carWidMeas, :carHghtMeas, :carMaxLoadCapacity1Meas, :giai, "
                        + ":trspAbilityLineItemId, :tractorIdcr, :carLicensePltNumId) RETURNING id";

                entityManager.createNativeQuery(insertSql)
                    .setParameter("operatorId", user.getCompanyId())
                    .setParameter("createdUser", user.getUsername())
                    .setParameter("createdDate", LocalDateTime.now())
                    .setParameter("serviceNo", vehicleDiagramId)
                    .setParameter("serviceStrtDate", date)
                    .setParameter("serviceStrtTime", item.getDepartureTime())
                    .setParameter("serviceEndDate", item.getEndDate())
                    .setParameter("serviceEndTime", item.getArrivalTime())
                    .setParameter("freightRate", 0)
                    .setParameter("serviceName", item.getTripName())
                    .setParameter("carLnghMeas", item.getTotalLength())
                    .setParameter("carWidMeas", item.getTotalWidth())
                    .setParameter("carHghtMeas", item.getTotalHeight())
                    .setParameter("carMaxLoadCapacity1Meas", item.getMaxPayload() != null ? item.getMaxPayload() : 3)
                    .setParameter("giai", item.getGiai() != null ? item.getGiai() : 0)
                    .setParameter("trspAbilityLineItemId", transportAbilityLineItemId)
                    .setParameter("tractorIdcr", 1)
                    .setParameter("carLicensePltNumId", carCtrlNumId)
                    .getSingleResult();
            }
        } catch (Exception e) {
            log.error("data transfer insertCarInfoTractor: " + e);
        }
    }

    /**
     * 車両可用リソースを挿入する。
     *
     * @param item      車両ダイアグラム明細
     * @param user      ユーザー
     * @param carInfoId 車両情報ID
     * @return 車両可用リソースID
     */
    private Long insertVehicleAvbResource(VehicleDiagramItemTrailerResponseDTO item, User user, Long carInfoId) {
        try {
            String vehicleAvbResource = "INSERT INTO vehicle_avb_resource (car_info_id, operator_id, created_user, "
                + "created_date, trsp_op_date_trm_strt_date, "
                + "trsp_op_plan_date_trm_strt_time, trsp_op_date_trm_end_date, trsp_op_plan_date_trm_end_time, "
                + "trsp_op_end_area_line_one_txt, trsp_op_strt_area_line_one_txt) VALUES (:carInfoId, :operatorId, "
                + ":createdUser, :createdDate, :trspOpDateTrmStrtDate, "
                + ":trspOpPlanDateTrmStrtTime, :trspOpDateTrmEndDate, :trspOpPlanDateTrmEndTime, :trspOpEndAreaLineOneTxt, "
                + ":trspOpStrtAreaLineOneTxt) RETURNING id";
            return (Long) entityManager.createNativeQuery(vehicleAvbResource)
                .setParameter("carInfoId", carInfoId)
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("createdUser", user.getUsername())
                .setParameter("createdDate", LocalDateTime.now())
                .setParameter("trspOpDateTrmStrtDate", item.getTrspOpDateTrmStrtDate())
                .setParameter("trspOpPlanDateTrmStrtTime", item.getTrspOpPlanDateTrmStrtTime())
                .setParameter("trspOpDateTrmEndDate", item.getTrspOpDateTrmEndDate())
                .setParameter("trspOpPlanDateTrmEndTime", item.getTrspOpPlanDateTrmEndTime())
                .setParameter("trspOpEndAreaLineOneTxt", item.getTrspOpEndAreaLineOneTxt())
                .setParameter("trspOpStrtAreaLineOneTxt", item.getTrspOpStrtAreaLineOneTxt())
                .getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 切り捨て情報を挿入する。
     *
     * @param item                 車両ダイアグラム明細
     * @param user                 ユーザー
     * @param vehicleAvbResourceId 車両可用リソースID
     * @param carInfoId            車両情報ID
     * @param vehicleDiagramId     車両ダイアグラムID
     * @param carrierOperator      運行者
     */
    private void insertCutOffInfo(VehicleDiagramItemTrailerResponseDTO item, User user, Long vehicleAvbResourceId,
        Long carInfoId, Long vehicleDiagramId, CarrierOperator carrierOperator) {
        LocalTime serviceStartTime = DateTimeMapper.stringToLocalTime(
            item.getServiceStrtTime().toString().replaceAll(":", ""));
        if (item.getCutOffPrice() != null) {
            Map<String, BigDecimal> resultMap = null;
            try {
                resultMap = objectMapper.readValue(item.getCutOffPrice(), new TypeReference<Map<String, BigDecimal>>() {
                });
            } catch (JsonProcessingException e) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.Validate.VALID_CUT_OFF_PRICE);
            }

            List<String> sortedKeys = new ArrayList<>(resultMap.keySet());
            Collections.sort(sortedKeys);

            for (int i = 0; i < sortedKeys.size(); i++) {
                try {
                    String currentKey = sortedKeys.get(i);
                    String previousKey = i > 0 ? sortedKeys.get(i - 1) : "0";

                    BigDecimal cutOffTime = parseBigDecimal(currentKey);
                    BigDecimal cutOffFee = resultMap.get(currentKey);
                    LocalTime departureTimeMin = serviceStartTime.minusHours(Long.parseLong(currentKey));
                    LocalTime departureTimeMax = serviceStartTime.minusHours(Long.parseLong(previousKey));
                    String sqlCutOffInfo =
                        "INSERT INTO cut_off_info (operator_id, created_user, created_date, cut_off_time, cut_off_fee, "
                            + "vehicle_avb_resource_id) VALUES (:operatorId, :createdUser, :createdDate, "
                            + ":cutOffTime, :cutOffFee, :vehicleAvbResourceId) RETURNING id";
                    Long cutoffInfoId = (Long) entityManager.createNativeQuery(sqlCutOffInfo)
                        .setParameter("operatorId", user.getCompanyId())
                        .setParameter("createdUser", user.getUsername())
                        .setParameter("createdDate", LocalDateTime.now())
                        .setParameter("cutOffTime", cutOffTime)
                        .setParameter("cutOffFee", cutOffFee)
                        .setParameter("vehicleAvbResourceId", vehicleAvbResourceId)
                        .getSingleResult();
                    insertVehicleAvbResourceItem(item, user, vehicleAvbResourceId, carInfoId, vehicleDiagramId,
                        carrierOperator, departureTimeMin, departureTimeMax, cutoffInfoId, cutOffTime, cutOffFee);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } else {
            insertVehicleAvbResourceItem(item, user, vehicleAvbResourceId, carInfoId, vehicleDiagramId,
                carrierOperator, serviceStartTime, serviceStartTime, null, null, BigDecimal.valueOf(0));
        }
    }

    /**
     * 車両可用リソース明細を挿入する。
     *
     * @param item                 車両ダイアグラム明細
     * @param user                 ユーザー
     * @param vehicleAvbResourceId 車両可用リソースID
     * @param carInfoId            車両情報ID
     * @param vehicleDiagramId     車両ダイアグラムID
     * @param carrierOperator      運行者
     * @param departureTimeMin     出発時間最小
     * @param departureTimeMax     出発時間最大
     * @param cutoffInfoId         切り捨て情報ID
     * @param cutOffTime           切り捨て時間
     * @param cutOffFee            切り捨て料金
     */
    private void insertVehicleAvbResourceItem(VehicleDiagramItemTrailerResponseDTO item, User user,
        Long vehicleAvbResourceId, Long carInfoId, Long vehicleDiagramId, CarrierOperator carrierOperator,
        LocalTime departureTimeMin, LocalTime departureTimeMax, Long cutoffInfoId, BigDecimal cutOffTime,
        BigDecimal cutOffFee) {
        try {
            String sqlVehicleAvbResourceItem = "INSERT INTO vehicle_avb_resource_item (created_user, created_date, "
                + "vehicle_avb_resource_id, departure_from, arrival_to, day, arrival_time, operator_id, operator_code, giai, "
                + "car_info_id, vehicle_diagram_id, departure_time_min, departure_time_max, price, "
                + "vehicle_diagram_item_trailer_id, operator_name, status, trip_name, total_length, total_width, total_height, max_payload, temperature_range, vehicle_name, cut_off_info_id, cut_off_time, cut_off_fee, display_order, vehicle_diagram_item_id, departure_time) VALUES (:createdUser, :createdDate, :vehicleAvbResourceId, "
                + ":departureFrom, :arrivalTo, :day, :arrivalTime, :operatorId, :operatorCode, :giai, :carInfoId, "
                + ":vehicleDiagramId, :departureTimeMin, :departureTimeMax, :price, :vehicleDiagramItemTrailerId, :operatorName, :status, :tripName, :totalLength, :totalWidth, :totalHeight, :maxPayload, :temperatureRange, :vehicleName, :cutOffInfoId, :cutOffTime, :cutOffFee, :displayOrder, :vehicleDiagramItemId, :departureTime) RETURNING id";
            entityManager.createNativeQuery(sqlVehicleAvbResourceItem)
                .setParameter("createdUser", user.getUsername())
                .setParameter("createdDate", LocalDateTime.now())
                .setParameter("vehicleAvbResourceId", vehicleAvbResourceId)
                .setParameter("departureFrom", item.getTrspOpStrtAreaCtyJisCd())
                .setParameter("arrivalTo", item.getTrspOpEndAreaCtyJisCd())
                .setParameter("day", item.getServiceStrtDate())
                .setParameter("arrivalTime", item.getServiceEndTime())
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("operatorCode", item.getOperatorCode())
                .setParameter("giai", item.getGiai() != null ? item.getGiai() : 0)
                .setParameter("carInfoId", carInfoId)
                .setParameter("vehicleDiagramId", vehicleDiagramId)
                .setParameter("departureTimeMin", departureTimeMin)
                .setParameter("departureTimeMax", departureTimeMax)
                .setParameter("price", item.getFreightRate().subtract(cutOffFee))
                .setParameter("vehicleDiagramItemTrailerId", item.getVehicleDiagramItemTrailerId())
                .setParameter("operatorName", carrierOperator.getOperatorName())
                .setParameter("status", ParamConstant.Status.INITIALIZED)
                .setParameter("tripName", item.getServiceName())
                .setParameter("totalLength", item.getCarLnghMeas() != null ? item.getCarLnghMeas() : BigDecimal.ZERO)
                .setParameter("totalWidth", item.getCarWdthMeas() != null ? item.getCarWdthMeas() : BigDecimal.ZERO)
                .setParameter("totalHeight", item.getCarHghtMeas() != null ? item.getCarHghtMeas() : BigDecimal.ZERO)
                .setParameter("maxPayload",
                    item.getCarMaxLoadCapacity1Meas() != null ? item.getCarMaxLoadCapacity1Meas() : BigDecimal.ZERO)
                .setParameter("temperatureRange", item.getTemperatureRange())
                .setParameter("vehicleName", item.getVehicleName())
                .setParameter("cutOffInfoId", cutoffInfoId)
                .setParameter("cutOffTime", cutOffTime)
                .setParameter("cutOffFee", cutOffFee)
                .setParameter("displayOrder", item.getDisplayOrder())
                .setParameter("vehicleDiagramItemId", item.getServiceNo())
                .setParameter("departureTime", item.getServiceStrtTime())
                .getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * データの日付時間を転送する。
     *
     * @param vehicleDiagramItem 車両ダイアグラム明細
     */
    @Override
    public void transferDataDateTime(VehicleDiagramItem vehicleDiagramItem) {
        try {
            if (vehicleDiagramItem != null) {
                String sqlUpdateResourceItem =
                    "UPDATE vehicle_avb_resource_item SET arrival_time = :arrivalTime, departure_time = :departureTime, "
                        + " departure_time_min = TO_TIMESTAMP(:departureTime , 'HH24:MI:SS') - (INTERVAL '1 hour' * COALESCE(cut_off_time, 0)),"
                        + " departure_time_max = TO_TIMESTAMP(:departureTime , 'HH24:MI:SS') - CASE "
                        + "                     WHEN cut_off_time = 1 THEN INTERVAL '0 hour' "
                        + "                     WHEN cut_off_time = 2 THEN INTERVAL '1 hour' "
                        + "                     WHEN cut_off_time = 3 THEN INTERVAL '2 hour' "
                        + "                     WHEN cut_off_time = 4 THEN INTERVAL '3 hour' "
                        + "                     WHEN cut_off_time = 5 THEN INTERVAL '4 hour' "
                        + "                     ELSE INTERVAL '0 hour' "
                        + "                     END "
                        + " WHERE vehicle_diagram_item_id = :vehicleDiagramItemId AND status < 3 ";
                Query queryUpdateResourceItem = entityManager.createNativeQuery(sqlUpdateResourceItem);
                queryUpdateResourceItem.setParameter("arrivalTime", vehicleDiagramItem.getArrivalTime());
                queryUpdateResourceItem.setParameter("departureTime", vehicleDiagramItem.getDepartureTime());
                queryUpdateResourceItem.setParameter("vehicleDiagramItemId", vehicleDiagramItem.getId());
                queryUpdateResourceItem.executeUpdate();

                String sqlUpdateResource =
                    "UPDATE vehicle_avb_resource var SET  trsp_op_plan_date_trm_end_time = :arrivalTime, trsp_op_plan_date_trm_strt_time = :departureTime FROM vehicle_avb_resource_item vair "
                        +
                        "WHERE var.id = vair.vehicle_avb_resource_id AND vair.vehicle_diagram_item_id = :vehicleDiagramItemId";
                Query queryUpdateResource = entityManager.createNativeQuery(sqlUpdateResource);
                queryUpdateResource.setParameter("arrivalTime", vehicleDiagramItem.getArrivalTime());
                queryUpdateResource.setParameter("departureTime", vehicleDiagramItem.getDepartureTime());
                queryUpdateResource.setParameter("vehicleDiagramItemId", vehicleDiagramItem.getId());
                queryUpdateResource.executeUpdate();

                String sqlUpdateCarInfo =
                    "UPDATE car_info ci SET  service_end_time  = :arrivalTime, service_strt_time = :departureTime FROM vehicle_avb_resource var  "
                        + " JOIN vehicle_avb_resource_item vair ON var.id = vair.vehicle_avb_resource_id " +
                        "WHERE ci.id = var.car_info_id AND vair.vehicle_diagram_item_id = :vehicleDiagramItemId";
                Query queryUpdateCarInfo = entityManager.createNativeQuery(sqlUpdateCarInfo);
                queryUpdateCarInfo.setParameter("arrivalTime", vehicleDiagramItem.getArrivalTime());
                queryUpdateCarInfo.setParameter("departureTime", vehicleDiagramItem.getDepartureTime());
                queryUpdateCarInfo.setParameter("vehicleDiagramItemId", vehicleDiagramItem.getId());
                queryUpdateCarInfo.executeUpdate();

                String sqlUpdateTransOrder =
                    "UPDATE t_trans_order tto SET departure_time = :departureTime, arrival_time = :arrivalTime WHERE vehicle_diagram_item_id = :vehicleDiagramItemId";
                Query queryUpdateTransOrder = entityManager.createNativeQuery(sqlUpdateTransOrder);
                queryUpdateTransOrder.setParameter("departureTime", vehicleDiagramItem.getDepartureTime());
                queryUpdateTransOrder.setParameter("arrivalTime", vehicleDiagramItem.getArrivalTime());
                queryUpdateTransOrder.setParameter("vehicleDiagramItemId", vehicleDiagramItem.getId());
                queryUpdateTransOrder.executeUpdate();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 文字列をBigDecimalに変換する。
     *
     * @param value 文字列
     * @return BigDecimal
     */
    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
