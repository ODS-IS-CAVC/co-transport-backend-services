package nlj.business.carrier.link.mapper;

import java.util.Set;
import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CarInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * カーインフォメーションマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
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
    VehicleAvailabilityResourceDTO mapToVehicleAvailabilityResourceDTO(
        VehicleAvailabilityResource vehicleAvailabilityResource);
}
