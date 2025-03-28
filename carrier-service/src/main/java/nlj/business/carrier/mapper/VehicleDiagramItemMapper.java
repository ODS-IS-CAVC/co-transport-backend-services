package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.dto.vehicleDiagramItem.request.VehicleDiagramItemDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityDetailsDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両ダイアグラムアイテムマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleDiagramItemMapper {

    @Mapping(source = "operatorId", target = "operatorId")
    @Mapping(source = "vehicleDiagramId", target = "vehicleDiagram.id")
    @Mapping(source = "day", target = "day", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_ITEM)
    VehicleDiagramItem toVehicleDiagramItem(VehicleDiagramItemDTO vehicleDiagramItemDTO);

    @Mapping(source = "operatorId", target = "operatorId")
    @Mapping(source = "vehicleDiagram.id", target = "vehicleDiagramId")
    @Mapping(source = "day", target = "day", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_ITEM_DTO)
    VehicleDiagramItemResponseDTO toVehicleDiagramItemDTO(VehicleDiagramItem vehicleDiagramItem);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "day", target = "day", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "departureFrom", target = "departureFrom")
    @Mapping(source = "arrivalTo", target = "arrivalTo")
    @Mapping(target = "vehicleDiagramItemTrailers", ignore = true)
    VehicleDiagramItemByAbilityDetailsDTO toVehicleDiagramItemByAbilityDetailsDto(
        VehicleDiagramItem vehicleDiagramItem);

    VehicleDiagramItemTrackingResponseDTO toVehicleDiagramItemTrackingDTO(VehicleDiagramItem vehicleDiagramItem);
} 