package jp.co.nlj.ix.mapper;

import java.util.Set;
import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.DriverAvailabilityTime;
import jp.co.nlj.ix.domain.DriverInformation;
import jp.co.nlj.ix.dto.shipperTrspCapacity.DriverAvailabilityTimeDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.DriverInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * ドライバー情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
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

    DriverInformation mapToDriverInformation(DriverInformationDTO driverInformationDTO);

    @Mapping(target = "drvAvbFromDate", source = "drvAvbFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvAvbFromTimeOfWrkgTime", source = "drvAvbFromTimeOfWrkgTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "drvAvbToDate", source = "drvAvbToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvAvbToTimeOfWrkgTime", source = "drvAvbToTimeOfWrkgTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "drvFrmrOptgDate", source = "drvFrmrOptgDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvFrmrOpEndTime", source = "drvFrmrOpEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    DriverAvailabilityTime mapToDriverAvailabilityTime(DriverAvailabilityTimeDTO driverAvailabilityTimeDTO);
}
