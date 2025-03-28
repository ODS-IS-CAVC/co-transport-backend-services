package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramHead;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramHeadResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両ダイアグラムヘッダマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleDiagramHeadMapper {

    @Mapping(source = "departureFrom", target = "departureFrom")
    @Mapping(source = "arrivalTo", target = "arrivalTo")
    @Mapping(source = "oneWayTime", target = "oneWayTime")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "repeatDay", target = "repeatDay")
    @Mapping(source = "trailerNumber", target = "trailerNumber")
    @Mapping(source = "isRoundTrip", target = "isRoundTrip")
    @Mapping(source = "originType", target = "originType")
    @Mapping(source = "importId", target = "importId")
    @Mapping(source = "status", target = "status")
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_HEAD)
    VehicleDiagramHead toVehicleDiagramHead(VehicleDiagramHeadDTO vehicleDiagramHeadDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "operatorId", target = "operatorId")
    @Mapping(source = "departureFrom", target = "departureFrom")
    @Mapping(source = "arrivalTo", target = "arrivalTo")
    @Mapping(source = "oneWayTime", target = "oneWayTime")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "repeatDay", target = "repeatDay")
    @Mapping(source = "trailerNumber", target = "trailerNumber")
    @Mapping(source = "isRoundTrip", target = "isRoundTrip")
    @Mapping(source = "originType", target = "originType")
    @Mapping(source = "importId", target = "importId")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "vehicleDiagrams", ignore = true)
    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_HEAD_DTO)
    VehicleDiagramHeadResponseDTO toVehicleDiagramHeadDTO(VehicleDiagramHead vehicleDiagramHead);

    @Mapping(target = "id", ignore = true)
    void updateVehicleDiagramHeadFromDto(VehicleDiagramHeadDTO dto, @MappingTarget VehicleDiagramHead entity);
} 