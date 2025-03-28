package jp.co.nlj.ix.repository.impl;

import com.next.logistic.authorization.UserContext;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.repository.CnsLineItemByDateCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class CnsLineItemByDateCustomRepositoryImpl implements CnsLineItemByDateCustomRepository {

    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 指定された輸送計画IDとステータスのリストに基づいてCnsLineItemByDateを検索します。
     *
     * @param transPlanId 輸送計画ID
     * @param statuses    ステータスのリスト
     * @return CnsLineItemByDateのリスト、結果がない場合はnull
     */
    @Override
    public List<CnsLineItemByDate> findByTransPlanId(String transPlanId, List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CnsLineItemByDate> query = cb.createQuery(CnsLineItemByDate.class);
        Root<CnsLineItemByDate> varRoot = query.from(CnsLineItemByDate.class);
        query.select(varRoot);
        query.where(cb.and(
            //cb.or(
            //cb.equal(varRoot.get("reqCnsLineItemId"), transPlanId),
            cb.equal(varRoot.get("transPlanId"), transPlanId),
            // ),
            statuses != null && !statuses.isEmpty() ? cb.in(varRoot.get("status")).value(statuses) : cb.conjunction()
        ));
        List<CnsLineItemByDate> results = entityManager.createQuery(query).getResultList();
        return results.isEmpty() ? null : results;
    }
}
