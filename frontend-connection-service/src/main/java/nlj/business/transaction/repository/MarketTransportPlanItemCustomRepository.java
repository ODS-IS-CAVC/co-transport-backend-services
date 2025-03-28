package nlj.business.transaction.repository;

import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.request.MarketTransportPlanItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * マーケット輸送計画項目カスタムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MarketTransportPlanItemCustomRepository {

    Page<MarketTransportPlanItem> search(MarketTransportPlanItemSearch searchRequest);
}
