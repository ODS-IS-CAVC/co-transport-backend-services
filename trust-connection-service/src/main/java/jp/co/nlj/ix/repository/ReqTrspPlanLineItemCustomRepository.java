package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqTrspPlanLineItemCustomRepository {

    /**
     * 輸送計画明細項目を検索する。
     *
     * @param request 検索条件
     * @return 検索結果
     */
    List<ReqTrspPlanLineItem> searchTransportPlanItem(ReqTrspPlanLineItemSearchRequest request);
}
