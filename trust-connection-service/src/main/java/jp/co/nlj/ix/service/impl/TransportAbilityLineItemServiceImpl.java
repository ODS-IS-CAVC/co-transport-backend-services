package jp.co.nlj.ix.service.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jp.co.nlj.ix.constant.MessageConstant.ShipperTrspCapacity;
import jp.co.nlj.ix.constant.ParamConstant;
import jp.co.nlj.ix.domain.CarInfo;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.dto.shipperTrspCapacity.AttributeDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CarInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.RoadCarrDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.TransportAbilityLineItemDTO;
import jp.co.nlj.ix.mapper.CarInfoMapper;
import jp.co.nlj.ix.mapper.DriverInformationMapper;
import jp.co.nlj.ix.mapper.TransportAbilityMessageInfoMapper;
import jp.co.nlj.ix.repository.CarInfoRepository;
import jp.co.nlj.ix.repository.TransportAbilityLineItemCustomRepository;
import jp.co.nlj.ix.service.TransportAbilityLineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransportAbilityLineItemServiceImpl implements TransportAbilityLineItemService {

    private final CarInfoMapper carInfoMapper;

    private final DriverInformationMapper driverInformationMapper;

    private final TransportAbilityMessageInfoMapper transportAbilityMessageInfoMapper;

    private final TransportAbilityLineItemCustomRepository transportAbilityLineItemCustomRepository;

    @Resource(name = "userContext")
    private UserContext userContext;
    @Autowired
    private CarInfoRepository carInfoRepository;

    /**
     * 指定した条件の運送能力情報を取得
     *
     * @param params
     * @return 荷主向け運行案件の運送能力情報
     */
    @Override
    @Transactional
    public AttributeDTO getAllTransportAbilityLineItems(ShipperTransportCapacitySearchDTO params, int page, int limit) {
        /*String operatorId = userContext.getUser().getCompanyId();*/

        String maxTrspOpDateTrmStrtDate = params.getMaxTrspOpDateTrmStrtDate();
        String minTrspOpDateTrmStrtDate = params.getMinTrspOpDateTrmStrtDate();
        String maxTrspOpDateTrmEndDate = params.getMaxTrspOpDateTrmEndDate();
        String minTrspOpDateTrmEndDate = params.getMinTrspOpDateTrmEndDate();

        checkDateRange(maxTrspOpDateTrmStrtDate, minTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_STRT_DATE,
            ParamConstant.MIN_TRSP_STRT_DATE);
        checkDateRange(maxTrspOpDateTrmEndDate, minTrspOpDateTrmEndDate, ParamConstant.MAX_TRSP_END_DATE,
            ParamConstant.MIN_TRSP_END_DATE);
        checkDateRange(maxTrspOpDateTrmEndDate, maxTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_END_DATE,
            ParamConstant.MAX_TRSP_STRT_DATE);
        checkDateRange(maxTrspOpDateTrmEndDate, minTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_END_DATE,
            ParamConstant.MIN_TRSP_STRT_DATE);
        checkDateRange(minTrspOpDateTrmEndDate, maxTrspOpDateTrmStrtDate, ParamConstant.MIN_TRSP_END_DATE,
            ParamConstant.MAX_TRSP_STRT_DATE);
        checkDateRange(minTrspOpDateTrmEndDate, minTrspOpDateTrmStrtDate, ParamConstant.MIN_TRSP_END_DATE,
            ParamConstant.MIN_TRSP_STRT_DATE);

        String maxTrspOpPlanDateTrmStrtTime = params.getMaxTrspOpPlanDateTrmStrtTime();
        String minTrspOpPlanDateTrmStrtTime = params.getMinTrspOpPlanDateTrmStrtTime();
        String maxTrspOpPlanDateTrmEndTime = params.getMaxTrspOpPlanDateTrmEndTime();
        String minTrspOpPlanDateTrmEndTime = params.getMinTrspOpPlanDateTrmEndTime();

        checkTimeRange(maxTrspOpPlanDateTrmStrtTime, minTrspOpPlanDateTrmStrtTime, maxTrspOpDateTrmStrtDate,
            minTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_STRT_TIME, ParamConstant.MIN_TRSP_STRT_TIME);
        checkTimeRange(maxTrspOpPlanDateTrmEndTime, minTrspOpPlanDateTrmEndTime, maxTrspOpDateTrmEndDate,
            minTrspOpDateTrmEndDate, ParamConstant.MAX_TRSP_END_TIME, ParamConstant.MIN_TRSP_END_TIME);
        checkTimeRange(maxTrspOpPlanDateTrmEndTime, maxTrspOpPlanDateTrmStrtTime, maxTrspOpDateTrmEndDate,
            maxTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_END_TIME, ParamConstant.MAX_TRSP_STRT_TIME);
        checkTimeRange(maxTrspOpPlanDateTrmEndTime, minTrspOpPlanDateTrmStrtTime, maxTrspOpDateTrmEndDate,
            minTrspOpDateTrmStrtDate, ParamConstant.MAX_TRSP_END_TIME, ParamConstant.MIN_TRSP_STRT_TIME);
        checkTimeRange(minTrspOpPlanDateTrmEndTime, maxTrspOpPlanDateTrmStrtTime, minTrspOpDateTrmEndDate,
            maxTrspOpDateTrmStrtDate, ParamConstant.MIN_TRSP_END_TIME, ParamConstant.MAX_TRSP_STRT_TIME);
        checkTimeRange(minTrspOpPlanDateTrmEndTime, minTrspOpPlanDateTrmStrtTime, minTrspOpDateTrmEndDate,
            minTrspOpDateTrmStrtDate, ParamConstant.MIN_TRSP_END_TIME, ParamConstant.MIN_TRSP_STRT_TIME);

        Page<VehicleAvbResourceItem> list = transportAbilityLineItemCustomRepository.searchTransportPlanItem(params,
            page - 1, limit);
        List<TransportAbilityLineItemDTO> transportAbilityLineItemDTOS = list.stream()
            .map(item -> {
                CarInfo carInfo = carInfoRepository.findById(item.getCarInfoId()).get();
                RoadCarrDTO roadCarrDTO = transportAbilityMessageInfoMapper.mapToRoadCarrDTO(
                    carInfo.getTransportAbilityLineItem());
                LogisticsServiceProviderDTO logisticsServiceProviderDTO = transportAbilityMessageInfoMapper.mapToLogisticsServiceProviderDTO(
                    carInfo.getTransportAbilityLineItem());
                Set<CarInfoDTO> carInfoDTOS = new HashSet<>();
                CarInfoDTO carInfoDTO = carInfoMapper.map(carInfo);
                Optional<Long> operationId = carInfo.getVehicleAvailabilityResources().stream()
                    .findFirst()
                    .map(VehicleAvailabilityResource::getId);
                carInfoDTO.setOperationId(operationId.orElse(0L).toString());
                if (item.getPrice() != null) {
                    carInfoDTO.setFreightRate(String.valueOf(item.getPrice()));
                }
                carInfoDTOS.add(carInfoDTO);

                return new TransportAbilityLineItemDTO(
                    item.getOperatorCode(),
                    roadCarrDTO,
                    logisticsServiceProviderDTO,
                    carInfoDTOS,
                    driverInformationMapper.mapDriverInformationList(
                        carInfo.getTransportAbilityLineItem().getDriverInformations())
                );
            })
            .collect(Collectors.toList());
        return new AttributeDTO(transportAbilityLineItemDTOS);
    }

    /**
     * compare Date or Time
     *
     * @param maxDateStr, minDateStr
     * @return boolean
     */
    private boolean compareDateAndTimeRange(String maxDateStr, String minDateStr) {
        int maxDate = (maxDateStr != null) ? Integer.parseInt(maxDateStr) : 0;
        int minDate = (minDateStr != null) ? Integer.parseInt(minDateStr) : 0;
        return maxDate < minDate;
    }

    /**
     * check Date
     *
     * @param maxDateStr, minDateStr, maxParam, minParam
     */
    private void checkDateRange(String maxDateStr, String minDateStr, String maxParam, String minParam) {
        if (maxDateStr != null && minDateStr != null && compareDateAndTimeRange(maxDateStr, minDateStr)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperTrspCapacity.SHIPPER_TRSP_CAPACITY_PARAM,
                maxParam, minParam);
        }
    }

    /**
     * check Time
     *
     * @param maxTimeStr, minTimeStr, maxDateStr, minDateStr, maxParam, minParam
     */
    private void checkTimeRange(String maxTimeStr, String minTimeStr, String maxDateStr, String minDateStr,
        String maxParam, String minParam) {
        if (maxTimeStr != null && minTimeStr != null && maxDateStr == null && minDateStr == null
            && compareDateAndTimeRange(maxTimeStr, minTimeStr)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperTrspCapacity.SHIPPER_TRSP_CAPACITY_PARAM,
                maxParam, minParam);
        }
    }
}
