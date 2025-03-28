package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.TrspPlanLineItem;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanLineItemCustomRepository {

    /**
     * 輸送計画明細項目を検索する。
     *
     * @param request 輸送計画明細項目検索条件
     * @return 輸送計画明細項目のリスト
     */
    List<TrspPlanLineItem> searchTransportPlanItem(TrspPlanLineItemSearchRequest request);
}
