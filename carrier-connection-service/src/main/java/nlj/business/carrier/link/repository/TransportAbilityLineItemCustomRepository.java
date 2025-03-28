package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportAbilityLineItemCustomRepository {

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param operatorId シッパーコーポレートコード
     * @param request    輸送計画明細項目検索
     * @return 輸送計画明細項目
     */
    List<TransportAbilityLineItem> searchTransportPlanItem(String operatorId,
        ShipperTransportCapacitySearchDTO request);
}
