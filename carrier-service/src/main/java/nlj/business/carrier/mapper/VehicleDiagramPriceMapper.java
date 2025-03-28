package nlj.business.carrier.mapper;

import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.dto.vehicleDiagramPrice.VehicleDiagramItemPriceDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.VehicleDiagramPriceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleDiagramPriceMapper {

    @Mapping(source = "tripName", target = "tripName")
    @Mapping(source = "adjustmentPrice", target = "adjustmentPrice")
    @Mapping(source = "commonPrice", target = "commonPrice")
    @Mapping(source = "cutOffPrice", target = "cutOffPrice")
    VehicleDiagramPriceDTO toVehicleDiagramPriceDTO(VehicleDiagram vehicleDiagram);

    VehicleDiagramItemPriceDTO toVehicleDiagramItemPriceDTO(VehicleDiagramItem vehicleDiagramItem);
}
