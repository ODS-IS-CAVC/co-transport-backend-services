package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanLineItemCustomRepository {

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param request 輸送計画明細項目検索
     * @return 輸送計画明細項目
     */
    List<TrspPlanLineItem> searchTransportPlanItem(TrspPlanLineItemSearchRequest request);

}
