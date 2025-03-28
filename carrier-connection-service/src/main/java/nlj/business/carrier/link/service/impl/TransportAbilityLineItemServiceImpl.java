package nlj.business.carrier.link.service.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.MessageConstant.ShipperTrspCapacity;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.dto.shipperTrspCapacity.AttributeDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CarInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverInformationDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.MessageInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.RoadCarrDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.TransportAbilityLineItemDTO;
import nlj.business.carrier.link.mapper.CarInfoMapper;
import nlj.business.carrier.link.mapper.DriverInformationMapper;
import nlj.business.carrier.link.mapper.TransportAbilityMessageInfoMapper;
import nlj.business.carrier.link.repository.TransportAbilityLineItemCustomRepository;
import nlj.business.carrier.link.service.TransportAbilityLineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    /**
     * 指定した条件の運送能力情報を取得
     *
     * @param params
     * @return 荷主向け運行案件の運送能力情報
     */
    @Override
    public ShipperTransportCapacityRegisterDTO getAllTransportAbilityLineItems(
        ShipperTransportCapacitySearchDTO params) {
        String operatorId = userContext.getUser().getCompanyId();

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

        List<TransportAbilityLineItem> list = transportAbilityLineItemCustomRepository.searchTransportPlanItem(
            operatorId, params);
        Set<TransportAbilityLineItemDTO> transportAbilityLineItemDTOS = new HashSet<>();
        MessageInfoDTO messageInfoDTO = null;
        Set<DriverInformationDTO> driverInfoDTOs;
        for (TransportAbilityLineItem item : list) {
            messageInfoDTO = transportAbilityMessageInfoMapper.mapMessageInfoToTransportAbility(
                item.getTransportAbilityMessageInfo());
            RoadCarrDTO roadCarrDTO = transportAbilityMessageInfoMapper.mapToRoadCarrDTO(item);
            LogisticsServiceProviderDTO logisticsServiceProviderDTO = transportAbilityMessageInfoMapper.mapToLogisticsServiceProviderDTO(
                item);
            Set<CarInfoDTO> carInfoDTOs = carInfoMapper.mapCarInfoList(item.getCarInfos());
            driverInfoDTOs = driverInformationMapper.mapDriverInformationList(item.getDriverInformations());
            TransportAbilityLineItemDTO transportAbilityLineItemDTO = new TransportAbilityLineItemDTO(
                roadCarrDTO,
                logisticsServiceProviderDTO,
                carInfoDTOs,
                driverInfoDTOs
            );
            transportAbilityLineItemDTOS.add(transportAbilityLineItemDTO);
        }
        AttributeDTO attributeDTO = new AttributeDTO(
            messageInfoDTO,
            transportAbilityLineItemDTOS
        );
        return new ShipperTransportCapacityRegisterDTO(
            ParamConstant.DataModelType.TEST_1,
            attributeDTO
        );
    }

    /**
     * 日付または時間を比較.<BR>
     *
     * @param maxDateStr 最大日付
     * @param minDateStr 最小日付
     * @return 比較結果
     */
    private boolean compareDateAndTimeRange(String maxDateStr, String minDateStr) {
        int maxDate = (maxDateStr != null) ? Integer.parseInt(maxDateStr) : 0;
        int minDate = (minDateStr != null) ? Integer.parseInt(minDateStr) : 0;
        return maxDate < minDate;
    }

    /**
     * 日付を比較.<BR>
     *
     * @param maxDateStr 最大日付
     * @param minDateStr 最小日付
     * @param maxParam   最大パラメータ
     * @param minParam   最小パラメータ
     */
    private void checkDateRange(String maxDateStr, String minDateStr, String maxParam, String minParam) {
        if (maxDateStr != null && minDateStr != null && compareDateAndTimeRange(maxDateStr, minDateStr)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, ShipperTrspCapacity.SHIPPER_TRSP_CAPACITY_PARAM,
                maxParam, minParam);
        }
    }

    /**
     * 時間を比較.<BR>
     *
     * @param maxTimeStr 最大時間
     * @param minTimeStr 最小時間
     * @param maxDateStr 最大日付
     * @param minDateStr 最小日付
     * @param maxParam   最大パラメータ
     * @param minParam   最小パラメータ
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