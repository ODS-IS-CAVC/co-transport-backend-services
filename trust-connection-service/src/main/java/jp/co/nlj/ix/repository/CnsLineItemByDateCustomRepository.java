package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運行計画明細リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CnsLineItemByDateCustomRepository {

    /**
     * 指定された運行計画IDとステータスのリストに基づいて、運行計画明細を検索する。
     *
     * @param transPlanId 運行計画ID
     * @param statuses    ステータスのリスト
     * @return 指定された条件に一致する運行計画明細のリスト
     */
    List<CnsLineItemByDate> findByTransPlanId(String transPlanId, List<Integer> statuses);

}
