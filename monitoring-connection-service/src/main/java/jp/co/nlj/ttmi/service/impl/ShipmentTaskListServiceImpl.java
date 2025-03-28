package jp.co.nlj.ttmi.service.impl;

import com.next.logistic.util.BaseUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jp.co.nlj.ttmi.constant.ParamConstant;
import jp.co.nlj.ttmi.constant.ParamConstant.ZLWeb;
import jp.co.nlj.ttmi.domain.ILoadingInfo;
import jp.co.nlj.ttmi.domain.IOrderDetail;
import jp.co.nlj.ttmi.domain.IOrderInfo;
import jp.co.nlj.ttmi.domain.IShipmentCargoStatus;
import jp.co.nlj.ttmi.domain.IShipmentStatus;
import jp.co.nlj.ttmi.domain.IShipmentTaskList;
import jp.co.nlj.ttmi.domain.IVehicleSchedule;
import jp.co.nlj.ttmi.domain.IVehicleSchedulePlan;
import jp.co.nlj.ttmi.domain.MOperatingService;
import jp.co.nlj.ttmi.domain.MOperatingServicePlan;
import jp.co.nlj.ttmi.dto.Request.OrderTimeUpdateRequest;
import jp.co.nlj.ttmi.dto.zlWeb.ZLWebDTO;
import jp.co.nlj.ttmi.repository.ILoadingInfoRepository;
import jp.co.nlj.ttmi.repository.IOrderDetailRepository;
import jp.co.nlj.ttmi.repository.IOrderInfoRepository;
import jp.co.nlj.ttmi.repository.IShipmentCargoStatusRepository;
import jp.co.nlj.ttmi.repository.IShipmentStatusRepository;
import jp.co.nlj.ttmi.repository.IShipmentTaskListRepository;
import jp.co.nlj.ttmi.repository.IVehicleSchedulePlanRepository;
import jp.co.nlj.ttmi.repository.IVehicleScheduleRepository;
import jp.co.nlj.ttmi.repository.MOperatingServicePlanRepository;
import jp.co.nlj.ttmi.repository.MOperatingServiceRepository;
import jp.co.nlj.ttmi.service.ShipmentTaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 運送タスクリストサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class ShipmentTaskListServiceImpl implements ShipmentTaskListService {

    private final IShipmentTaskListRepository shipmentTaskListRepository;
    private final IOrderInfoRepository orderInfoRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IVehicleSchedulePlanRepository vehicleSchedulePlanRepository;
    private final MOperatingServiceRepository operatingServiceRepository;
    private final MOperatingServicePlanRepository operatingServicePlanRepository;
    private final IShipmentStatusRepository shipmentStatusRepository;
    private final ILoadingInfoRepository loadingInfoRepository;
    private final IVehicleScheduleRepository vehicleScheduleRepository;
    private final IShipmentCargoStatusRepository shipmentCargoStatusRepository;

    /**
     * スケジュール保存。<BR>
     *
     * @param zlWebDTO ZLWebDTO
     */
    @Override
    public void saveSchedule(ZLWebDTO zlWebDTO) {
        IShipmentTaskList existedShipmentTaskList = shipmentTaskListRepository.findByManagementNumberAndDetailNo(
            zlWebDTO.getManagementNumber(), ZLWeb.OPERATION_SERIVCE_ORDER_2);
        if (existedShipmentTaskList == null) {
            LocalDateTime now = LocalDateTime.now();

            String locationName1 = zlWebDTO.getDepartureFromName();
            String locationKey1 = ZLWeb.LOCATION_KEY_1;
            String locationName2 = zlWebDTO.getArrivalToName();
            String locationKey2 = ZLWeb.LOCATION_KEY_2;
            if (!BaseUtil.isNull(locationName1)) {
                if (locationName1.equals(ZLWeb.DEFAULT_SHIP_1)) {
                    locationKey1 = ZLWeb.LOCATION_KEY_1;
                } else if (locationName1.equals(ZLWeb.DEFAULT_SHIP_2)) {
                    locationKey1 = ZLWeb.LOCATION_KEY_2;
                } else {
                    locationName1 = ZLWeb.DEFAULT_SHIP_1;
                }
            }

            if (!BaseUtil.isNull(locationName2)) {
                if (locationName2.equals(ZLWeb.DEFAULT_SHIP_1)) {
                    locationKey2 = ZLWeb.LOCATION_KEY_1;
                } else if (locationName2.equals(ZLWeb.DEFAULT_SHIP_2)) {
                    locationKey2 = ZLWeb.LOCATION_KEY_2;
                } else {
                    locationName2 = ZLWeb.DEFAULT_SHIP_2;
                }
            }

            String lastTrunkLineAllocationKey = loadingInfoRepository.getLastTrunkLineAllocationKey();
            String nextTrunkLineAllocationKey = generateNextTrunkLineAllocationKey(lastTrunkLineAllocationKey);
            String lastVehicleScheduleKey = vehicleSchedulePlanRepository.getLastVehicleScheduleKey();
            String newVehicleScheduleKey = generateNextVehicleScheduleKey(lastVehicleScheduleKey);

            IOrderInfo iOrderInfo = new IOrderInfo();
            iOrderInfo.setManagementNumber(zlWebDTO.getManagementNumber());
            iOrderInfo.setOrderDate(zlWebDTO.getStartDate());
            iOrderInfo.setOwnerCompanyCode(zlWebDTO.getShipperOperatorCode());
            iOrderInfo.setOwnerUserId(ParamConstant.ZLWeb.OWNER_USER);
            iOrderInfo.setOwnerCompanyNameAbbreviation(zlWebDTO.getShipperOperatorName());
            iOrderInfo.setOwnerUserName(ParamConstant.ZLWeb.OWNER_USER);
            iOrderInfo.setShipmentShipperName(zlWebDTO.getShipperOperatorName());
            iOrderInfo.setLogisticsCompanyCode(ParamConstant.ZLWeb.LOGISTIC_COMPANY_CODE);
            iOrderInfo.setDeleteFlg(ParamConstant.ZLWeb.DELETE_FLG);
            iOrderInfo.setProcessId(ParamConstant.ZLWeb.PROCESS_ID);
            iOrderInfo.setCreateUser(ParamConstant.ZLWeb.DEFAULT_USER);
            iOrderInfo.setCreateDt(LocalDateTime.now());
            iOrderInfo.setUpdUser(ParamConstant.ZLWeb.DEFAULT_USER);
            iOrderInfo.setUpdDt(LocalDateTime.now());
            iOrderInfo.setOwnerCompanyNameAbbreviation(ParamConstant.ZLWeb.DEFAULT_COMPANY_ABBREVIATION);
            iOrderInfo.setShipmentShipperName(BaseUtil.isNull(zlWebDTO.getShipperOperatorName()) ?
                ParamConstant.ZLWeb.DEFAULT_SHIPPER_SHIPMENT_NAME : zlWebDTO.getShipperOperatorName());
            iOrderInfo.setDeliveryTimeFrom(BaseUtil.isNull(zlWebDTO.getShipmentDateFrom()) ?
                now.format(DateTimeFormatter.ofPattern(ParamConstant.TIME_FORMAT)) :
                zlWebDTO.getShipmentDateFrom());
            iOrderInfo.setDeliveryTimeTo(BaseUtil.isNull(zlWebDTO.getShipmentDateTo()) ?
                now.plusHours(1).format(DateTimeFormatter.ofPattern(ParamConstant.TIME_FORMAT)) :
                zlWebDTO.getShipmentDateTo());
            iOrderInfo.setShipFrom(locationName1);
            iOrderInfo.setShipTo(locationName2);
            iOrderInfo.setDropOffDate(zlWebDTO.getDropOffDate());
            iOrderInfo.setDropOffTimeFrom(zlWebDTO.getDropOffTimeFrom());
            iOrderInfo.setDropOffTimeTo(zlWebDTO.getDropOffTimeTo());
            iOrderInfo.setDeliveryVehicleNumber(zlWebDTO.getVehicleNumber());
            iOrderInfo.setDeliveryCompanyName(zlWebDTO.getCarrierOperatorName());
            iOrderInfo.setDeliveryLocationKey(locationKey2);
            iOrderInfo.setDeliveryDate(zlWebDTO.getStartDate());
            orderInfoRepository.save(iOrderInfo);

            saveOrderDetails(zlWebDTO, locationName1, locationName2);

            String lastShipmentKey = shipmentTaskListRepository.lastShipmentTaskKey();
            String newShipmentTaskKey = generateNextShipmentTaskKey(lastShipmentKey);
            IShipmentTaskList shipmentTaskTo = createShipmentTask(zlWebDTO, newShipmentTaskKey, ZLWeb.SHIPMENT_KBN_4,
                locationName2, locationName1);
            shipmentTaskListRepository.save(shipmentTaskTo);

            String lastOpeServiceKey = operatingServiceRepository.getLastOpeServiceKey();
            String newOpeServiceKey = generateNextOpeServiceKey(lastOpeServiceKey);
            MOperatingService mOperatingService = setOperatingService(zlWebDTO, newOpeServiceKey, locationKey2);
            operatingServiceRepository.save(mOperatingService);

            MOperatingServicePlan operatingServicePlanStart = setOperatingServicePlan(newOpeServiceKey,
                ZLWeb.OPERATION_SERIVCE_ORDER_1, locationKey1, ZLWeb.OPERATION_KBN_2,
                String.valueOf(zlWebDTO.getStartTime()), ZLWeb.END_POINT_1);
            MOperatingServicePlan operatingServicePlanEnd = setOperatingServicePlan(newOpeServiceKey,
                ZLWeb.OPERATION_SERIVCE_ORDER_2, locationKey2, ZLWeb.OPERATION_KBN_2,
                String.valueOf(zlWebDTO.getEndTime()), ZLWeb.END_POINT_2);
            operatingServicePlanRepository.save(operatingServicePlanStart);
            operatingServicePlanRepository.save(operatingServicePlanEnd);

            IVehicleSchedule vehicleSchedule = getiVehicleSchedule(zlWebDTO, newVehicleScheduleKey,
                newOpeServiceKey);
            vehicleScheduleRepository.saveAndFlush(vehicleSchedule);

            LocalDateTime operationDate = zlWebDTO.getStartDate().atTime(zlWebDTO.getStartTime());
            LocalDateTime operationDateTo = zlWebDTO.getEndDate().atTime(zlWebDTO.getEndTime());
            IVehicleSchedulePlan vehicleSchedulePlanStart = setVehicleSchedulePlan(newVehicleScheduleKey,
                ZLWeb.OPERATION_SERIVCE_ORDER_1,
                newOpeServiceKey, operationDate,
                zlWebDTO.getManagementNumber());
            IVehicleSchedulePlan vehicleSchedulePlanEnd = setVehicleSchedulePlan(newVehicleScheduleKey,
                ZLWeb.OPERATION_SERIVCE_ORDER_2, newOpeServiceKey,
                operationDateTo,
                zlWebDTO.getManagementNumber());
            vehicleSchedulePlanRepository.save(vehicleSchedulePlanStart);
            vehicleSchedulePlanRepository.save(vehicleSchedulePlanEnd);

            IShipmentStatus iShipmentStatus = new IShipmentStatus();
            iShipmentStatus.setDestinationCenterKey(locationKey2);
            iShipmentStatus.setShipmentKbn(ZLWeb.SHIPMENT_KBN_4);
            iShipmentStatus.setDeliveryServiceKey(zlWebDTO.getManagementNumber());
            iShipmentStatus.setManagementNumber(ZLWeb.MANAGEMENT_NUMBER);
            iShipmentStatus.setShipmentStatusKbn(ZLWeb.STATUS_SHIPMENT_KBN);
            iShipmentStatus.setDeleteFlg(ZLWeb.DELETE_FLG);
            iShipmentStatus.setProcessId(ZLWeb.PROCESS_ID);
            shipmentStatusRepository.save(iShipmentStatus);

            ILoadingInfo iLoadingInfo = new ILoadingInfo();
            iLoadingInfo.setTrunkLineAllocationKey(nextTrunkLineAllocationKey);
            iLoadingInfo.setVehicleScheduleKey(newVehicleScheduleKey);
            iLoadingInfo.setManagementNumber(zlWebDTO.getManagementNumber());
            iLoadingInfo.setDetailNo(ZLWeb.OPERATION_SERIVCE_ORDER_1);
            iLoadingInfo.setUnit(BigDecimal.ONE);
            iLoadingInfo.setDeleteFlg(ZLWeb.DELETE_FLG);
            iLoadingInfo.setProcessId(ZLWeb.PROCESS_ID);
            loadingInfoRepository.save(iLoadingInfo);

            IShipmentCargoStatus shipmentCargoStatus = getiShipmentCargoStatus(zlWebDTO, locationKey2);
            shipmentCargoStatusRepository.save(shipmentCargoStatus);
        }
    }

    /**
     * スケジュール時間更新。<BR>
     *
     * @param orderTimeUpdateRequest 注文時間更新リクエスト
     */
    @Override
    public void updateScheduleTime(OrderTimeUpdateRequest orderTimeUpdateRequest) {
        IShipmentTaskList shipmentTask = shipmentTaskListRepository.findByManagementNumberAndDetailNo(
            orderTimeUpdateRequest.getManagementNumber(), ZLWeb.OPERATION_SERIVCE_ORDER_2);
        LocalDate destinationDate = shipmentTask.getDestinationDate();
        LocalDateTime newDepartureDatetime = destinationDate.atTime(orderTimeUpdateRequest.getDepartureTime());
        LocalDateTime newArrivalDatetime = destinationDate.atTime(orderTimeUpdateRequest.getArrivalTime());
        shipmentTask.setDestinationTimeFrom(orderTimeUpdateRequest.getArrivalTime().toString());
        shipmentTask.setDestinationTimeTo(orderTimeUpdateRequest.getArrivalTime().toString());
        shipmentTask.setDestinationDatetime(destinationDate.atTime(orderTimeUpdateRequest.getArrivalTime()));
        shipmentTaskListRepository.save(shipmentTask);

        List<IVehicleSchedulePlan> vehicleSchedulePlans = vehicleSchedulePlanRepository.findAllByDeliveryServiceKey(
            orderTimeUpdateRequest.getManagementNumber());
        vehicleSchedulePlans.forEach(vehicleSchedulePlan -> {
            if (vehicleSchedulePlan.getOpeServiceOrder() == ZLWeb.OPERATION_SERIVCE_ORDER_1) {
                vehicleSchedulePlan.setOperationDatetime(newDepartureDatetime);
            } else {
                vehicleSchedulePlan.setOperationDatetime(newArrivalDatetime);
            }
            vehicleSchedulePlanRepository.save(vehicleSchedulePlan);
        });

        String opeServiceKey = vehicleSchedulePlans.get(0).getOpeServiceKey();
        String vehicleScheduleKey = vehicleSchedulePlans.get(0).getVehicleScheduleKey();

        List<MOperatingServicePlan> operatingServicePlans = operatingServicePlanRepository.findAllByOpeServiceKey(
            opeServiceKey);
        operatingServicePlans.forEach(operatingServicePlan -> {
            if (operatingServicePlan.getOpeServiceOrder() == ZLWeb.OPERATION_SERIVCE_ORDER_1) {
                operatingServicePlan.setOperationTime(orderTimeUpdateRequest.getDepartureTime().toString());
            } else {
                operatingServicePlan.setOperationTime(orderTimeUpdateRequest.getArrivalTime().toString());
            }
            operatingServicePlanRepository.save(operatingServicePlan);
        });

        IVehicleSchedule vehicleSchedule = vehicleScheduleRepository.findByVehicleScheduleKey(vehicleScheduleKey);
        vehicleSchedule.setClockInDatetime(newDepartureDatetime);
        vehicleSchedule.setClockOutDatetime(newArrivalDatetime);
        vehicleSchedule.setDepartureDatetime(newDepartureDatetime);
        vehicleSchedule.setArrivalDatetime(newArrivalDatetime);
        vehicleScheduleRepository.save(vehicleSchedule);

    }

    /**
     * 貨物ステータス取得。<BR>
     *
     * @param zlWebDTO ZLWebDTO
     * @return 貨物ステータス
     */
    private IShipmentCargoStatus getiShipmentCargoStatus(ZLWebDTO zlWebDTO, String destinationKey) {
        IShipmentCargoStatus shipmentCargoStatus = new IShipmentCargoStatus();
        shipmentCargoStatus.setDestinationCenterKey(destinationKey);
        shipmentCargoStatus.setShipmentKbn(ZLWeb.SHIPMENT_KBN_4);
        shipmentCargoStatus.setDeliveryServiceKey(zlWebDTO.getManagementNumber());
        shipmentCargoStatus.setManagementNumber(zlWebDTO.getManagementNumber());
        shipmentCargoStatus.setDetailNo(ZLWeb.OPERATION_SERIVCE_ORDER_1);
        shipmentCargoStatus.setCargoStatusKbn(ZLWeb.CARGO_STATUS);
        shipmentCargoStatus.setDeleteFlg(ZLWeb.DELETE_FLG);
        shipmentCargoStatus.setProcessId(ZLWeb.PROCESS_ID);
        return shipmentCargoStatus;
    }

    /**
     * 運行サービス計画設定。<BR>
     *
     * @param newOpeServiceKey 新しい運行サービスキー
     * @param order            順序
     * @param operationKbn     運行区分
     * @param operationTime    運行時間
     * @param endPoint         エンドポイント
     * @return 運行サービス計画
     */
    private MOperatingServicePlan setOperatingServicePlan(String newOpeServiceKey, int order, String locationKey,
        String operationKbn,
        String operationTime, String endPoint) {
        MOperatingServicePlan mOperatingServicePlan = new MOperatingServicePlan();
        mOperatingServicePlan.setOpeServiceKey(newOpeServiceKey);
        mOperatingServicePlan.setOpeServiceOrder(order);
        mOperatingServicePlan.setLocationKey(locationKey);
        mOperatingServicePlan.setOperationKbn(operationKbn);
        mOperatingServicePlan.setOperationTime(operationTime);
        mOperatingServicePlan.setShippingEndPoint(endPoint);
        mOperatingServicePlan.setDeleteFlg(ZLWeb.DELETE_FLG);
        mOperatingServicePlan.setProcessId(ZLWeb.PROCESS_ID);
        return mOperatingServicePlan;
    }

    /**
     * 車両スケジュール計画設定。<BR>
     *
     * @param newVehicleScheduleKey 新しい車両スケジュールキー
     * @param order                 順序
     * @param opeServiceKey         運行サービスキー
     * @param operationDatetime     運行日時
     * @param managementNumber      管理番号
     * @return 車両スケジュール計画
     */
    private IVehicleSchedulePlan setVehicleSchedulePlan(String newVehicleScheduleKey, int order, String opeServiceKey,
        LocalDateTime operationDatetime, String managementNumber) {
        IVehicleSchedulePlan vehicleSchedulePlan = new IVehicleSchedulePlan();
        vehicleSchedulePlan.setVehicleScheduleKey(newVehicleScheduleKey);
        vehicleSchedulePlan.setOpeServiceOrder(order);
        vehicleSchedulePlan.setOpeServiceKey(opeServiceKey);
        vehicleSchedulePlan.setOperationDatetime(operationDatetime);
        vehicleSchedulePlan.setDeleteFlg(ParamConstant.ZLWeb.DELETE_FLG);
        vehicleSchedulePlan.setProcessId(ZLWeb.PROCESS_ID);
        vehicleSchedulePlan.setDeliveryServiceKey(managementNumber);
        return vehicleSchedulePlan;
    }

    /**
     * 車両スケジュール取得。<BR>
     *
     * @param zlWebDTO              ZLWebDTO
     * @param newVehicleScheduleKey 新しい車両スケジュールキー
     * @param newOpeServiceKey      新しい運行サービスキー
     * @return 車両スケジュール
     */
    private IVehicleSchedule getiVehicleSchedule(ZLWebDTO zlWebDTO, String newVehicleScheduleKey,
        String newOpeServiceKey) {
        IVehicleSchedule vehicleSchedule = new IVehicleSchedule();
        vehicleSchedule.setVehicleScheduleKey(newVehicleScheduleKey);
        vehicleSchedule.setOpeServiceKey(newOpeServiceKey);
        vehicleSchedule.setClockInDatetime(zlWebDTO.getStartDate().atTime(zlWebDTO.getStartTime()));
        vehicleSchedule.setClockOutDatetime(
            zlWebDTO.getEndDate().atTime(zlWebDTO.getEndTime()));
        vehicleSchedule.setDepartureDate(zlWebDTO.getStartDate());
        vehicleSchedule.setDepartureDatetime(zlWebDTO.getStartDate().atTime(zlWebDTO.getStartTime()));
        vehicleSchedule.setArrivalDatetime(zlWebDTO.getEndDate().atTime(zlWebDTO.getEndTime()));
        vehicleSchedule.setCompanyCode(ZLWeb.LOGISTIC_COMPANY_CODE);
        vehicleSchedule.setLogisticsCompanyCode(ZLWeb.LOGISTIC_COMPANY_CODE);
        vehicleSchedule.setDeleteFlg(ZLWeb.DELETE_FLG);
        vehicleSchedule.setProcessId(ZLWeb.PROCESS_ID);
        return vehicleSchedule;
    }

    /**
     * 注文明細保存。<BR>
     *
     * @param zlWebDTO ZLWebDTO
     */
    private void saveOrderDetails(ZLWebDTO zlWebDTO, String shipFrom, String shipTo) {
        for (int i = 0; i < zlWebDTO.getZlWebItemDTOList().size(); i++) {
            IOrderDetail orderDetail = createOrderDetail(zlWebDTO, i, shipFrom, shipTo);
            orderDetailRepository.save(orderDetail);
        }
    }

    /**
     * 幹線配車キー生成。<BR>
     *
     * @param lastTrunkLineAllocationKey 前の幹線配車キー
     * @return 新しい幹線配車キー
     */
    private String generateNextTrunkLineAllocationKey(String lastTrunkLineAllocationKey) {
        String prefix = "KTL";
        String numberFormat = "%017d";  // 17 zeros for padding

        if (lastTrunkLineAllocationKey == null || lastTrunkLineAllocationKey.isEmpty()) {
            return String.format(prefix + numberFormat, 1);  // Start with KHT00000000000000001
        }

        try {
            // Extract the numeric part (last 17 digits)
            String numericPart = lastTrunkLineAllocationKey.substring(3);  // Skip "KHT"
            long number = Long.parseLong(numericPart);

            // Increment and format with leading zeros
            return String.format(prefix + numberFormat, number + 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid trunk line allocation key format", e);
        }
    }

    /**
     * 運送タスクキー生成。<BR>
     *
     * @param lastShipmentKey 前の運送タスクキー
     * @return 新しい運送タスクキー
     */
    private String generateNextShipmentTaskKey(String lastShipmentKey) {
        String prefix = "KHT";
        String numberFormat = "%017d";  // 17 zeros for padding

        if (lastShipmentKey == null || lastShipmentKey.isEmpty()) {
            return String.format(prefix + numberFormat, 1);  // Start with KHT00000000000000001
        }

        try {
            // Extract the numeric part (last 17 digits)
            String numericPart = lastShipmentKey.substring(3);  // Skip "KHT"
            long number = Long.parseLong(numericPart);

            // Increment and format with leading zeros
            return String.format(prefix + numberFormat, number + 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid shipment task key format", e);
        }
    }

    /**
     * 車両スケジュールキー生成。<BR>
     *
     * @param lastVehicleScheduleKey 前の車両スケジュールキー
     * @return 新しい車両スケジュールキー
     */
    private String generateNextVehicleScheduleKey(String lastVehicleScheduleKey) {
        String prefix = "LVS";
        String numberFormat = "%017d";  // 17 zeros for padding

        if (lastVehicleScheduleKey == null || lastVehicleScheduleKey.isEmpty()) {
            return String.format(prefix + numberFormat, 1);  // Start with LVS00000000000000001
        }

        try {
            // Extract the numeric part (last 17 digits)
            String numericPart = lastVehicleScheduleKey.substring(3);  // Skip "LVS"
            long number = Long.parseLong(numericPart);

            // Increment and format with leading zeros
            return String.format(prefix + numberFormat, number + 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid vehicle schedule key format", e);
        }
    }

    /**
     * 運行サービスキー生成。<BR>
     *
     * @param lastOpeServiceKey 前の運行サービスキー
     * @return 新しい運行サービスキー
     */
    private String generateNextOpeServiceKey(String lastOpeServiceKey) {
        String prefix = "LOP";
        String numberFormat = "%017d";  // 17 zeros for padding

        if (lastOpeServiceKey == null || lastOpeServiceKey.isEmpty()) {
            return String.format(prefix + numberFormat, 1);  // Start with LOP00000000000000001
        }

        try {
            // Extract the numeric part (last 17 digits)
            String numericPart = lastOpeServiceKey.substring(3);  // Skip "LOP"
            long number = Long.parseLong(numericPart);

            // Increment and format with leading zeros
            return String.format(prefix + numberFormat, number + 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid operating service key format", e);
        }
    }

    /**
     * 運行サービス設定。<BR>
     *
     * @param zlWebDTO         ZLWebDTO
     * @param newOpeServiceKey 新しい運行サービスキー
     * @return 運行サービス
     */
    private MOperatingService setOperatingService(ZLWebDTO zlWebDTO, String newOpeServiceKey, String routeKey) {
        MOperatingService mOperatingService = new MOperatingService();
        mOperatingService.setOpeServiceKey(newOpeServiceKey);
        mOperatingService.setOpeServiceName(zlWebDTO.getCarrierOperatorName());
        mOperatingService.setOpeServiceNameAbbreviation(zlWebDTO.getCarrierOperatorName());
        mOperatingService.setRouteKey(routeKey);
        mOperatingService.setSameVehicleKeyword(ZLWeb.SAME_VEHICLE_KEYWORD);
        mOperatingService.setClockInTime(String.valueOf(zlWebDTO.getStartTime()));
        mOperatingService.setClockOutTime(String.valueOf(zlWebDTO.getEndTime()));
        mOperatingService.setOwnedCompanyCode(ZLWeb.LOGISTIC_COMPANY_CODE);
        mOperatingService.setLogisticsCompanyCode(ZLWeb.LOGISTIC_COMPANY_CODE);
        mOperatingService.setShowDateFrom(zlWebDTO.getStartDate());
        mOperatingService.setShowDateTo(zlWebDTO.getEndDate());
        mOperatingService.setDeleteFlg(ZLWeb.DELETE_FLG);
        mOperatingService.setProcessId(ZLWeb.PROCESS_ID);
        return mOperatingService;
    }

    /**
     * 運送タスク作成。<BR>
     *
     * @param zlWebDTO                          ZLWebDTO
     * @param newShipmentTaskKey                新しい運送タスクキー
     * @param shipmentKbn                       運送区分
     * @return 運送タスク
     */
    private IShipmentTaskList createShipmentTask(ZLWebDTO zlWebDTO, String newShipmentTaskKey, String shipmentKbn,
        String shipFrom, String shipTo) {
        IShipmentTaskList shipmentTask = new IShipmentTaskList();
        shipmentTask.setShipmentTaskKey(newShipmentTaskKey);
        shipmentTask.setDestinationCenterNameAbbreviation(shipFrom);
        shipmentTask.setShipmentShipperId(zlWebDTO.getShipperOperatorCode().length() > 5 ?
            zlWebDTO.getShipperOperatorCode().substring(0, 5) : zlWebDTO.getShipperOperatorCode());
        shipmentTask.setShipmentShipperName(
            BaseUtil.isNull(zlWebDTO.getShipperOperatorName()) ?
                ParamConstant.ZLWeb.DEFAULT_SHIPPER_SHIPMENT_NAME : zlWebDTO.getShipperOperatorName());
        shipmentTask.setShipFrom(BaseUtil.isNull(zlWebDTO.getDepartureFromName()) ?
            shipFrom : zlWebDTO.getDepartureFromName());
        shipmentTask.setShipTo(BaseUtil.isNull(zlWebDTO.getArrivalToName()) ?
            shipTo : zlWebDTO.getArrivalToName());
        shipmentTask.setDestinationDate(zlWebDTO.getEndDate());
        shipmentTask.setDestinationTimeFrom(String.valueOf(zlWebDTO.getEndTime()));
        shipmentTask.setDestinationTimeTo(String.valueOf(zlWebDTO.getEndTime()));
        shipmentTask.setShippingCompanyName(zlWebDTO.getCarrierOperatorName());
        shipmentTask.setVehicleSpec(ParamConstant.ZLWeb.DEFAULT_VEHICLE_SPEC);
        shipmentTask.setVehicleNumber(zlWebDTO.getVehicleNumber());
        shipmentTask.setUnit(BigDecimal.valueOf(zlWebDTO.getZlWebItemDTOList().size()));
        shipmentTask.setDeleteFlg(ParamConstant.ZLWeb.DELETE_FLG);
        shipmentTask.setLogisticsCompanyCode(ParamConstant.ZLWeb.LOGISTIC_COMPANY_CODE);
        shipmentTask.setProcessId(ParamConstant.ZLWeb.PROCESS_ID);
        shipmentTask.setOrderNumber(ParamConstant.ZLWeb.ORDER_NUMBER);
        shipmentTask.setShipmentKbn(shipmentKbn);
        shipmentTask.setManagementNumber(zlWebDTO.getManagementNumber());
        shipmentTask.setDetailNo(ZLWeb.OPERATION_SERIVCE_ORDER_2);
        shipmentTask.setDestinationDatetime(zlWebDTO.getEndDate().atTime(zlWebDTO.getEndTime()));
        shipmentTask.setTrunkScheduleDate(zlWebDTO.getStartDate());
        shipmentTask.setTrunkCenterNameAbbreviation(shipFrom);
        shipmentTask.setTrunkShippingName(zlWebDTO.getCarrierOperatorName());
        shipmentTask.setTrunkScheduleDatetime(zlWebDTO.getStartDate().atTime(
            LocalTime.parse(zlWebDTO.getShipmentDateFrom())));
        return shipmentTask;
    }

    /**
     * 注文明細作成。<BR>
     *
     * @param zlWebDTO ZLWebDTO
     * @param i        インデックス
     * @return 注文明細
     */
    private IOrderDetail createOrderDetail(ZLWebDTO zlWebDTO, int i, String shipFrom, String shipTo) {
        IOrderDetail orderDetail = new IOrderDetail();
        orderDetail.setManagementNumber(zlWebDTO.getManagementNumber());
        orderDetail.setDetailNo(2 - i);
        orderDetail.setShipmentShipperId(zlWebDTO.getShipperOperatorCode().length() > 5 ?
            zlWebDTO.getShipperOperatorCode().substring(0, 5) : zlWebDTO.getShipperOperatorCode());
        orderDetail.setItemCode(
            zlWebDTO.getZlWebItemDTOList().get(i).getItemCode() != null &&
                zlWebDTO.getZlWebItemDTOList().get(i).getItemCode().length() > 15
                ? zlWebDTO.getZlWebItemDTOList().get(i).getItemCode().substring(0, 15)
                : zlWebDTO.getZlWebItemDTOList().get(i).getItemCode()
        );
        orderDetail.setItemName(zlWebDTO.getZlWebItemDTOList().get(i).getItemName());
        orderDetail.setLengthSize(zlWebDTO.getZlWebItemDTOList().get(i).getLengthSize());
        orderDetail.setWeight(zlWebDTO.getZlWebItemDTOList().get(i).getWeight());
        orderDetail.setHeightSize(zlWebDTO.getZlWebItemDTOList().get(i).getHeightSize());
        orderDetail.setWidthSize(zlWebDTO.getZlWebItemDTOList().get(i).getWidthSize());
        orderDetail.setUnit(zlWebDTO.getZlWebItemDTOList().get(i).getUnit());
        orderDetail.setDeleteFlg(ParamConstant.ZLWeb.DELETE_FLG);
        orderDetail.setLogisticsCompanyCode(ParamConstant.ZLWeb.LOGISTIC_COMPANY_CODE);
        orderDetail.setProcessId(ParamConstant.ZLWeb.PROCESS_ID);
        orderDetail.setShipFrom(shipFrom);
        orderDetail.setShipTo(shipTo);
        return orderDetail;
    }
}
