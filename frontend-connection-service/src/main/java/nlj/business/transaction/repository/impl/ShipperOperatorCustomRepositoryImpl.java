package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.ShipperOperatorCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送業者オペレーターカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class ShipperOperatorCustomRepositoryImpl implements ShipperOperatorCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送業者オペレーターのメールアドレスを取得する。
     *
     * @param operatorIds 運送業者オペレーターIDリスト
     * @return 運送業者オペレーターのメールアドレスリスト
     */
    @Override
    public List<String> getShipperOperatorMails(List<String> operatorIds) {
        String query = "SELECT email FROM s_shipper_operator WHERE id in (:operatorIds)";
        Query dataQuery = entityManager.createNativeQuery(query);
        dataQuery.setParameter("operatorIds",
            operatorIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        return dataQuery.getResultList();
    }

    /**
     * 運送業者オペレーターのメールアドレスを取得する。
     *
     * @param operatorId 運送業者オペレーターID
     * @return 運送業者オペレーターのメールアドレス
     */
    @Override
    public String getShipperOperatorMail(String operatorId) {
        String query = "SELECT email FROM s_shipper_operator WHERE id = (:operatorId)";
        Query dataQuery = entityManager.createNativeQuery(query);
        dataQuery.setParameter("operatorId", operatorId);
        return dataQuery.getSingleResult().toString();
    }
}
