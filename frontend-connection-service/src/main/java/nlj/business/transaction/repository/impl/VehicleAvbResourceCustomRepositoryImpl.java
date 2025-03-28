package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.VehicleAvbResourceCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報のカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleAvbResourceCustomRepositoryImpl implements VehicleAvbResourceCustomRepository {

    private final EntityManager entityManager;

    /**
     * 車両情報IDを取得する。
     *
     * @param vehicleAvbResourceId 車両情報ID
     * @return 車両情報ID
     */
    @Override
    public Long getCarInfoId(Long vehicleAvbResourceId) {
        String query = "SELECT car_info_id FROM vehicle_avb_resource WHERE id = ";
        query += vehicleAvbResourceId;
        Query dataQuery = entityManager.createNativeQuery(query);
        return (Long) dataQuery.getSingleResult();
    }
}
