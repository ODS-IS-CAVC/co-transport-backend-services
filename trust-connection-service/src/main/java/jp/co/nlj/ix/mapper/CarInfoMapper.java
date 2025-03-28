package jp.co.nlj.ix.mapper;

import java.util.Set;
import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.CarInfo;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CarInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <PRE>
 * カーインフォメーションマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface CarInfoMapper {

    @Mapping(target = "serviceStrtDate", source = "serviceStrtDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "serviceStrtTime", source = "serviceStrtTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "serviceEndDate", source = "serviceEndDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "serviceEndTime", source = "serviceEndTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "vehicleAvailabilityResourceDTOS", source = "carInfoDTO.vehicleAvailabilityResources")
    CarInfoDTO map(CarInfo carInfoDTO);

    Set<CarInfoDTO> mapCarInfoList(Set<CarInfo> carInfos);

    @Mapping(target = "trspOpDateTrmStrtDate", source = "trspOpDateTrmStrtDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "trspOpPlanDateTrmStrtTime", source = "trspOpPlanDateTrmStrtTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "trspOpDateTrmEndDate", source = "trspOpDateTrmEndDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "trspOpPlanDateTrmEndTime", source = "trspOpPlanDateTrmEndTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "avbDateCllDate", source = "avbDateCllDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "avbFromTimeOfCllTime", source = "avbFromTimeOfCllTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "avbToTimeOfCllTime", source = "avbToTimeOfCllTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "estiDelDatePrfmDttm", source = "estiDelDatePrfmDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "avbFromTimeOfDelTime", source = "avbFromTimeOfDelTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "avbToTimeOfDelTime", source = "avbToTimeOfDelTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "cutOffInfoDTOS", source = "cutOffInfos")
    @Mapping(target = "freeTimeInfoDTOS", source = "freeTimeInfos")
    @Mapping(target = "operationId", source = "id")
    VehicleAvailabilityResourceDTO mapToVehicleAvailabilityResourceDTO(
        VehicleAvailabilityResource vehicleAvailabilityResource);

    @Mapping(target = "serviceStrtDate", source = "serviceStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceStrtTime", source = "serviceStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "serviceEndDate", source = "serviceEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceEndTime", source = "serviceEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    CarInfo mapToCarInfo(CarInfoDTO carInfoDTO);

    @Mapping(target = "vehicleAvailabilityResources", ignore = true)
    @Mapping(target = "serviceStrtDate", source = "serviceStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceStrtTime", source = "serviceStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "serviceEndDate", source = "serviceEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceEndTime", source = "serviceEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "id", ignore = true)
    void updateCarInfo(@MappingTarget CarInfo target, CarInfoDTO carInfoDTO);
}
