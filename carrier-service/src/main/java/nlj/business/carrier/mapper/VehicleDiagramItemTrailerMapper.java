package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両ダイアグラムアイテムトレーラマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleDiagramItemTrailerMapper {

    @Mapping(source = "day", target = "day", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "vehicleDiagramAllocation.id", target = "vehicleDiagramAllocationId")
    VehicleDiagramItemTrailerDTO toVehicleDiagramItemTrailerDto(VehicleDiagramItemTrailer vehicleDiagramItemTrailer);

    @Mapping(source = "day", target = "day", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    VehicleDiagramItemTrailer toVehicleDiagramItemTrailer(VehicleDiagramItemTrailerDTO vehicleDiagramItemTrailerDTO);
}
