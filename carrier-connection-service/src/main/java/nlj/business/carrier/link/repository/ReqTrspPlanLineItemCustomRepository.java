package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqTrspPlanLineItem;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqTrspPlanLineItemCustomRepository {

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param request 検索条件
     * @return 輸送計画明細項目
     */
    List<ReqTrspPlanLineItem> searchTransportPlanItem(ReqTrspPlanLineItemSearchRequest request);
}
