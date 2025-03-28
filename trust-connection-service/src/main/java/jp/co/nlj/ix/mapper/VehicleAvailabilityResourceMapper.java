package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両可用性リソース マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface VehicleAvailabilityResourceMapper {

    @Mapping(target = "trspOpDateTrmStrtDate", source = "trspOpDateTrmStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "trspOpPlanDateTrmStrtTime", source = "trspOpPlanDateTrmStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "trspOpDateTrmEndDate", source = "trspOpDateTrmEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "trspOpPlanDateTrmEndTime", source = "trspOpPlanDateTrmEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbDateCllDate", source = "avbDateCllDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "avbFromTimeOfCllTime", source = "avbFromTimeOfCllTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbToTimeOfCllTime", source = "avbToTimeOfCllTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "estiDelDatePrfmDttm", source = "estiDelDatePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "avbFromTimeOfDelTime", source = "avbFromTimeOfDelTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbToTimeOfDelTime", source = "avbToTimeOfDelTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    VehicleAvailabilityResource mapToVehicleAvailabilityResource(VehicleAvailabilityResourceDTO dto);
}
