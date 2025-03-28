package jp.co.nlj.ix.service.impl;

import static jp.co.nlj.ix.mapper.DateTimeMapper.stringToLocalDate;
import static jp.co.nlj.ix.mapper.DateTimeMapper.stringToLocalTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import jp.co.nlj.ix.constant.MapperConstants.TransOrderError;
import jp.co.nlj.ix.constant.MapperConstants.TrspPlanItem;
import jp.co.nlj.ix.constant.MessageConstant.ShipperOperationPlans;
import jp.co.nlj.ix.constant.ParamConstant.Status;
import jp.co.nlj.ix.constant.ParamConstant.TransOrderStatus;
import jp.co.nlj.ix.constant.ParamConstant.TransOrderType;
import jp.co.nlj.ix.domain.CnsLineItem;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.domain.ShipCutOffInfo;
import jp.co.nlj.ix.domain.ShipFreeTimeInfo;
import jp.co.nlj.ix.domain.ShipFromPrty;
import jp.co.nlj.ix.domain.ShipFromPrtyRqrm;
import jp.co.nlj.ix.domain.ShipToPrty;
import jp.co.nlj.ix.domain.ShipToPrtyRqrm;
import jp.co.nlj.ix.domain.TrspPlanLineItem;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.domain.carrier.CarrierOperator;
import jp.co.nlj.ix.domain.shipper.TransOrder;
import jp.co.nlj.ix.dto.VehicleAvbResourceItemSnapshot;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.CarrierOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.CarrierOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsLineItemDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipCutOffInfoDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFreeTimeInfoDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFromPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFromPrtyRqrmDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipToPrtyRqrmDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspPlanLineItemDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspSrvcDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.CarrierOperatorPlansRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansInsertResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansUpdateResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;
import jp.co.nlj.ix.mapper.CnsLineItemByDateMapper;
import jp.co.nlj.ix.mapper.CnsLineItemMapper;
import jp.co.nlj.ix.mapper.ShipCutOffInfoMapper;
import jp.co.nlj.ix.mapper.ShipFreeTimeInfoMapper;
import jp.co.nlj.ix.mapper.ShipFromPrtyMapper;
import jp.co.nlj.ix.mapper.ShipFromPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.ShipToPrtyMapper;
import jp.co.nlj.ix.mapper.ShipToPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.TrspPlanLineItemMapper;
import jp.co.nlj.ix.mapper.VehicleAvbResourceItemMapper;
import jp.co.nlj.ix.repository.CarrierOperatorRepository;
import jp.co.nlj.ix.repository.CnsLineItemByDateRepository;
import jp.co.nlj.ix.repository.CnsLineItemRepository;
import jp.co.nlj.ix.repository.ShipCutOffInfoRepository;
import jp.co.nlj.ix.repository.ShipFreeTimeInfoRepository;
import jp.co.nlj.ix.repository.ShipFromPrtyRepository;
import jp.co.nlj.ix.repository.ShipFromPrtyRqrmRepository;
import jp.co.nlj.ix.repository.ShipToPrtyRepository;
import jp.co.nlj.ix.repository.ShipToPrtyRqrmRepository;
import jp.co.nlj.ix.repository.TransOrderRepository;
import jp.co.nlj.ix.repository.TrspPlanLineItemRepository;
import jp.co.nlj.ix.repository.VehicleAvailabilityResourceCustomRepository;
import jp.co.nlj.ix.repository.VehicleAvailabilityResourceItemRepository;
import jp.co.nlj.ix.service.CarrierOperationPlansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画明細サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CarrierOperationPlansServiceImpl implements CarrierOperationPlansService {

    private final TrspPlanLineItemRepository trspPlanLineItemRepository;

    private final CnsLineItemRepository cnsLineItemRepository;

    private final ShipFromPrtyRepository shipFromPrtyRepository;

    private final ShipFromPrtyRqrmRepository shipFromPrtyRqrmRepository;

    private final ShipToPrtyRepository shipToPrtyRepository;
    private final ShipToPrtyRqrmRepository shipToPrtyRqrmRepository;
    private final TrspPlanLineItemMapper trspPlanLineItemMapper;
    private final CnsLineItemMapper cnsLineItemMapper;
    private final ShipFromPrtyMapper shipFromPrtyMapper;
    private final ShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ShipToPrtyMapper shipToPrtyMapper;
    private final ShipToPrtyRqrmMapper shipToPrtyRqrmMapper;
    private final ShipCutOffInfoMapper shipCutOffInfoMapper;
    private final ShipCutOffInfoRepository shipCutOffInfoRepository;
    private final ShipFreeTimeInfoRepository shipFreeTimeInfoRepository;
    private final ShipFreeTimeInfoMapper shipFreeTimeInfoMapper;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvailabilityResourceItemRepository vehicleAvailabilityResourceItemRepository;
    private final VehicleAvailabilityResourceCustomRepository vehicleAvailabilityResourceCustomRepository;
    private final TransOrderRepository transOrderRepository;
    private final CarrierOperatorRepository carrierOperatorRepository;


    @Resource(name = "userContext")
    private final UserContext userContext;
    @Autowired
    private CnsLineItemByDateMapper cnsLineItemByDateMapper;
    @Autowired
    private VehicleAvbResourceItemMapper vehicleAvbResourceItemMapper;


    /**
     * 指定されたオペレーターIDからオペレーターコードを取得する。
     *
     * @param operatorId オペレーターID
     * @param list       オペレーターリスト
     * @return オペレーターコード
     */
    private String getOperatorCodeById(String operatorId, List<CarrierOperator> list) {
        Optional<CarrierOperator> companyOptional = list.stream()
            .filter(company -> company.getId().equals(operatorId))
            .findFirst();
        return companyOptional.map(CarrierOperator::getOperatorCode).orElse(null);
    }

    /**
     * 指定されたオペレーターコードからオペレーターIDを取得する。
     *
     * @param operatorCode オペレーターコード
     * @param list         オペレーターリスト
     * @return オペレーターID
     */
    private String getOperatorIdByCode(String operatorCode, List<CarrierOperator> list) {
        Optional<CarrierOperator> companyOptional = list.stream()
            .filter(company -> company.getOperatorCode().equals(operatorCode))
            .findFirst();
        return companyOptional.map(CarrierOperator::getId).orElse(null);
    }

    /**
     * 輸送計画を検索
     *
     * @param request 輸送計画検索リクエスト
     * @return 輸送計画検索レスポンス
     * @author Next Logistic
     */
    @Override
    @Transactional
    public TrspPlanLineItemSearchResponse searchTransportPlan(TrspPlanLineItemSearchRequest request) {
        List<CarrierOperator> listCarrierOperator = carrierOperatorRepository.findAll();
        if (request.getAdvancedConditions() != null && !BaseUtil.isNull(request.getAdvancedConditions().getCid())) {
            String operatorId = getOperatorIdByCode(request.getAdvancedConditions().getCid(), listCarrierOperator);
            if (operatorId != null) {
                request.setOperatorId(operatorId);
            } else {
                request.setOperatorId(request.getAdvancedConditions().getCid());
            }
        }
        List<TrspPlanLineItem> trspPlanLineItemList = trspPlanLineItemRepository.searchTransportPlanItem(request);
        TrspPlanLineItemSearchResponse response = new TrspPlanLineItemSearchResponse();
        if (null == trspPlanLineItemList) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (trspPlanLineItemList.isEmpty()) {
            return response;
        }

        List<Long> trspPlanLineItemIds = trspPlanLineItemList.stream().map(TrspPlanLineItem::getId).toList();
        List<CnsLineItem> cnsLineItemList = cnsLineItemRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipFromPrty> shipFromPrties = shipFromPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipToPrty> shipToPrties = shipToPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipFromPrtyRqrm> shipFromPrtyRqrmList = new ArrayList<>();
        List<ShipToPrtyRqrm> shipToPrtyRqrmList = new ArrayList<>();
        List<ShipCutOffInfo> shipCutOffInfoList = new ArrayList<>();
        List<ShipFreeTimeInfo> shipFreeTimeInfoList = new ArrayList<>();

        if (null != shipFromPrties) {
            List<Long> shipFromPrtyIds = shipFromPrties.stream().map(ShipFromPrty::getId).toList();
            shipFromPrtyRqrmList = shipFromPrtyRqrmRepository.findAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipCutOffInfoList = shipCutOffInfoRepository.findAllByShipFromPrtyIdIn(shipFromPrtyIds);
        }
        if (null != shipToPrties) {
            List<Long> shipToPrtyIds = shipToPrties.stream().map(ShipToPrty::getId).toList();
            shipToPrtyRqrmList = shipToPrtyRqrmRepository.findAllByShipToPrtyIdIn(shipToPrtyIds);
            shipFreeTimeInfoList = shipFreeTimeInfoRepository.findAllByShipToPrtyIdIn(shipToPrtyIds);
        }
        Map<Long, List<CnsLineItem>> cnsLineItemMap = new HashMap<>();
        Map<Long, List<ShipFromPrty>> shipFromPrtyMap = new HashMap<>();
        Map<Long, List<ShipToPrty>> shipToPrtyMap = new HashMap<>();
        Map<Long, ShipFromPrtyRqrm> shipFromPrtyRqrmMap = new HashMap<>();
        Map<Long, ShipToPrtyRqrm> shipToPrtyRqrmMap = new HashMap<>();
        Map<Long, List<ShipCutOffInfo>> shipCutOffInfoMap = new HashMap<>();
        Map<Long, List<ShipFreeTimeInfo>> shipFreeTimeInfoMap = new HashMap<>();

        if (null != cnsLineItemList) {
            cnsLineItemMap = cnsLineItemList.stream()
                .collect(Collectors.groupingBy(CnsLineItem::getTrspPlanLineItemId));
        }
        if (null != shipFromPrties) {
            shipFromPrtyMap = shipFromPrties.stream()
                .collect(Collectors.groupingBy(ShipFromPrty::getTrspPlanLineItemId));
        }
        if (null != shipToPrties) {
            shipToPrtyMap = shipToPrties.stream().collect(Collectors.groupingBy(ShipToPrty::getTrspPlanLineItemId));
        }
        if (null != shipFromPrtyRqrmList) {
            shipFromPrtyRqrmMap = shipFromPrtyRqrmList.stream()
                .collect(Collectors.toMap(ShipFromPrtyRqrm::getShipFromPrtyId, shipFromPrtyRqrm -> shipFromPrtyRqrm));
        }
        if (null != shipToPrtyRqrmList) {
            shipToPrtyRqrmMap = shipToPrtyRqrmList.stream()
                .collect(Collectors.toMap(ShipToPrtyRqrm::getShipToPrtyId, shipToPrtyRqrm -> shipToPrtyRqrm));
        }
        if (null != shipCutOffInfoList) {
            shipCutOffInfoMap = shipCutOffInfoList.stream()
                .collect(Collectors.groupingBy(ShipCutOffInfo::getShipFromPrtyId));
        }
        if (null != shipFreeTimeInfoList) {
            shipFreeTimeInfoMap = shipFreeTimeInfoList.stream()
                .collect(Collectors.groupingBy(ShipFreeTimeInfo::getShipToPrtyId));
        }
        List<TrspPlanLineItemDTO> trspPlanLineItemDTOList = new ArrayList<>();
        for (TrspPlanLineItem trspPlanLineItem : trspPlanLineItemList) {
            TrspPlanLineItemDTO trspPlanLineItemDTO = new TrspPlanLineItemDTO();
            trspPlanLineItemDTO.setTrspIsr(trspPlanLineItemMapper.toTrspIsrDTO(trspPlanLineItem));
            TrspSrvcDTO trspSrvcDTO = trspPlanLineItemMapper.toTrspSrvcDTO(trspPlanLineItem);
            if (!BaseUtil.isNull(trspPlanLineItem.getServiceNo()) && !BaseUtil.isNull(
                String.valueOf(trspPlanLineItem.getServiceStrtDate()))) {
                List<VehicleAvailabilityResource> vehicleAvailabilityResource = vehicleAvailabilityResourceCustomRepository.searchByTrspPlan(
                    trspPlanLineItem.getOperatorId(), trspPlanLineItem.getServiceNo(),
                    trspPlanLineItem.getServiceStrtDate());
                if (vehicleAvailabilityResource != null && !vehicleAvailabilityResource.isEmpty()) {
                    trspSrvcDTO.setOperationId(String.valueOf(vehicleAvailabilityResource.get(0).getId()));
                }
            }
            trspPlanLineItemDTO.setTrspSrvc(trspSrvcDTO);
            trspPlanLineItemDTO.setTrspVehicleTrms(
                trspPlanLineItemMapper.toTrspVehicleTrmsDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setDelInfo(trspPlanLineItemMapper.toDelInfoDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setCns(trspPlanLineItemMapper.toCnsDTO(trspPlanLineItem));
            if (null != cnsLineItemMap.get(trspPlanLineItem.getId())) {
                List<CnsLineItemDTO> cnsLineItemDTOList = cnsLineItemMap.get(trspPlanLineItem.getId()).stream()
                    .map(cnsLineItemMapper::toDTO).toList();
                trspPlanLineItemDTO.setCnsLineItem(cnsLineItemDTOList);
            }
            trspPlanLineItemDTO.setCnsgPrty(trspPlanLineItemMapper.toCnsgPrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setTrspRqrPrty(trspPlanLineItemMapper.toTrspRqrPrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setCneePrty(trspPlanLineItemMapper.toCneePrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setLogsSrvcPrv(trspPlanLineItemMapper.toLogsSrvcPrvDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setRoadCarr(trspPlanLineItemMapper.toRoadCarrDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setFretClimToPrty(trspPlanLineItemMapper.toFretClimToPrtyDTO(trspPlanLineItem));
            List<ShipFromPrtyDTO> shipFromPrtyDTOList = new ArrayList<>();
            List<ShipToPrtyDTO> shipToPrtyDTOList = new ArrayList<>();
            if (null != shipFromPrtyMap.get(trspPlanLineItem.getId())) {
                for (ShipFromPrty shipFromPrty : shipFromPrtyMap.get(trspPlanLineItem.getId())) {
                    ShipFromPrtyDTO shipFromPrtyDTO;
                    shipFromPrtyDTO = shipFromPrtyMapper.toDTO(shipFromPrty);
                    if (null != shipFromPrtyRqrmMap.get(shipFromPrty.getId())) {
                        ShipFromPrtyRqrmDTO shipFromPrtyRqrmDTO = shipFromPrtyRqrmMapper.toDTO(
                            shipFromPrtyRqrmMap.get(shipFromPrty.getId()));
                        if (null != shipFromPrtyDTO && (null != shipFromPrtyRqrmDTO)) {
                            shipFromPrtyDTO.setShipFromPrtyRqrm(shipFromPrtyRqrmDTO);
                        }
                    }
                    if (null != shipCutOffInfoMap.get(shipFromPrty.getId())) {
                        List<ShipCutOffInfoDTO> listDto = shipCutOffInfoMap.get(shipFromPrty.getId()).stream()
                            .map(shipCutOffInfoMapper::toDTO).toList();
                        if (null != shipFromPrtyDTO) {
                            shipFromPrtyDTO.setShipCutOffInfo(listDto);
                        }
                    }
                    shipFromPrtyDTOList.add(shipFromPrtyDTO);
                }
            }
            if (null != shipToPrtyMap.get(trspPlanLineItem.getId())) {
                for (ShipToPrty shipToPrty : shipToPrtyMap.get(trspPlanLineItem.getId())) {
                    ShipToPrtyDTO shipToPrtyDTO = shipToPrtyMapper.toDTO(shipToPrty);
                    if (null != shipToPrtyRqrmMap.get(shipToPrty.getId())) {
                        ShipToPrtyRqrmDTO shiptoPrtyRqrmDTO = shipToPrtyRqrmMapper.toDTO(
                            shipToPrtyRqrmMap.get(shipToPrty.getId()));
                        if (null != shipToPrtyDTO && (null != shiptoPrtyRqrmDTO)) {
                            shipToPrtyDTO.setShipToPrtyRqrm(shiptoPrtyRqrmDTO);

                        }
                    }
                    if (null != shipFreeTimeInfoMap.get(shipToPrty.getId())) {
                        List<ShipFreeTimeInfoDTO> listDto = shipFreeTimeInfoMap.get(shipToPrty.getId()).stream()
                            .map(shipFreeTimeInfoMapper::toDTO).toList();
                        if (null != shipToPrtyDTO) {
                            shipToPrtyDTO.setShipFreeTimeInfo(listDto);
                        }
                    }
                    shipToPrtyDTOList.add(shipToPrtyDTO);
                }
            }
            trspPlanLineItemDTO.setShipFromPrty(shipFromPrtyDTOList);
            trspPlanLineItemDTO.setShipToPrty(shipToPrtyDTOList);
            trspPlanLineItemDTO.setCid(getOperatorCodeById(trspPlanLineItem.getOperatorId(), listCarrierOperator));
            trspPlanLineItemDTOList.add(trspPlanLineItemDTO);
        }
        response.setTrspPlanLineItem(trspPlanLineItemDTOList);
        return response;
    }

    /**
     * キャリアオペレーターのプランを挿入する。
     *
     * @param request キャリアオペレーターのプランリクエスト
     * @return キャリアオペレーターのプラン挿入レスポンス
     */
    @Override
    @Transactional
    public CarrierOperatorPlansInsertResponse insertItem(CarrierOperatorPlansRequest request) {
        TransOrder transOrder = new TransOrder();
        CarrierOperatorPlansInsertResponse response = new CarrierOperatorPlansInsertResponse();
        //Make data insert table Transport order
        transOrder.setTransType(Integer.valueOf(TransOrderType.CARRIER_REQUEST));
        transOrder.setTransportDate(stringToLocalDate(request.getTrspOpDateTrmStrtDate()));

        CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.valueOf(request.getTrspPlanId())).orElse(null);
        if (cnsLineItemByDate != null) {
            transOrder.setCarrierOperatorId(cnsLineItemByDate.getOperatorId());
            transOrder.setCarrierOperatorCode(cnsLineItemByDate.getOperatorCode());
            transOrder.setDepartureFrom(cnsLineItemByDate.getDepartureFrom());
            transOrder.setArrivalTo(cnsLineItemByDate.getArrivalTo());
            transOrder.setTransportPlanItemId(cnsLineItemByDate.getTrspPlanItemId());
            transOrder.setCnsLineItemByDateId(cnsLineItemByDate.getId());
            transOrder.setCnsLineItemId(cnsLineItemByDate.getCnsLineItemId());
            transOrder.setTrspInstructionId(cnsLineItemByDate.getTransPlanId());
            transOrder.setItemNameTxt(cnsLineItemByDate.getTransportName());
            transOrder.setCarrierOperatorName(cnsLineItemByDate.getOperatorName());
            transOrder.setParentOrderId(cnsLineItemByDate.getTransOrderId());
            if (cnsLineItemByDate.getTransOrderId() != null) {
                Optional<TransOrder> transOrderParent = transOrderRepository.findById(
                    cnsLineItemByDate.getTransOrderId());
                if (transOrderParent.isPresent()) {
                    TransOrder transOrderParentItem = transOrderParent.get();
                    transOrder.setShipperOperatorId(transOrderParentItem.getShipperOperatorId());
                    transOrder.setShipperOperatorCode(transOrderParentItem.getShipperOperatorCode());
                    transOrder.setShipperOperatorName(transOrderParentItem.getShipperOperatorName());
                }
            }
            try {
                transOrder.setRequestSnapshot(cnsLineItemByDateMapper.mapToSnapshot(cnsLineItemByDate));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, TransOrderError.CNS_LINE_ITEM_BY_DATE_NOT_NULL);
        }
        VehicleAvbResourceItem vehicleAvbResourceItem = vehicleAvailabilityResourceItemRepository.findById(
            Long.valueOf(request.getOperationId())).orElse(null);
        if (vehicleAvbResourceItem != null) {
            transOrder.setCarrier2OperatorId(vehicleAvbResourceItem.getOperatorId());
            transOrder.setCarrier2OperatorCode(vehicleAvbResourceItem.getOperatorCode());
            transOrder.setVehicleAvbResourceItemId(vehicleAvbResourceItem.getId());
            transOrder.setServiceName(vehicleAvbResourceItem.getTripName());
            transOrder.setCarrier2OperatorName(vehicleAvbResourceItem.getOperatorName());
            transOrder.setVehicleAvbResourceId(vehicleAvbResourceItem.getVehicleAvbResourceId());
            Long vehicleDiagramItemTrailerId;
            if (BaseUtil.isNull(request.getTrspOpTrailerId())) {
                vehicleDiagramItemTrailerId = vehicleAvbResourceItem.getVehicleDiagramItemTrailerId();
            } else {
                try {
                    vehicleDiagramItemTrailerId = Long.valueOf(request.getTrspOpTrailerId());
                } catch (Exception e) {
                    vehicleDiagramItemTrailerId = vehicleAvbResourceItem.getVehicleDiagramItemTrailerId();
                }
            }
            transOrder.setVehicleDiagramItemTrailerId(vehicleDiagramItemTrailerId);
            transOrder.setDepartureTime(vehicleAvbResourceItem.getDepartureTime());
            transOrder.setArrivalTime(vehicleAvbResourceItem.getArrivalTime());
            transOrder.setVehicleDiagramItemId(vehicleAvbResourceItem.getVehicleDiagramId());
            try {
                transOrder.setProposeSnapshot(vehicleAvbResourceItemMapper.mapToSnapshot(vehicleAvbResourceItem));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                TransOrderError.VEHICLE_AVAILABILITY_RESOURCE_ITEM_NOT_NULL);
        }

        transOrder.setProposePrice(request.getReqFreightRate());
        transOrder.setProposeDepartureTime(stringToLocalTime(request.getTrspOpPlanDateTrmStrtTime()));
        transOrder.setProposeArrivalTime(stringToLocalTime(request.getTrspOpPlanDateTrmEndTime()));
        transOrder.setStatus(Integer.valueOf(TransOrderStatus.CARRIER2_PROPOSAL));
        //Update cns and vehicle resource
        cnsLineItemByDate.setStatus(Status.AWAITING_CONFIRMATION);
        cnsLineItemByDateRepository.save(cnsLineItemByDate);
        vehicleAvbResourceItem.setStatus(Status.AWAITING_CONFIRMATION);
        vehicleAvailabilityResourceItemRepository.save(vehicleAvbResourceItem);

        transOrder = transOrderRepository.save(transOrder);
        response.setProposeId(transOrder.getId().toString());

        return response;
    }

    /**
     * キャリアオペレーターのプランを更新する。
     *
     * @param request     キャリアオペレーターのプランリクエスト
     * @param proposeId   提案ID
     * @param operationId 操作ID
     * @return キャリアオペレーターのプラン更新レスポンス
     */
    @Override
    @Transactional
    public CarrierOperatorPlansUpdateResponse updateItem(CarrierOperatorPlansRequest request, String proposeId,
        String operationId) {
        if (BaseUtil.isNull(proposeId)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TransOrderError.PROPOSE_NOT_NULL)));
        }
        if (BaseUtil.isNull(operationId)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                ShipperOperationPlans.OPERATION_ID_NOT_NULL_OR_EMPTY);
        }
        /*User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TransOrderError.USER_COMPANY_NOT_NULL)));
        }*/
        long proposeIdLong;
        try {
            proposeIdLong = Long.parseLong(proposeId);
        } catch (NumberFormatException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperOperationPlans.PROPOSE_ID_NOT_VALID);
            return null;
        }
        Optional<TransOrder> optional = transOrderRepository.findById(proposeIdLong);
        TransOrder transOrder;
        if (optional.isEmpty()) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TransOrderError.TRANS_ORDER_NOT_NULL)));
        } else {
            transOrder = optional.get();
        }
        CarrierOperatorPlansUpdateResponse response = new CarrierOperatorPlansUpdateResponse();
        response.setTrspPlanId(request.getTrspPlanId());
        response.setOperationId(operationId);
        response.setServiceNo(request.getServiceNo());
        response.setTrspOpDateTrmStrtDate(request.getTrspOpDateTrmStrtDate());
        response.setTrspOpDateTrmEndDate(request.getTrspOpDateTrmEndDate());
        response.setTrspOpPlanDateTrmStrtTime(request.getTrspOpPlanDateTrmStrtTime());
        response.setTrspOpPlanDateTrmEndTime(request.getTrspOpPlanDateTrmEndTime());
        response.setTrspOpTrailerId(request.getTrspOpTrailerId());
        response.setGiaiNumber(request.getGiaiNumber());
        response.setReqFreightRate(request.getReqFreightRate());

        //Make data insert table transOrder
        if (!BaseUtil.isNull(request.getTrspOpDateTrmStrtDate())) {
            transOrder.setTransportDate(stringToLocalDate(request.getTrspOpDateTrmStrtDate()));
        }
        transOrder.setStatus(211);
        if (request.getReqFreightRate() != null) {
            transOrder.setProposePrice(request.getReqFreightRate());
        }
        if (!BaseUtil.isNull(request.getTrspOpPlanDateTrmStrtTime())) {
            transOrder.setProposeDepartureTime(stringToLocalTime(request.getTrspOpPlanDateTrmStrtTime()));
        }
        if (!BaseUtil.isNull(request.getTrspOpPlanDateTrmEndTime())) {
            transOrder.setProposeArrivalTime(stringToLocalTime(request.getTrspOpPlanDateTrmEndTime()));
        }

        TransOrder transOrderUpdate = transOrderRepository.save(transOrder);
        response.setProposeId(transOrderUpdate.getId().toString());
        return response;
    }

    /**
     * 荷主向け運行申し込みの諾否回答通知を作成する。
     *
     * @param carrierOperationApprovalRequestDTO 運行計画承認リクエストDTO
     * @param operationIdParam                   操作ID
     * @param proposeId                          提案ID
     * @return 運行計画承認レスポンスDTO
     */
    @Override
    @Transactional
    public CarrierOperationApprovalResponseDTO carrierOperationApproval(
        CarrierOperationApprovalRequestDTO carrierOperationApprovalRequestDTO,
        String operationIdParam, String proposeId) {
        CarrierOperationApprovalResponseDTO carrierOperationApprovalResponseDTO = new CarrierOperationApprovalResponseDTO();

        if (BaseUtil.isNull(operationIdParam)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                ShipperOperationPlans.OPERATION_ID_NOT_NULL_OR_EMPTY);
        }
        if (BaseUtil.isNull(proposeId)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                ShipperOperationPlans.PROPOSE_ID_NOT_NULL_OR_EMPTY);
        }

        Long proposeIdLong;
        try {
            proposeIdLong = Long.parseLong(proposeId);
        } catch (NumberFormatException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperOperationPlans.PROPOSE_ID_NOT_VALID);
            return null;
        }
        TransOrder transOrder = transOrderRepository.findById(proposeIdLong).orElse(null);
        if (Objects.isNull(transOrder)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperOperationPlans.TRANS_ORDER_NOT_FOUND);
        }
        if (carrierOperationApprovalRequestDTO.getApproval()) {
            transOrder.setStatus(230);
        } else {
            transOrder.setStatus(232);
        }
        TransOrder transOrderInsert = transOrderRepository.save(transOrder);

        carrierOperationApprovalResponseDTO.setTrspPlanId(carrierOperationApprovalRequestDTO.getTrspPlanId());
        carrierOperationApprovalResponseDTO.setOperationId(operationIdParam);
        carrierOperationApprovalResponseDTO.setApproval(carrierOperationApprovalRequestDTO.getApproval());
        carrierOperationApprovalResponseDTO.setProposeId(transOrderInsert.getId().toString());
        return carrierOperationApprovalResponseDTO;
    }


    /**
     * VehicleAvbResourceItemSnapshotをデータベースカラムに変換する。
     *
     * @param attribute VehicleAvbResourceItemSnapshot
     * @return JSON形式の文字列
     */
    public String convertToDatabaseColumn(VehicleAvbResourceItemSnapshot attribute) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
