package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.CnsLineItem;
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
public interface CnsLineItemRepository extends JpaRepository<CnsLineItem, Long> {

    /**
     * 指定されたトランスポートプランラインアイテムのIDに基づいて、すべての貨物明細を削除します。
     *
     * @param ids 削除するトランスポートプランラインアイテムのIDリスト
     */
    void deleteAllByTrspPlanLineItem_IdIn(List<Long> ids);

    /**
     * 指定されたトランスポートプランラインアイテムのIDに基づいて、すべての貨物明細を取得します。
     *
     * @param ids 取得するトランスポートプランラインアイテムのIDリスト
     * @return 指定されたIDに関連する貨物明細のリスト
     */
    List<CnsLineItem> findAllByTrspPlanLineItem_IdIn(List<Long> ids);

}
