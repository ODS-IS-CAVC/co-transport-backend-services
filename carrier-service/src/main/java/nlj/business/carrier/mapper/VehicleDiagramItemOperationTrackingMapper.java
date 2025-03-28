package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramItemOperationTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTracking;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingDetailsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両ダイアグラムアイテム操作追跡マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface VehicleDiagramItemOperationTrackingMapper {

    @Mapping(source = "operationDate", target = "operationDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "operationTime", target = "operationTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    VehicleDiagramItemTrackingDetailsResponseDTO toVehicleDiagramItemOperationTrackingResponseDTO(
        VehicleDiagramItemOperationTracking vehicleDiagramItemTracking);

    VehicleDiagramItemOperationTracking toVehicleDiagramItemOperationTracking(
        VehicleDiagramItemTracking vehicleDiagramItemTracking);
}
