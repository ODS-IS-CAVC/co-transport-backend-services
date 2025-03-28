package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqCnsLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 貨物明細リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqCnsLineItemRepository extends JpaRepository<ReqCnsLineItem, Long> {

    /**
     * 指定されたIDのリストに基づいて、すべてのReqCnsLineItemを削除します。
     *
     * @param id 削除するReqCnsLineItemのIDのリスト
     */
    void deleteAllByReqTrspPlanLineItem_IdIn(List<Long> id);

    /**
     * 指定されたIDのリストに基づいて、すべてのReqCnsLineItemを取得します。
     *
     * @param ids 取得するReqCnsLineItemのIDのリスト
     * @return 指定されたIDに対応するReqCnsLineItemのリスト
     */
    List<ReqCnsLineItem> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);
}
