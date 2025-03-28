package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.LocationMasterCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 場所マスタカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class LocationMasterCustomRepositoryImpl implements LocationMasterCustomRepository {

    private final EntityManager entityManager;

    /**
     * 場所コードから場所名を取得する。
     *
     * @param locationCode 場所コード
     * @return 場所名
     */
    @Override
    public String getLocationNameByCode(String locationCode) {
        String query = "select name from location_master where code = '" + locationCode + "'";
        Query dataQuery = entityManager.createNativeQuery(query);

        List<String> results = dataQuery.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
