package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqCnsLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 貨物明細リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqCnsLineItemRepository extends JpaRepository<ReqCnsLineItem, Long> {

    /**
     * リクエスト輸送計画明細IDのリストで検索.<BR>
     *
     * @param id リクエスト輸送計画明細IDのリスト
     */
    void deleteAllByReqTrspPlanLineItem_IdIn(List<Long> id);

    /**
     * リクエスト輸送計画明細IDのリストで検索.<BR>
     *
     * @param ids リクエスト輸送計画明細IDのリスト
     * @return 貨物明細
     */
    List<ReqCnsLineItem> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);
}
