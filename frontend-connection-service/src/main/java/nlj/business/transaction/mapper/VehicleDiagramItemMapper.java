package nlj.business.transaction.mapper;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;
import nlj.business.transaction.dto.VehicleDiagramItemDTO;
import nlj.business.transaction.dto.VehicleDiagramItemSnapshot;
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
public interface VehicleDiagramItemMapper {

    @Mapping(target = "vehicleDiagramId", source = "vehicleDiagramItem.vehicleDiagram.id")
    VehicleDiagramItemSnapshot mapToSnapshot(VehicleDiagramItem vehicleDiagramItem);

    List<VehicleDiagramItemDTO> mapToVehicleDiagramItemDTO(List<VehicleDiagramItem> vehicleDiagramItems);

    VehicleDiagramItemDTO mapVehicleDiagramItemDTO(VehicleDiagramItem vehicleDiagramItems);
}
