package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.CarInfoCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class CarInfoCustomRepositoryImpl implements CarInfoCustomRepository {

    private final EntityManager entityManager;

    /**
     * 車両情報IDから車両登録番号を取得する。
     *
     * @param carInfoId 車両情報ID
     * @return 車両登録番号
     */
    @Override
    public String getCarPlateNumber(Long carInfoId) {
        String query = "SELECT car_license_plt_num_id FROM car_info WHERE id = ";
        query += carInfoId;
        Query dataQuery = entityManager.createNativeQuery(query);
        return (String) dataQuery.getSingleResult();
    }
}
