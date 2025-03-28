package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.TrspPlanLineItemCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画のカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TrspPlanLineItemCustomRepositoryImpl implements TrspPlanLineItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送計画の指示IDを取得する。
     *
     * @param cnsLineItemId 運送計画の指示ID
     * @return 運送計画の指示ID
     */
    @Override
    public String findInstructionIdByCnsLineItemId(Long cnsLineItemId) {
        String queryString = "SELECT trsp.trsp_instruction_id FROM trsp_plan_line_item trsp "
            + "LEFT JOIN cns_line_item cns ON cns.trsp_plan_line_item_id = trsp.id "
            + "WHERE cns.id = :cnsLineItemId";
        try {
            Query query = entityManager.createNativeQuery(queryString, String.class)
                .setParameter("cnsLineItemId", cnsLineItemId);
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
