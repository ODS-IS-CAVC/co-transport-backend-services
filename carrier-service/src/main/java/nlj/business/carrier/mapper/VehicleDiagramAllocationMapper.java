package nlj.business.carrier.mapper;

import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.dto.vehicleDiagramAllocation.request.VehicleDiagramAllocationRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両ダイアグラム割り当てマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleDiagramAllocationMapper {

    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_ALLOCATION)
    VehicleDiagramAllocation toVehicleDiagramAllocation(
        VehicleDiagramAllocationRequestDTO vehicleDiagramAllocationRequestDTO);

    @Named(MapperConstants.TO_VEHICLE_DIAGRAM_ALLOCATION_RESPONSE_DTO)
    VehicleDiagramAllocationResponseDTO toVehicleDiagramAllocationResponseDTO(
        VehicleDiagramAllocation vehicleDiagramAllocation);
}
