package jp.co.nlj.ix.service.impl;

import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import java.util.Objects;
import jp.co.nlj.ix.constant.MapperConstants.TransOrderError;
import jp.co.nlj.ix.constant.MessageConstant.ShipperOperationPlans;
import jp.co.nlj.ix.constant.ParamConstant.Status;
import jp.co.nlj.ix.constant.ParamConstant.TransOrderStatus;
import jp.co.nlj.ix.constant.ParamConstant.TransOrderType;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.domain.shipper.TransOrder;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationReserveRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationUpdateRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationReserveResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationUpdateResponseDTO;
import jp.co.nlj.ix.mapper.CnsLineItemByDateMapper;
import jp.co.nlj.ix.mapper.DateTimeMapper;
import jp.co.nlj.ix.mapper.VehicleAvbResourceItemMapper;
import jp.co.nlj.ix.repository.CnsLineItemByDateRepository;
import jp.co.nlj.ix.repository.TransOrderRepository;
import jp.co.nlj.ix.repository.VehicleAvailabilityResourceItemRepository;
import jp.co.nlj.ix.service.ShipperOperationPlansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 荷主向け運行申し込み諾否回答通知サービス実装クラス<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShipperOperationPlansServiceImpl implements ShipperOperationPlansService {

    private final TransOrderRepository transOrderRepository;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvailabilityResourceItemRepository vehicleAvailabilityResourceItemRepository;
    private final CnsLineItemByDateMapper cnsLineItemByDateMapper;
    private final VehicleAvbResourceItemMapper vehicleAvbResourceItemMapper;

    /**
     * 荷主向け運行申し込み諾否回答通知を作成する。
     *
     * @param shipperOperationApprovalRequestDTO 荷主向け運行申し込み諾否回答通知リクエストDTO
     * @return 荷主向け運行申し込み諾否回答通知レスポンスDTO
     */
    @Override
    @Transactional
    public ShipperOperationApprovalResponseDTO shipperOperationApproval(
        ShipperOperationApprovalRequestDTO shipperOperationApprovalRequestDTO,
        String operationIdParam, String proposeId) {
        ShipperOperationApprovalResponseDTO shipperOperationApprovalResponseDTO = new ShipperOperationApprovalResponseDTO();

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
        if (shipperOperationApprovalRequestDTO.getApproval()) {
            transOrder.setStatus(130);
        } else {
            transOrder.setStatus(132);
        }
        transOrderRepository.save(transOrder);

        shipperOperationApprovalResponseDTO.setOperationId(operationIdParam);
        shipperOperationApprovalResponseDTO.setApproval(shipperOperationApprovalRequestDTO.getApproval());
        return shipperOperationApprovalResponseDTO;
    }

    /**
     * 荷主向け運行申し込みを更新する。
     *
     * @param shipperOperationUpdateRequestDTO 荷主向け運行申し込み更新リクエストDTO
     * @return 荷主向け運行申し込みレスポンスDTO
     */
    @Override
    @Transactional
    public ShipperOperationUpdateResponseDTO shipperOperationUpdate(
        ShipperOperationUpdateRequestDTO shipperOperationUpdateRequestDTO,
        String operationIdParam, String proposeId) {
        ShipperOperationUpdateResponseDTO shipperOperationUpdateResponseDTO = new ShipperOperationUpdateResponseDTO();

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
        // 運行申し込みを取得
        TransOrder transOrder = transOrderRepository.findById(proposeIdLong).orElse(null);
        if (Objects.isNull(transOrder)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperOperationPlans.TRANS_ORDER_NOT_FOUND);
        }
        if (shipperOperationUpdateRequestDTO.getReqFreightRate() != null) {
            transOrder.setRequestPrice(shipperOperationUpdateRequestDTO.getReqFreightRate());
        }
        if (!BaseUtil.isNull(shipperOperationUpdateRequestDTO.getReqAvbFromTimeOfCllTime())) {
            transOrder.setRequestCollectionTimeFrom(
                DateTimeMapper.stringToLocalTime(shipperOperationUpdateRequestDTO.getReqAvbFromTimeOfCllTime()));
        }
        if (!BaseUtil.isNull(shipperOperationUpdateRequestDTO.getReqAvbToTimeOfCllTime())) {
            transOrder.setRequestCollectionTimeTo(
                DateTimeMapper.stringToLocalTime(shipperOperationUpdateRequestDTO.getReqAvbToTimeOfCllTime()));
        }
        if (!BaseUtil.isNull(shipperOperationUpdateRequestDTO.getTrspOpDateTrmStrtDate())) {
            transOrder.setTransportDate(
                DateTimeMapper.stringToLocalDate(shipperOperationUpdateRequestDTO.getTrspOpDateTrmStrtDate()));
        }
        transOrder.setStatus(111);
        transOrderRepository.save(transOrder);
        // レスポンスDTOに値を設定する
        shipperOperationUpdateResponseDTO.setTrspPlanId(shipperOperationUpdateRequestDTO.getTrspPlanId());
        shipperOperationUpdateResponseDTO.setServiceNo(shipperOperationUpdateRequestDTO.getServiceNo());
        shipperOperationUpdateResponseDTO.setOperationId(operationIdParam);
        shipperOperationUpdateResponseDTO.setGiaiNumber(shipperOperationUpdateRequestDTO.getGiaiNumber());
        shipperOperationUpdateResponseDTO.setTrspOpTrailerId(shipperOperationUpdateRequestDTO.getTrspOpTrailerId());
        shipperOperationUpdateResponseDTO.setTrspOpDateTrmStrtDate(
            shipperOperationUpdateRequestDTO.getTrspOpDateTrmStrtDate());
        shipperOperationUpdateResponseDTO.setTrspOpPlanDateTrmStrtTime(
            shipperOperationUpdateRequestDTO.getTrspOpPlanDateTrmStrtTime());
        shipperOperationUpdateResponseDTO.setReqAvbFromTimeOfCllTime(
            shipperOperationUpdateRequestDTO.getReqAvbFromTimeOfCllTime());
        shipperOperationUpdateResponseDTO.setReqAvbToTimeOfCllTime(
            shipperOperationUpdateRequestDTO.getReqAvbToTimeOfCllTime());
        shipperOperationUpdateResponseDTO.setReqFreightRate(shipperOperationUpdateRequestDTO.getReqFreightRate());

        return shipperOperationUpdateResponseDTO;
    }

    /**
     * 荷主向け運行申し込みを作成する。
     *
     * @param shipperOperationReserveRequestDTO 荷主向け運行申し込みリクエストDTO
     * @return 荷主向け運行申し込みレスポンスDTO
     */
    @Override
    @Transactional
    public ShipperOperationReserveResponseDTO shipperOperationReserve(
        ShipperOperationReserveRequestDTO shipperOperationReserveRequestDTO) {
        ShipperOperationReserveResponseDTO shipperOperationReserveResponseDTO = new ShipperOperationReserveResponseDTO();
        TransOrder transOrder = new TransOrder();
        transOrder.setTransType(Integer.valueOf(TransOrderType.SHIPPER_REQUEST));
        CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.valueOf(shipperOperationReserveRequestDTO.getTrspPlanId())).orElse(null);
        if (cnsLineItemByDate != null) {
            transOrder.setShipperOperatorId(cnsLineItemByDate.getOperatorId());
            transOrder.setShipperOperatorCode(cnsLineItemByDate.getOperatorCode());
            transOrder.setDepartureFrom(cnsLineItemByDate.getDepartureFrom());
            transOrder.setArrivalTo(cnsLineItemByDate.getArrivalTo());
            transOrder.setTransportPlanItemId(cnsLineItemByDate.getTrspPlanItemId());
            transOrder.setCnsLineItemByDateId(cnsLineItemByDate.getId());
            transOrder.setCnsLineItemId(cnsLineItemByDate.getCnsLineItemId());
            transOrder.setReqCnsLineItemId(cnsLineItemByDate.getReqCnsLineItemId());
            transOrder.setTrspInstructionId(cnsLineItemByDate.getTransPlanId());
            transOrder.setItemNameTxt(cnsLineItemByDate.getTransportName());
            transOrder.setShipperOperatorName(cnsLineItemByDate.getOperatorName());
            try {
                transOrder.setRequestSnapshot(cnsLineItemByDateMapper.mapToSnapshot(cnsLineItemByDate));
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        } else {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                TransOrderError.TRANS_PLAN_ID_NOT_FOUND_IN_CNS_LINE_ITEM_BY_DATE);
        }

        VehicleAvbResourceItem vehicleAvbResourceItem = vehicleAvailabilityResourceItemRepository.findById(
            Long.valueOf(shipperOperationReserveRequestDTO.getOperationId())).orElse(null);
        if (vehicleAvbResourceItem != null) {
            transOrder.setCarrierOperatorId(vehicleAvbResourceItem.getOperatorId());
            transOrder.setCarrierOperatorCode(vehicleAvbResourceItem.getOperatorCode());
            transOrder.setVehicleAvbResourceItemId(vehicleAvbResourceItem.getId());
            transOrder.setServiceName(vehicleAvbResourceItem.getTripName());
            transOrder.setCarrierOperatorName(vehicleAvbResourceItem.getOperatorName());
            transOrder.setVehicleAvbResourceId(vehicleAvbResourceItem.getVehicleAvbResourceId());
            Long vehicleDiagramItemTrailerId;
            if (BaseUtil.isNull(shipperOperationReserveRequestDTO.getTrspOpTrailerId())) {
                vehicleDiagramItemTrailerId = vehicleAvbResourceItem.getVehicleDiagramItemTrailerId();
            } else {
                try {
                    vehicleDiagramItemTrailerId = Long.valueOf(shipperOperationReserveRequestDTO.getTrspOpTrailerId());
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
                TransOrderError.OPERATION_ID_NOT_FOUND_IN_VEHICLE_AVAILABILITY_RESOURCE_ITEM);
        }
        transOrder.setRequestPrice(shipperOperationReserveRequestDTO.getReqFreightRate());
        transOrder.setRequestCollectionTimeFrom(
            DateTimeMapper.stringToLocalTime(shipperOperationReserveRequestDTO.getReqAvbFromTimeOfCllTime()));
        transOrder.setRequestCollectionTimeTo(
            DateTimeMapper.stringToLocalTime(shipperOperationReserveRequestDTO.getReqAvbToTimeOfCllTime()));
        transOrder.setTransportDate(
            DateTimeMapper.stringToLocalDate(shipperOperationReserveRequestDTO.getTrspOpDateTrmStrtDate()));
        transOrder.setStatus(Integer.valueOf(TransOrderStatus.SHIPPER_REQUEST));
        //Update cns and vehicle resource
        cnsLineItemByDate.setStatus(Status.AWAITING_CONFIRMATION);
        vehicleAvbResourceItem.setStatus(Status.AWAITING_CONFIRMATION);
        cnsLineItemByDateRepository.save(cnsLineItemByDate);
        vehicleAvailabilityResourceItemRepository.save(vehicleAvbResourceItem);
        //Insert transport order
        transOrder = transOrderRepository.save(transOrder);

        shipperOperationReserveResponseDTO.setProposeId(transOrder.getId().toString());
        return shipperOperationReserveResponseDTO;
    }
} 
