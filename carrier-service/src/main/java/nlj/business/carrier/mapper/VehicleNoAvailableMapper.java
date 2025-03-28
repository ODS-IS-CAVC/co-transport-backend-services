package nlj.business.carrier.mapper;


import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleNoAvailable;
import nlj.business.carrier.dto.vehicleInfo.VehicleNoAvailableDTO;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleNoAvailableResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 利用可能な車両マップはありません。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleNoAvailableMapper {

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "dayWeek", target = "dayWeek")
    VehicleNoAvailable toEntity(VehicleNoAvailableDTO vehicleNoAvailableDTO);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    VehicleNoAvailableDTO toDto(VehicleNoAvailable vehicleNoAvailable);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "vehicleInfoId", target = "vehicleInfoId")
    @Mapping(source = "dayWeek", target = "dayWeek")
    VehicleNoAvailableResponseDTO toResponseDto(VehicleNoAvailable vehicleNoAvailable);
}
