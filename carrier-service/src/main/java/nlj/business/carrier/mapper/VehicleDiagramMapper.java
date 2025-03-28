package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.dto.vehicleDiagram.request.VehicleDiagramDTO;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両ダイアグラムアイテムトレーラマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleDiagramMapper {

    @Mapping(source = "diagramHeadId", target = "vehicleDiagramHead.id")
    @Mapping(source = "roundTripType", target = "roundTripType")
    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "departureFrom", target = "departureFrom")
    @Mapping(source = "arrivalTo", target = "arrivalTo")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "dayWeek", target = "dayWeek")
    @Mapping(source = "adjustmentPrice", target = "adjustmentPrice")
    @Mapping(source = "commonPrice", target = "commonPrice")
    @Mapping(source = "cutOffPrice", target = "cutOffPrice")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "vehicleDiagramItems", ignore = true)
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM)
    VehicleDiagram toVehicleDiagram(VehicleDiagramDTO vehicleDiagramDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "operatorId", target = "operatorId")
    @Mapping(source = "vehicleDiagramHead.id", target = "diagramHeadId")
    @Mapping(source = "roundTripType", target = "roundTripType")
    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "departureFrom", target = "departureFrom")
    @Mapping(source = "arrivalTo", target = "arrivalTo")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "dayWeek", target = "dayWeek")
    @Mapping(source = "adjustmentPrice", target = "adjustmentPrice")
    @Mapping(source = "commonPrice", target = "commonPrice")
    @Mapping(source = "cutOffPrice", target = "cutOffPrice")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "vehicleDiagramItems", ignore = true)
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_DTO)
    VehicleDiagramResponseDTO toVehicleDiagramDTO(VehicleDiagram vehicleDiagram);

}