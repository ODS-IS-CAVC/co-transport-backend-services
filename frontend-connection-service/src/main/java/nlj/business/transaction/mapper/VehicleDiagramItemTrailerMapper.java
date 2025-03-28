package nlj.business.transaction.mapper;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerDTO;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 輸送計画項目マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleDiagramItemTrailerMapper {

    @Mapping(target = "operatorId", source = "vehicleDiagramItemTrailer.operatorId")
    @Mapping(target = "vehicleDiagramId", source = "vehicleDiagramItemTrailer.vehicleDiagram.id")
    @Mapping(target = "vehicleDiagramItemId", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.id")
    @Mapping(target = "departureFrom", source = "vehicleDiagramItemTrailer.vehicleDiagram.departureFrom")
    @Mapping(target = "arrivalTo", source = "vehicleDiagramItemTrailer.vehicleDiagram.arrivalTo")
    @Mapping(target = "day", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.day")
    @Mapping(target = "tripName", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.tripName")
    @Mapping(target = "departureTime", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.departureTime")
    @Mapping(target = "arrivalTime", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.arrivalTime")
    @Mapping(target = "vehicleCode", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.vehicleCode")
    @Mapping(target = "vehicleName", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.vehicleName")
    @Mapping(target = "vehicleType", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.vehicleType")
    @Mapping(target = "vehicleSize", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.vehicleSize")
    @Mapping(target = "temperatureRange", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.temperatureRange")
    @Mapping(target = "maxPayload", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.maxPayload")
    @Mapping(target = "totalLength", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.totalLength")
    @Mapping(target = "totalWidth", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.totalWidth")
    @Mapping(target = "totalHeight", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.totalHeight")
    @Mapping(target = "groundClearance", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.groundClearance")
    @Mapping(target = "doorHeight", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.doorHeight")
    @Mapping(target = "bodySpecification", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.bodySpecification")
    @Mapping(target = "bodyShape", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.bodyShape")
    @Mapping(target = "bodyConstruction", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.bodyConstruction")
    @Mapping(target = "images", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.images")
    @Mapping(target = "price", source = "vehicleDiagramItemTrailer.vehicleDiagramItem.price")
    @Mapping(target = "vehicleDiagramAllocationId", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.id")
    @Mapping(target = "vehicleInfoId", source = "vehicleDiagramItemTrailer.vehicleDiagramAllocation.vehicleInfo.id")
    VehicleDiagramItemTrailerSnapshot mapToSnapshot(VehicleDiagramItemTrailer vehicleDiagramItemTrailer);

    List<VehicleDiagramItemTrailerSnapshot> mapToListCarrierMatching(
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers);

    @Mapping(target = "vehicleDiagramId", source = "vehicleDiagram.id")
    @Mapping(target = "vehicleDiagramItemId", source = "vehicleDiagramItem.id")
    @Mapping(target = "vehicleDiagramAllocationId", source = "vehicleDiagramAllocation.id")
    VehicleDiagramItemTrailerDTO mapToDTO(VehicleDiagramItemTrailer source);
}
