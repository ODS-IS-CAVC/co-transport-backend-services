package nlj.business.transaction.mapper;

import java.util.List;
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.dto.MarketVehicleDiagramItemTrailerDTO;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 市場輸送計画アイテムマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface MarketVehicleDiagramItemTrailerMapper {

    List<MarketVehicleDiagramItemTrailerDTO> map(
        List<MarketVehicleDiagramItemTrailer> marketVehicleDiagramItemTrailerList);

    @Mapping(target = "vehicleDiagramItemTrailerId", source = "vehicleDiagramItemTrailerSnapshot.id")
    MarketVehicleDiagramItemTrailer mapToMarket(VehicleDiagramItemTrailerSnapshot vehicleDiagramItemTrailerSnapshot);

    MarketVehicleDiagramItemTrailerDTO mapToMarketVehicleDiagramItemTrailerDTO(
        MarketVehicleDiagramItemTrailer marketVehicleDiagramItemTrailer);
}
