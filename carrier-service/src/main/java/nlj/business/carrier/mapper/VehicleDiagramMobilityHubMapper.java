package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.dto.vehicleDiagramMobilityHub.response.VehicleDiagramMobilityHubResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleDiagramMobilityHubMapper {

    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_MOBILITY_HUB)
    VehicleDiagramMobilityHub toVehicleDiagramMobilityHub(
        VehicleDiagramMobilityHubResponseDTO vehicleDiagramMobilityHubResponseDTO);

    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_MOBILITY_HUB_RESPONSE_DTO)
    VehicleDiagramMobilityHubResponseDTO toVehicleDiagramMobilityHubResponseDTO(
        VehicleDiagramMobilityHub vehicleDiagramMobilityHub);
}
