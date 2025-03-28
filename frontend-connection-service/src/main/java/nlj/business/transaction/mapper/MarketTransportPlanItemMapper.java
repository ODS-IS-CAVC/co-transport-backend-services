package nlj.business.transaction.mapper;

import java.util.List;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.MarketTransportPlanItemDTO;
import nlj.business.transaction.dto.TransportPlanItemSnapshot;
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
public interface MarketTransportPlanItemMapper {

    List<MarketTransportPlanItemDTO> mapToMarkeTransportPlanItemDTO(
        List<MarketTransportPlanItem> marketTransportPlanItemList);

    @Mapping(target = "transportPlanItemId", source = "transportPlanItemSnapshot.id")
    @Mapping(target = "shipperCorperateId", source = "transportPlanItemSnapshot.transportPlanId")
    MarketTransportPlanItem mapToMarket(TransportPlanItemSnapshot transportPlanItemSnapshot);

    MarketTransportPlanItemDTO mapToMarketTransportPlanItemDTO(MarketTransportPlanItem marketTransportPlanItem);
}
