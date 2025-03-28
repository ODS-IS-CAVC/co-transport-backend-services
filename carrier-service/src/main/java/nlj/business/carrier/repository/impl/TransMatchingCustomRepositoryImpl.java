package nlj.business.carrier.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.AllArgsConstructor;
import nlj.business.carrier.dto.vehicleDiagram.response.CarrierServiceTransMatchingResponse;
import nlj.business.carrier.repository.TransMatchingCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムアイテムトレーラマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@AllArgsConstructor
public class TransMatchingCustomRepositoryImpl implements TransMatchingCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CarrierServiceTransMatchingResponse> findAllByVehicleDiagramItemTrailerIdIn(List<Long> ids) {
        String sqlQuery =
            " SELECT DISTINCT ON (t_trans_matching.vehicle_avb_resource_id, t_trans_matching.vehicle_diagram_item_trailer_id)  "
                +
                "               COUNT(t_trans_matching.vehicle_avb_resource_id) OVER (PARTITION BY t_trans_matching.vehicle_avb_resource_id) AS total_count, "
                +
                "               t_trans_matching.status AS matching_status,  " +
                "               t_trans_order.status AS order_status," +
                "               t_trans_matching.vehicle_diagram_item_trailer_id  " +
                "           FROM t_trans_matching  " +
                "           LEFT JOIN t_trans_order ON t_trans_matching.id = t_trans_order.trans_matching_id " +
                "           WHERE t_trans_matching.vehicle_diagram_item_trailer_id IN (:ids)  " +
                "           AND t_trans_matching.status = 1  " +
                "           ORDER BY t_trans_matching.vehicle_avb_resource_id," +
                "                    t_trans_matching.vehicle_diagram_item_trailer_id, " +
                "                    t_trans_matching.created_date DESC ";

        Query query = entityManager.createNativeQuery(sqlQuery, CarrierServiceTransMatchingResponse.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
