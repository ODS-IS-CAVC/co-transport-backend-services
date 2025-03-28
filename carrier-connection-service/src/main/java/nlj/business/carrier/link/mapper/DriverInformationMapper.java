package nlj.business.carrier.link.mapper;

import java.util.Set;
import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.DriverAvailabilityTime;
import nlj.business.carrier.link.domain.DriverInformation;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverAvailabilityTimeDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * ドライバー情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface DriverInformationMapper {

    @Mapping(target = "driverAvailabilityTimeDTOS", source = "driverInformation.driverAvailabilityTimes")
    DriverInformationDTO map(DriverInformation driverInformation);

    Set<DriverInformationDTO> mapDriverInformationList(Set<DriverInformation> driverInformations);

    @Mapping(target = "drvAvbFromDate", source = "drvAvbFromDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "drvAvbFromTimeOfWrkgTime", source = "drvAvbFromTimeOfWrkgTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "drvAvbToDate", source = "drvAvbToDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "drvAvbToTimeOfWrkgTime", source = "drvAvbToTimeOfWrkgTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(target = "drvFrmrOptgDate", source = "drvFrmrOptgDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "drvFrmrOpEndTime", source = "drvFrmrOpEndTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    DriverAvailabilityTimeDTO mapToDriverAvailabilityTimeDTO(DriverAvailabilityTime driverAvailabilityTime);
}
