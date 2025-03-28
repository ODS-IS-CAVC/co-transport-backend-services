package nlj.business.transaction.service.impl;

import static com.next.logistic.util.BaseUtil.getGiai;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.APIConstant.IX;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.domain.yamato.CarInfo;
import nlj.business.transaction.dto.operationPlans.OperationPlansNotifyDTO;
import nlj.business.transaction.dto.operationPlans.request.LogisticsServiceProviderNotifyDTO;
import nlj.business.transaction.dto.operationPlans.request.RoadCarrNotifyDTO;
import nlj.business.transaction.dto.request.DiagramItemUpdateStatusRequest;
import nlj.business.transaction.dto.trip.VehicleDiagramMobilityHubResponseDTO;
import nlj.business.transaction.dto.trip.cnsLineItem.response.CnsLineItemResponseDTO;
import nlj.business.transaction.dto.trip.transportPlans.ConsigneePartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.ShipFromPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.ShipToPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.TrspIsrDTO;
import nlj.business.transaction.dto.trip.transportPlans.TrspSrvcDTO;
import nlj.business.transaction.dto.trip.transportPlans.request.TrspPlanDTO;
import nlj.business.transaction.dto.trip.transportPlans.request.TrspPlansNotifyRequestDTO;
import nlj.business.transaction.dto.trip.trspAbillityLineItem.response.TrspAbillityLineItemResponseDTO;
import nlj.business.transaction.dto.trip.trspPlanLineItem.response.TrspPlanLineItemResponseDTO;
import nlj.business.transaction.dto.trip.vehicleAvbResource.request.VehicleAvbResourceRequestDTO;
import nlj.business.transaction.dto.trip.vehicleAvbResource.response.VehicleAvbResourceResponseDTO;
import nlj.business.transaction.dto.trip.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerResponseDTO;
import nlj.business.transaction.mapper.DateTimeMapper;
import nlj.business.transaction.repository.CarInfoRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemRepository;
import nlj.business.transaction.repository.VehicleDiagramMobilityHubCustomRepository;
import nlj.business.transaction.service.IXBurningService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <PRE>
 * 配送マッチングを実行するクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IXBurningServiceImpl implements IXBurningService {

    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;
    private final NljAuthProperties authProperties;
    private final TransOrderRepository transOrderRepository;
    private final EntityManager entityManager;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;
    private final VehicleDiagramMobilityHubCustomRepository vehicleDiagramMobilityHubCustomRepository;
    private final CarInfoRepository carInfoRepository;

    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public void registerIXBurning(Long transOrderId, HttpServletRequest servletRequest) {
        HttpHeaders headers = setHeader(servletRequest);
        String endPointIX = urlProperties.getDomainIx();

        TransOrder transOrder = transOrderRepository.findById(transOrderId).get();
        Long vehicleAvbResourceId = transOrder.getVehicleAvbResourceId();
        VehicleAvbResourceResponseDTO vehicleResource = new VehicleAvbResourceResponseDTO();
        TrspPlanLineItemResponseDTO trspPlanLineItemResponseDTO = new TrspPlanLineItemResponseDTO();
        CnsLineItemResponseDTO cnsLineItemResponseDTO = new CnsLineItemResponseDTO();
        VehicleDiagramItemTrailerResponseDTO vehicleDiagramItemTrailer = new VehicleDiagramItemTrailerResponseDTO();
        String tractorGiai = "";
        String trailerGiaiList = "";
        try {
            transOrder = transOrderRepository.findById(transOrderId).get();
            if (transOrder.getVehicleDiagramItemTrailerId() != null) {
                vehicleDiagramItemTrailer = getVehicleDiagramItemTrailerResponseDTO(
                    transOrder.getVehicleDiagramItemTrailerId());
            }
            vehicleResource = getVehicleResource(vehicleAvbResourceId);
            cnsLineItemResponseDTO = getCnsLineItem(transOrder);
            trspPlanLineItemResponseDTO = getTrspPlanLineItem(cnsLineItemResponseDTO);
            trailerGiaiList = vehicleAvbResourceItemRepository.findByVehicleAvbResourceId(vehicleAvbResourceId).stream()
                .map(VehicleAvbResourceItem::getGiai)
                .collect(Collectors.joining(","));

            if (vehicleDiagramItemTrailer != null && vehicleDiagramItemTrailer.getVehicleDiagramId() != null) {
                List<CarInfo> carInfos = carInfoRepository.findCarInfosByServiceNoAndServiceStrtDateAndTractorIdcr(
                    String.valueOf(vehicleDiagramItemTrailer.getVehicleDiagramId()),
                    LocalDate.parse(vehicleResource.getTrspOpDateTrmStrtDate(),
                        DateTimeFormatter.ofPattern("yyyyMMdd")), "1");
                tractorGiai = carInfos.stream()
                    .map(CarInfo::getGiai)
                    .collect(Collectors.joining(","));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String operationId = transOrder.getVehicleAvbResourceItemId().toString();

        List<TransOrder> transOrders = transOrderRepository.findByVehicleDiagramItemIdAndStatusIn(
            transOrder.getVehicleDiagramItemId(),
            List.of("140", "150", "151", "160", "161", "240", "250", "251", "260", "261"));

        int index = 0;

        for (TransOrder transOrderT : transOrders) {
            String trspInstructionId = transOrderT.getCnsLineItemByDateId().toString();
            // Call IX 040
            registerIX040(endPointIX, headers, trspInstructionId, operationId, transOrderT, vehicleResource,
                getGiai(tractorGiai), vehicleDiagramItemTrailer);
        }

        for (TransOrder transOrderT : transOrders) {
            // Call IX 043
            registerIX043(endPointIX, headers, operationId, transOrderT, vehicleResource);
        }
        // Wait for 30s
        try {
            Thread.sleep(5000); // Wait for 15 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
        String IXdemandUrl = urlProperties.getDomainIxDemand();
        for (TransOrder transOrderT : transOrders) {
            // Call IX 050
            registerIX050(IXdemandUrl, headers, transOrderT, vehicleResource, null, null, vehicleDiagramItemTrailer);
        }

        for (TransOrder transOrderT : transOrders) {
            String trspInstructionId = transOrderT.getCnsLineItemByDateId().toString();
            // Call IX 052
            registerIX052(IXdemandUrl, headers, trspInstructionId, trspPlanLineItemResponseDTO, transOrderT);
            index = index + 1;
        }

        updateDiagramItemStatus(transOrder);
    }

    /**
     * キャリアのリクエスト時間を取得する。
     *
     * @param vehicleResource 車両リソース
     * @param isCarrier       キャリアかどうか
     * @return リクエスト時間のリスト
     */
    private List<String> getRequestTimeCarrier(VehicleAvbResourceResponseDTO vehicleResource, boolean isCarrier) {
        String dateStr = vehicleResource.getTrspOpDateTrmStrtDate();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, inputFormatter);
        String formattedDate = date.format(outputFormatter);

        String diagramStart = formattedDate + " " + BaseUtil.formatTime(vehicleResource.getTrspOpPlanDateTrmStrtTime());
        String diagramEnd = formattedDate + " " + BaseUtil.formatTime(vehicleResource.getTrspOpPlanDateTrmEndTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(diagramStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(diagramEnd, formatter);

        if (endDateTime.isBefore(startDateTime)) {
            endDateTime = endDateTime.plusDays(1);
        }

        List<String> times = new ArrayList<>();
        if (isCarrier) {
            String requestDepFromTime = startDateTime.minusHours(1).format(formatter);
            String requestDepToTime = startDateTime.format(formatter);

            String requestArrivalFromTime = endDateTime.format(formatter);
            String requestArrivalToTime = endDateTime.plusHours(1).format(formatter);
            times.add(requestDepFromTime);
            times.add(requestDepToTime);
            times.add(requestArrivalFromTime);
            times.add(requestArrivalToTime);
            return times;
        } else {
            String requestDepFromTime = startDateTime.minusHours(2).format(formatter);
            String requestDepToTime = startDateTime.format(formatter);
            times.add(requestDepFromTime);
            times.add(requestDepToTime);
            return times;
        }
    }

    /**
     * IX 040を登録する。
     *
     * @param endPointIX                エンドポイント
     * @param headers                   HTTPヘッダー
     * @param trspInstructionId         輸送指示ID
     * @param operationId               操作ID
     * @param transOrder                トランスオーダー
     * @param vehicleResource           車両リソース
     * @param tractorGiai               トラクターのGIAI
     * @param vehicleDiagramItemTrailer 車両ダイアグラムアイテムトレーラー
     */
    private void registerIX040(String endPointIX, HttpHeaders headers, String trspInstructionId, String operationId,
        TransOrder transOrder,
        VehicleAvbResourceResponseDTO vehicleResource, String tractorGiai,
        VehicleDiagramItemTrailerResponseDTO vehicleDiagramItemTrailer) {
        VehicleAvbResourceRequestDTO vehicleAvbResourceRequestDTO = new VehicleAvbResourceRequestDTO();
        vehicleAvbResourceRequestDTO.setOperationId(operationId);
        vehicleAvbResourceRequestDTO.setTrspOpEndAreaCtyJisCd(vehicleResource.getTrspOpEndAreaCtyJisCd());
        vehicleAvbResourceRequestDTO.setTrspOpDateTrmStrtDate(vehicleResource.getTrspOpDateTrmStrtDate());
        vehicleAvbResourceRequestDTO.setTrspOpDateTrmEndDate(vehicleResource.getTrspOpDateTrmStrtDate());
        vehicleAvbResourceRequestDTO.setTrspOpPlanDateTrmStrtTime(vehicleResource.getTrspOpPlanDateTrmStrtTime());
        vehicleAvbResourceRequestDTO.setTrspOpPlanDateTrmEndTime(vehicleResource.getTrspOpPlanDateTrmEndTime());
        vehicleAvbResourceRequestDTO.setTrspOpEndAreaLineOneTxt(
            vehicleAvbResourceRequestDTO.getTrspOpEndAreaLineOneTxt());
        vehicleAvbResourceRequestDTO.setTrspOpStrtAreaCtyJisCd(vehicleResource.getTrspOpStrtAreaCtyJisCd());
        vehicleAvbResourceRequestDTO.setTrspOpStrtAreaLineOneTxt(
            vehicleAvbResourceRequestDTO.getTrspOpStrtAreaLineOneTxt());
        String shipperCid = transOrder.getShipperOperatorCode();
        String carrierCid = transOrder.getCarrierOperatorCode();
        if (transOrder.getCarrier2OperatorCode() != null) {
            carrierCid = transOrder.getCarrier2OperatorCode();
        }
        Long departureMh = transOrder.getDepartureFrom();

        List<String> departureSpaceList = new ArrayList<>();
        List<String> arrivalSpaceList = new ArrayList<>();

        Long arrivalMh = transOrder.getArrivalTo();
        Long vehicleDiagramItemId = vehicleResource.getCarInfoId();
        if (vehicleDiagramItemTrailer != null && vehicleDiagramItemTrailer.getVehicleDiagramItemId() != null) {
            vehicleDiagramItemId = vehicleDiagramItemTrailer.getVehicleDiagramItemId();
        }
        try {
            if (null != vehicleDiagramItemId) {
                List<VehicleDiagramMobilityHubResponseDTO> vehicleDiagramList = vehicleDiagramMobilityHubCustomRepository.findAllByOperationIdAndVehicleType(
                    vehicleDiagramItemId, 2
                );
                if (!BaseUtil.isEmpty(vehicleDiagramList, null, 1)) {
                    departureSpaceList = vehicleDiagramList.stream().filter(vehicleDiagramMobilityHubResponseDTO ->
                            Objects.nonNull(vehicleDiagramMobilityHubResponseDTO.getType())
                                && 0 == vehicleDiagramMobilityHubResponseDTO.getType())
                        .map(VehicleDiagramMobilityHubResponseDTO::getSlotId).toList();
                    arrivalSpaceList = vehicleDiagramList.stream().filter(vehicleDiagramMobilityHubResponseDTO ->
                            Objects.nonNull(vehicleDiagramMobilityHubResponseDTO.getType())
                                && 1 == vehicleDiagramMobilityHubResponseDTO.getType())
                        .map(VehicleDiagramMobilityHubResponseDTO::getSlotId).toList();
                }
            }

            List<String> times = getRequestTimeCarrier(vehicleResource, true);
            boolean isShipper1 = "992000001".equals(transOrder.getShipperOperatorCode());
            String endPointIX040 = UriComponentsBuilder.fromUriString(
                    endPointIX + APIConstant.IX.OPERATION_PLANS)
                .queryParam("trsp_instruction_id", trspInstructionId)
                .queryParam("shipper_cid", BaseUtil.getShipperCid(shipperCid))
                .queryParam("carrier_cid", BaseUtil.getCarrierCid(carrierCid))
                .queryParam("departure_mh", BaseUtil.getGlnStart(String.valueOf(departureMh)))
                .queryParam("departure_mh_space_list", isShipper1 ? "A023" : "B021")
                .queryParam("arrival_mh", BaseUtil.getGlnEnd(String.valueOf(arrivalMh)))
                .queryParam("arrival_mh_space_list", isShipper1 ? "C024" : "D022")
                .queryParam("tractor_giai", BaseUtil.getCarrierTractorGiaList(carrierCid))
                .queryParam("dep_req_from_time", times.get(0))
                .queryParam("dep_req_to_time", times.get(1))
                .queryParam("arv_req_from_time", times.get(2))
                .queryParam("arv_req_to_time", times.get(3))
                .build()
                .encode()
                .toUriString();

            HttpEntity<VehicleAvbResourceRequestDTO> requestEntity =
                new HttpEntity<>(vehicleAvbResourceRequestDTO, headers);

            log.info("DEBUG: IX API-040 url: " + endPointIX040);
            log.info("DEBUG: IX API-040 body: " + BaseUtil.makeString(vehicleAvbResourceRequestDTO));

            ResponseEntity<String> response = restTemplate.exchange(
                endPointIX040,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("DEBUG: IX API-040 Successfully called IX 040 for operationId: {}, response: {}", operationId,
                response);
        } catch (Exception e) {
            log.error("DEBUG: IX API-040 Error called IX 040 for operationId: {}, response: {}", operationId,
                e.getMessage());
        }
    }

    /**
     * 車両リソースを取得する。
     *
     * @param vehicleAvbResourceId 車両可用リソースID
     * @return 車両可用リソースのDTO
     */
    private VehicleAvbResourceResponseDTO getVehicleResource(Long vehicleAvbResourceId) {
        String vehicleAvbResourceSql = "SELECT " +
            "trsp_op_strt_area_line_one_txt, " +
            "trsp_op_strt_area_cty_jis_cd, " +
            "trsp_op_date_trm_strt_date, " +
            "trsp_op_plan_date_trm_strt_time, " +
            "trsp_op_end_area_line_one_txt, " +
            "trsp_op_end_area_cty_jis_cd, " +
            "trsp_op_date_trm_end_date, " +
            "trsp_op_plan_date_trm_end_time, " +
            "car_info_id " +
            "FROM vehicle_avb_resource WHERE id = ?";

        Object[] result = (Object[]) entityManager.createNativeQuery(vehicleAvbResourceSql)
            .setParameter(1, vehicleAvbResourceId)
            .getSingleResult();
        return VehicleAvbResourceResponseDTO.fromResult(result);
    }

    /**
     * IX 043を登録する。
     *
     * @param endPointIX      エンドポイント
     * @param headers         HTTPヘッダー
     * @param operationId     操作ID
     * @param transOrder      トランスオーダー
     * @param vehicleResource 車両リソース
     */
    private void registerIX043(String endPointIX, HttpHeaders headers, String operationId, TransOrder transOrder,
        VehicleAvbResourceResponseDTO vehicleResource) {
        OperationPlansNotifyDTO operationPlansNotifyDTO = new OperationPlansNotifyDTO();
        TrspAbillityLineItemResponseDTO trspAbillityLineItemResponseDTO = getTrspAbilityLineItem(
            vehicleResource.getCarInfoId());
        getOperationPlansNotifyDTO(operationPlansNotifyDTO, trspAbillityLineItemResponseDTO);
        String endPointIX043 = UriComponentsBuilder.fromUriString(
                endPointIX + APIConstant.IX.OPERATION_PLANS + "/" + operationId + "/notify")
            .queryParam("shipper_cid", BaseUtil.getShipperCid(transOrder.getShipperOperatorCode()))
            .queryParam("carrier_cid", BaseUtil.getCarrierCid(transOrder.getCarrierOperatorCode()))
            .build()
            .toString();

        HttpEntity<OperationPlansNotifyDTO> requestEntity =
            new HttpEntity<>(operationPlansNotifyDTO, headers);
        try {
            log.info("DEBUG: IX API-042 url: " + endPointIX043);
            log.info("DEBUG: IX API-042 body: " + BaseUtil.makeString(operationPlansNotifyDTO));
            ResponseEntity<String> response = restTemplate.exchange(
                endPointIX043,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call IX 042: " + response);
        } catch (Exception e) {
            log.error("DEBUG: IX API-042 ERROR call IX 043: {}", e.getMessage());
        }
    }

    /**
     * 輸送能力ラインアイテムを取得する。
     *
     * @param carInfoId 車両情報ID
     * @return 輸送能力ラインアイテムのDTO
     */
    private TrspAbillityLineItemResponseDTO getTrspAbilityLineItem(Long carInfoId) {
        TrspAbillityLineItemResponseDTO trspAbillityLineItemResponseDTO = new TrspAbillityLineItemResponseDTO();
        try {
            if (carInfoId != null) {
                String carInfoSql = "SELECT trsp_ability_line_item_id " +
                    "FROM car_info WHERE id = ?";

                Long trspAbillityLineItemId = ((Number) entityManager.createNativeQuery(carInfoSql)
                    .setParameter(1, carInfoId)
                    .getSingleResult()).longValue();

                if (trspAbillityLineItemId != null) {
                    String trspAbilityLineItem = "SELECT " +
                        "trsp_cli_prty_head_off_id, " +
                        "trsp_cli_prty_brnc_off_id, " +
                        "trsp_cli_prty_name_txt, " +
                        "road_carr_depa_sped_org_id, " +
                        "road_carr_depa_sped_org_name_txt, " +
                        "trsp_cli_tel_cmm_cmp_num_txt, " +
                        "road_carr_arr_sped_org_id, " +
                        "road_carr_arr_sped_org_name_txt, " +
                        "logs_srvc_prv_prty_head_off_id, " +
                        "logs_srvc_prv_prty_brnc_off_id, " +
                        "logs_srvc_prv_prty_name_txt, " +
                        "logs_srvc_prv_sct_sped_org_id, " +
                        "logs_srvc_prv_sct_sped_org_name_txt, " +
                        "logs_srvc_prv_sct_prim_cnt_pers_name_txt, " +
                        "logs_srvc_prv_sct_tel_cmm_cmp_num_txt " +
                        "FROM trsp_ability_line_item WHERE id = ?";

                    List resultList = entityManager.createNativeQuery(trspAbilityLineItem)
                        .setParameter(1, trspAbillityLineItemId)
                        .getResultList();

                    if (resultList.isEmpty()) {
                        return null;
                    }

                    Object[] resultLine = (Object[]) resultList.get(0);
                    trspAbillityLineItemResponseDTO = TrspAbillityLineItemResponseDTO.fromResult(resultLine);
                }
            }
        } catch (Exception e) {
            log.error("ERROR call IX 043 get trsp ability line item: {}", e.getMessage());
        }
        return trspAbillityLineItemResponseDTO;
    }

    /**
     * 操作計画通知DTOを取得する。
     *
     * @param operationPlansNotifyDTO         操作計画通知DTO
     * @param trspAbillityLineItemResponseDTO 輸送能力ラインアイテムのDTO
     */
    private void getOperationPlansNotifyDTO(OperationPlansNotifyDTO operationPlansNotifyDTO,
        TrspAbillityLineItemResponseDTO trspAbillityLineItemResponseDTO) {
        RoadCarrNotifyDTO roadCarrNotifyDTO = new RoadCarrNotifyDTO();
        LogisticsServiceProviderNotifyDTO logisticsServiceProviderDTO = new LogisticsServiceProviderNotifyDTO();
        if (trspAbillityLineItemResponseDTO != null) {
            roadCarrNotifyDTO.setRoadCarrArrSpedOrgId(trspAbillityLineItemResponseDTO.getRoadCarrArrSpedOrgId());
            roadCarrNotifyDTO.setRoadCarrDepaSpedOrgId(trspAbillityLineItemResponseDTO.getRoadCarrDepaSpedOrgId());
            roadCarrNotifyDTO.setRoadCarrArrSpedOrgNameTxt(
                trspAbillityLineItemResponseDTO.getRoadCarrArrSpedOrgNameTxt());
            roadCarrNotifyDTO.setRoadCarrDepaSpedOrgNameTxt(
                trspAbillityLineItemResponseDTO.getRoadCarrDepaSpedOrgNameTxt());
            roadCarrNotifyDTO.setTrspCliPrtyHeadOffId(trspAbillityLineItemResponseDTO.getTrspCliPrtyHeadOffId());
            roadCarrNotifyDTO.setTrspCliPrtyBrncOffId(trspAbillityLineItemResponseDTO.getTrspCliPrtyBrncOffId());
            roadCarrNotifyDTO.setTrspCliPrtyNameTxt(trspAbillityLineItemResponseDTO.getTrspCliPrtyNameTxt());
            roadCarrNotifyDTO.setTrspCliTelCmmCmpNumTxt(trspAbillityLineItemResponseDTO.getTrspCliTelCmmCmpNumTxt());

            logisticsServiceProviderDTO.setLogsSrvcPrvPrtyBrncOffId(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvPrtyBrncOffId());
            logisticsServiceProviderDTO.setLogsSrvcPrvPrtyHeadOffId(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvPrtyHeadOffId());
            logisticsServiceProviderDTO.setLogsSrvcPrvPrtyNameTxt(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvPrtyNameTxt());
            logisticsServiceProviderDTO.setLogsSrvcPrvSctPrimCntPersNameTxt(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvSctPrimCntPersNameTxt());
            logisticsServiceProviderDTO.setLogsSrvcPrvSctSpedOrgId(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvSctSpedOrgId());
            logisticsServiceProviderDTO.setLogsSrvcPrvSctTelCmmCmpNumTxt(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvSctTelCmmCmpNumTxt());
            logisticsServiceProviderDTO.setLogsSrvcPrvSctSpedOrgNameTxt(
                trspAbillityLineItemResponseDTO.getLogsSrvcPrvSctSpedOrgNameTxt());
        }
        operationPlansNotifyDTO.setRoadCarrDTO(roadCarrNotifyDTO);
        operationPlansNotifyDTO.setLogisticsServiceProviderDTO(logisticsServiceProviderDTO);
    }

    /**
     * IX 050を登録する。
     *
     * @param endPointIX                エンドポイント
     * @param headers                   HTTPヘッダー
     * @param transOrder                トランスオーダー
     * @param vehicleResource           車両リソース
     * @param tractorGiai               トラクターのGIAI
     * @param trailerGiaiList           トレーラーのGIAIリスト
     * @param vehicleDiagramItemTrailer 車両ダイアグラムアイテムトレーラー
     */
    private void registerIX050(String endPointIX, HttpHeaders headers, TransOrder transOrder,
        VehicleAvbResourceResponseDTO vehicleResource, String tractorGiai, String trailerGiaiList,
        VehicleDiagramItemTrailerResponseDTO vehicleDiagramItemTrailer) {
        String shipperCode = transOrder.getShipperOperatorCode();
        String carrierCode = transOrder.getCarrierOperatorCode();
        String trspInstructionId = transOrder.getCnsLineItemByDateId().toString();

        List<String> times = getRequestTimeCarrier(vehicleResource, false);

        String recipientCode = getRecipientCode(shipperCode);
        String tractorGiaiT = getTractorGiai(shipperCode);
        String trailerGiaiListT = getTrailerGiai(shipperCode);

        String endPointIX050 = UriComponentsBuilder.fromUriString(
                endPointIX + IX.TRANSPORT_PLANS + "/" + trspInstructionId)
            .queryParam("is_shipper", true)
            .queryParam("shipper_cid", BaseUtil.getShipperCid(shipperCode))
            .queryParam("carrier_cid", BaseUtil.getCarrierCid(carrierCode))
            .queryParam("recipient_cid", recipientCode)
            .queryParam("tractor_giai", tractorGiaiT)
            .queryParam("trailer_giai_list", trailerGiaiListT)
            .queryParam("req_from_time", times.get(0))
            .queryParam("req_to_time", times.get(1))
            .queryParam("mh", BaseUtil.getGlnStart(transOrder.getDepartureFrom().toString()))
            .build()
            .encode()
            .toUriString();
        TrspPlanDTO trspPlanDTO = new TrspPlanDTO();
        trspPlanDTO.setTrspInstructionId(transOrder.getTrspInstructionId());
        HttpEntity<TrspPlanDTO> requestEntity =
            new HttpEntity<>(trspPlanDTO, headers);
        try {
            log.info("DEBUG: IX API-050 url: " + endPointIX050);
            log.info("DEBUG: IX API-050 body: " + BaseUtil.makeString(trspPlanDTO));
            ResponseEntity<String> response = restTemplate.exchange(
                endPointIX050,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call IX 050: " + response);
        } catch (Exception e) {
            log.error("ERROR call IX 050: {}", e.getMessage());
        }
    }

    /**
     * 受取人コードを取得する。
     *
     * @param shipperCode 出荷者コード
     * @return 受取人コード
     */
    private String getRecipientCode(String shipperCode) {
        return "992000001".equals(shipperCode) ? "993000001" : "993000002";
    }

    /**
     * トラクターのGIAIを取得する。
     *
     * @param shipperCode 出荷者コード
     * @return トラクターのGIAI
     */
    private String getTractorGiai(String shipperCode) {
        return "992000001".equals(shipperCode) ? "8004992000001000000000000000000001"
            : "8004992000002000000000000000000001";
    }

    /**
     * トレーラーのGIAIを取得する。
     *
     * @param shipperCode 出荷者コード
     * @return トレーラーのGIAI
     */
    private String getTrailerGiai(String shipperCode) {
        return "992000001".equals(shipperCode) ? "8004992000001000000000000000000003"
            : "8004992000002000000000000000000003";
    }

    /**
     * IX 052を登録する。
     *
     * @param endPointIX                  エンドポイント
     * @param headers                     HTTPヘッダー
     * @param trspInstructionId           輸送指示ID
     * @param trspPlanLineItemResponseDTO 輸送計画ラインアイテムのDTO
     * @param transOrder                  トランスオーダー
     */
    private void registerIX052(String endPointIX, HttpHeaders headers, String trspInstructionId,
        TrspPlanLineItemResponseDTO trspPlanLineItemResponseDTO, TransOrder transOrder) {
        String shipperCode = transOrder.getShipperOperatorCode();
        String recipientCode = getRecipientCode(shipperCode);

        String endPointIX052 = UriComponentsBuilder.fromUriString(
                endPointIX + APIConstant.IX.TRANSPORT_PLANS + "/" + trspInstructionId + "/notify")
            .queryParam("shipper_cid", BaseUtil.getShipperCid(transOrder.getShipperOperatorCode()))
            .queryParam("carrier_cid", BaseUtil.getCarrierCid(transOrder.getCarrierOperatorCode()))
            .queryParam("trsp_instruction_id", trspInstructionId)
            .build()
            .encode()
            .toUriString();
        TrspPlanLineItemResponseDTO responseDTO = new TrspPlanLineItemResponseDTO();
        TrspPlansNotifyRequestDTO tranTrspPlansNotifyRequestDTO;
        tranTrspPlansNotifyRequestDTO = getTrspPlansNotifyRequestDTO(trspInstructionId, responseDTO);
        tranTrspPlansNotifyRequestDTO.setCneePrty(new ConsigneePartyDTO());
        tranTrspPlansNotifyRequestDTO.getCneePrty().setCneePrtyHeadOffId(recipientCode);
        ShipToPartyDTO dtoTo = new ShipToPartyDTO();
        dtoTo.setGlnPrtyId(BaseUtil.getGlnStart(transOrder.getDepartureFrom().toString()));

        ShipFromPartyDTO dtoFrom = new ShipFromPartyDTO();
        dtoFrom.setGlnPrtyId(BaseUtil.getGlnEnd(transOrder.getArrivalTo().toString()));
        tranTrspPlansNotifyRequestDTO.setShipFromPrty(dtoFrom);
        tranTrspPlansNotifyRequestDTO.setShipToPrty(dtoTo);

        HttpEntity<TrspPlansNotifyRequestDTO> requestEntity =
            new HttpEntity<>(tranTrspPlansNotifyRequestDTO, headers);
        try {
            log.info("DEBUG: IX API-052 url: " + endPointIX052);
            log.info("DEBUG: IX API-052 body: " + BaseUtil.makeString(tranTrspPlansNotifyRequestDTO));
            ResponseEntity<String> response = restTemplate.exchange(
                endPointIX052,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call IX 052: " + response);
        } catch (Exception e) {
            log.error("ERROR call IX 052: {}", e.getMessage());
        }
    }

    /**
     * 輸送計画通知リクエストDTOを取得する。
     *
     * @param trspInstructionId           輸送指示ID
     * @param trspPlanLineItemResponseDTO 輸送計画ラインアイテムのDTO
     * @return 輸送計画通知リクエストDTO
     */
    private TrspPlansNotifyRequestDTO getTrspPlansNotifyRequestDTO(String trspInstructionId, TrspPlanLineItemResponseDTO
        trspPlanLineItemResponseDTO) {
        TrspPlansNotifyRequestDTO trspPlansNotifyRequestDTO = new TrspPlansNotifyRequestDTO();
        // TrspIsrDTO
        TrspIsrDTO trspIsrDTO = new TrspIsrDTO();
        trspIsrDTO.setTrspInstructionId(trspInstructionId);
        if (trspPlanLineItemResponseDTO.getTrspInstructionDateSubmDttm() != null) {
            trspIsrDTO.setTrspInstructionDateSubmDttm(DateTimeMapper.dateToString(trspPlanLineItemResponseDTO
                .getTrspInstructionDateSubmDttm().toLocalDate()));
        }
        trspIsrDTO.setInvNumId(trspPlanLineItemResponseDTO.getInvNumId());
        trspIsrDTO.setCmnInvNumId(trspPlanLineItemResponseDTO.getCmnInvNumId());
        trspPlansNotifyRequestDTO.setTrspIsr(trspIsrDTO);

        // TrspSrvcDTO
        TrspSrvcDTO trspSrvcDTO = new TrspSrvcDTO();
        if (trspSrvcDTO.getApedArrFromDate() != null) {
            trspSrvcDTO.setApedArrFromDate(DateTimeMapper.dateToString(trspPlanLineItemResponseDTO
                .getApedArrFromDate().toLocalDate()));
        }
        if (trspPlanLineItemResponseDTO.getApedArrToDate() != null) {
            trspSrvcDTO.setApedArrToDate(DateTimeMapper.dateToString(trspPlanLineItemResponseDTO
                .getApedArrToDate().toLocalDate()));
        }
        if (trspPlanLineItemResponseDTO.getApedArrFromTimePrfmDttm() != null) {
            trspSrvcDTO.setApedArrFromTimePrfmDttm(DateTimeMapper.timeToString(trspPlanLineItemResponseDTO
                .getApedArrFromTimePrfmDttm().toLocalTime()));
        }
        trspSrvcDTO.setApedArrTimeTrmsSrvcRqrmCd(trspPlanLineItemResponseDTO.getApedArrTimeTrmsSrvcRqrmCd());
        if (trspPlanLineItemResponseDTO.getApedArrToTimePrfmDttm() != null) {
            trspSrvcDTO.setApedArrToTimePrfmDttm(DateTimeMapper.timeToString(trspPlanLineItemResponseDTO
                .getApedArrToTimePrfmDttm().toLocalTime()));
        }
        trspSrvcDTO.setTrspMeansTypCd(trspPlanLineItemResponseDTO.getTrspMeansTypCd());
        trspSrvcDTO.setTrspSrvcTypCd(trspPlanLineItemResponseDTO.getTrspSrvcTypCd());
        trspSrvcDTO.setRoadCarrSrvcTypCd(trspPlanLineItemResponseDTO.getRoadCarrSrvcTypCd());
        trspSrvcDTO.setTrspRootPrioCd(trspPlanLineItemResponseDTO.getTrspRootPrioCd());
        trspSrvcDTO.setCarClsPrioCd(trspPlanLineItemResponseDTO.getCarClsPrioCd());
        trspSrvcDTO.setClsOfCargInSrvcRqrmCd(trspPlanLineItemResponseDTO.getClsOfCargInSrvcRqrmCd());
        trspSrvcDTO.setClsOfPkgUpSrvcRqrmCd(trspPlanLineItemResponseDTO.getClsOfPkgUpSrvcRqrmCd());
        trspSrvcDTO.setPyrClsSrvcRqrmCd(trspPlanLineItemResponseDTO.getPyrClsSrvcRqrmCd());
        trspSrvcDTO.setTrmsOfMixLoadCndCd(trspPlanLineItemResponseDTO.getTrmsOfMixLoadCndCd());
        if (trspPlanLineItemResponseDTO.getDsedCllFromDate() != null) {
            trspSrvcDTO.setDsedCllFromDate(DateTimeMapper.dateToString(trspPlanLineItemResponseDTO
                .getDsedCllFromDate().toLocalDate()));
        }
        if (trspPlanLineItemResponseDTO.getDsedCllToDate() != null) {
            trspSrvcDTO.setDsedCllToDate(DateTimeMapper.dateToString(trspPlanLineItemResponseDTO
                .getDsedCllToDate().toLocalDate()));
        }
        if (trspPlanLineItemResponseDTO.getDsedCllFromTime() != null) {
            trspSrvcDTO.setDsedCllFromTime(DateTimeMapper.timeToString(trspPlanLineItemResponseDTO
                .getDsedCllFromTime().toLocalTime()));
        }
        if (trspPlanLineItemResponseDTO.getDsedCllToTime() != null) {
            trspSrvcDTO.setDsedCllToTime(DateTimeMapper.timeToString(trspPlanLineItemResponseDTO
                .getDsedCllToTime().toLocalTime()));
        }
        trspSrvcDTO.setDsedCllTimeTrmsSrvcRqrmCd(trspPlanLineItemResponseDTO.getDsedCllTimeTrmsSrvcRqrmCd());
        trspSrvcDTO.setTrmsOfMixLoadTxt(trspPlanLineItemResponseDTO.getTrmsOfMixLoadTxt());
        trspSrvcDTO.setTrspSrvcNoteOneTxt(trspPlanLineItemResponseDTO.getTrspSrvcNoteOneTxt());
        trspSrvcDTO.setTrspSrvcNoteTwoTxt(trspPlanLineItemResponseDTO.getTrspSrvcNoteTwoTxt());
        trspSrvcDTO.setFreightRate(String.valueOf(trspPlanLineItemResponseDTO.getFreightRate()));
        trspPlansNotifyRequestDTO.setTrspSrvc(trspSrvcDTO);

        return trspPlansNotifyRequestDTO;
    }

    /**
     * 輸送計画ラインアイテムを取得する。
     *
     * @param cnsLineItemResponseDTO CNSラインアイテムのDTO
     * @return 輸送計画ラインアイテムのDTO
     */
    private TrspPlanLineItemResponseDTO getTrspPlanLineItem(
        CnsLineItemResponseDTO cnsLineItemResponseDTO) {
        if (cnsLineItemResponseDTO == null || cnsLineItemResponseDTO.getTrspPlanLineItemId() == null) {
            return null;
        }
        String trspPlanLineItemSql = "SELECT " +
            "trsp_instruction_date_subm_dttm, " +
            "inv_num_id, " +
            "cmn_inv_num_id, " +
            "trsp_means_typ_cd, " +
            "trsp_srvc_typ_cd, " +
            "road_carr_srvc_typ_cd, " +
            "trsp_root_prio_cd, " +
            "car_cls_prio_cd, " +
            "cls_of_carg_in_srvc_rqrm_cd, " +
            "cls_of_pkg_up_srvc_rqrm_cd, " +
            "pyr_cls_srvc_rqrm_cd, " +
            "trms_of_mix_load_cnd_cd, " +
            "dsed_cll_from_date, " +
            "dsed_cll_to_date, " +
            "dsed_cll_from_time, " +
            "dsed_cll_to_time, " +
            "dsed_cll_time_trms_srvc_rqrm_cd, " +
            "aped_arr_from_date, " +
            "aped_arr_to_date, " +
            "aped_arr_from_time_prfm_dttm, " +
            "aped_arr_time_trms_srvc_rqrm_cd, " +
            "aped_arr_to_time_prfm_dttm, " +
            "trms_of_mix_load_txt, " +
            "trsp_srvc_note_one_txt, " +
            "trsp_srvc_note_two_txt, " +
            "freight_rate, " +
            "car_cls_of_size_cd, " +
            "car_cls_of_shp_cd, " +
            "car_cls_of_tlg_lftr_exst_cd, " +
            "car_cls_of_wing_body_exst_cd, " +
            "car_cls_of_rfg_exst_cd, " +
            "trms_of_lwr_tmp_meas, " +
            "trms_of_upp_tmp_meas, " +
            "car_cls_of_crn_exst_cd, " +
            "car_rmk_about_eqpm_txt, " +
            "del_note_id, " +
            "shpm_num_id, " +
            "rced_ord_num_id, " +
            "istd_totl_pcks_quan, " +
            "num_unt_cd, " +
            "istd_totl_weig_meas, " +
            "weig_unt_cd, " +
            "istd_totl_vol_meas, " +
            "vol_unt_cd, " +
            "istd_totl_untl_quan, " +
            "cnsg_prty_head_off_id, " +
            "cnsg_prty_brnc_off_id, " +
            "cnsg_prty_name_txt, " +
            "cnsg_sct_sped_org_id, " +
            "cnsg_sct_sped_org_name_txt, " +
            "cnsg_tel_cmm_cmp_num_txt, " +
            "cnsg_pstl_adrs_line_one_txt, " +
            "cnsg_pstc_cd, " +
            "trsp_rqr_prty_head_off_id, " +
            "trsp_rqr_prty_brnc_off_id, " +
            "trsp_rqr_prty_name_txt, " +
            "trsp_rqr_sct_sped_org_id, " +
            "trsp_rqr_sct_sped_org_name_txt, " +
            "trsp_rqr_sct_tel_cmm_cmp_num_txt, " +
            "trsp_rqr_pstl_adrs_line_one_txt, " +
            "trsp_rqr_pstc_cd, " +
            "cnee_prty_head_off_id, " +
            "cnee_prty_brnc_off_id, " +
            "cnee_prty_name_txt, " +
            "cnee_sct_id, " +
            "cnee_sct_name_txt, " +
            "cnee_prim_cnt_pers_name_txt, " +
            "cnee_tel_cmm_cmp_num_txt, " +
            "cnee_pstl_adrs_line_one_txt, " +
            "cnee_pstc_cd, " +
            "logs_srvc_prv_prty_head_off_id, " +
            "logs_srvc_prv_prty_brnc_off_id, " +
            "logs_srvc_prv_prty_name_txt, " +
            "logs_srvc_prv_sct_sped_org_id, " +
            "logs_srvc_prv_sct_sped_org_name_txt, " +
            "logs_srvc_prv_sct_prim_cnt_pers_name_txt, " +
            "logs_srvc_prv_sct_tel_cmm_cmp_num_txt, " +
            "trsp_cli_prty_head_off_id, " +
            "trsp_cli_prty_brnc_off_id, " +
            "trsp_cli_prty_name_txt, " +
            "trsp_cli_tel_cmm_cmp_num_txt, " +
            "fret_clim_to_prty_head_off_id, " +
            "fret_clim_to_prty_brnc_off_id, " +
            "fret_clim_to_prty_name_txt, " +
            "fret_clim_to_sct_sped_org_id, " +
            "fret_clim_to_sct_sped_org_name_txt " +
            "FROM trsp_plan_line_item WHERE id = ?";

        List resultList = entityManager.createNativeQuery(trspPlanLineItemSql)
            .setParameter(1, cnsLineItemResponseDTO.getTrspPlanLineItemId())
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Object[] result = (Object[]) resultList.get(0);
        return TrspPlanLineItemResponseDTO.fromResult(result);
    }

    /**
     * CNSラインアイテムを取得する。
     *
     * @param transOrder トランスオーダー
     * @return CNSラインアイテムのDTO
     */
    private CnsLineItemResponseDTO getCnsLineItem(TransOrder transOrder) {

        if (transOrder == null || transOrder.getCnsLineItemId() == null) {
            return null;
        }
        Long cnsLineItemId = transOrder.getCnsLineItemId();
        String cnsLineItemSql = "SELECT " +
            "line_item_num_id, " +
            "sev_ord_num_id, " +
            "cnsg_crg_item_num_id, " +
            "buy_assi_item_cd, " +
            "sell_assi_item_cd, " +
            "wrhs_assi_item_cd, " +
            "item_name_txt, " +
            "gods_idcs_in_ots_pcke_name_txt, " +
            "num_of_istd_untl_quan, " +
            "num_of_istd_quan, " +
            "sev_num_unt_cd, " +
            "istd_pcke_weig_meas, " +
            "sev_weig_unt_cd, " +
            "istd_pcke_vol_meas, " +
            "sev_vol_unt_cd, " +
            "istd_quan_meas, " +
            "cnte_num_unt_cd, " +
            "dcpv_trpn_pckg_txt, " +
            "pcke_frm_cd, " +
            "pcke_frm_name_cd, " +
            "crg_hnd_trms_spcl_isrs_txt, " +
            "glb_retb_asse_id, " +
            "totl_rti_quan_quan, " +
            "chrg_of_pcke_ctrl_num_unt_amnt, " +
            "trsp_plan_line_item_id " +
            "FROM cns_line_item WHERE id = ?";

        List resultList = entityManager.createNativeQuery(cnsLineItemSql)
            .setParameter(1, cnsLineItemId)
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Object[] result = (Object[]) resultList.get(0);

        return CnsLineItemResponseDTO.fromResult(result);
    }

    /**
     * 車両ダイアグラムアイテムトレーラーのレスポンスDTOを取得する。
     *
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレーラーID
     * @return 車両ダイアグラムアイテムトレーラーのDTO
     */
    private VehicleDiagramItemTrailerResponseDTO getVehicleDiagramItemTrailerResponseDTO(
        Long vehicleDiagramItemTrailerId) {
        String vehicleDiagramItemTrailerSql = "SELECT " +
            "vehicle_diagram_item_id, "
            + "vehicle_diagram_id, "
            + "departure_time, "
            + "arrival_time "
            + "FROM c_vehicle_diagram_item_trailer WHERE id = ?";

        List<Object[]> resultList = entityManager.createNativeQuery(vehicleDiagramItemTrailerSql)
            .setParameter(1, vehicleDiagramItemTrailerId)
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.stream()
            .map(VehicleDiagramItemTrailerResponseDTO::fromResult)
            .findFirst()
            .orElse(null);
    }

    /**
     * ダイアグラムアイテムのステータスを更新する。
     *
     * @param transOrder トランスオーダー
     */
    private void updateDiagramItemStatus(TransOrder transOrder) {
        if (transOrder != null && !BaseUtil.isNull(String.valueOf(transOrder.getVehicleDiagramItemId()))) {
            String urlPropertiesTime = urlProperties.getDomainCarrier()
                .concat(APIConstant.CarrierDiagramItem.UPDATE_DIAGRAM_ITEM_STATUS);
            DiagramItemUpdateStatusRequest request = new DiagramItemUpdateStatusRequest();
            request.setStatus(ParamConstant.Status.WAITING_TO_START);
            request.setId(transOrder.getVehicleDiagramItemId());
            HttpHeaders headers = new HttpHeaders();
            headers.set(APIConstant.API_KEY, authProperties.getApiKey());
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DiagramItemUpdateStatusRequest> requestEntity =
                new HttpEntity<>(request, headers);
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                    urlPropertiesTime,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
                );
                log.info("SUCCESS call Carrier Service : " + response);
            } catch (Exception e) {
                log.error("ERROR call Carrier Service : {}", e.getMessage());
            }
        }
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
