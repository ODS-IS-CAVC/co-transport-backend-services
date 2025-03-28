package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.ReqTrspPlanLineItemCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画明細カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class ReqTrspPlanLineItemCustomRepositoryImpl implements ReqTrspPlanLineItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送計画明細IDから運送指示IDを取得する。
     *
     * @param reqCnsLineItemId 運送計画明細ID
     * @return 運送指示ID
     */
    @Override
    public String findInstructionIdByReqCnsLineItemId(Long reqCnsLineItemId) {
        String queryString = "SELECT trsp.trsp_instruction_id FROM req_trsp_plan_line_item trsp "
            + "LEFT JOIN req_cns_line_item cns ON trsp.id = cns.req_trsp_plan_line_item_id "
            + "WHERE cns.id = :reqCnsLineItemId";
        try {
            Query query = entityManager.createNativeQuery(queryString, String.class)
                .setParameter("reqCnsLineItemId", reqCnsLineItemId);
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
