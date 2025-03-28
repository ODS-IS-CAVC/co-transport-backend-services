package nlj.business.carrier.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.domain.TransOrder;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.repository.TransOrderCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送注文カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransOrderCustomRepositoryImpl implements TransOrderCustomRepository {

    private static final Logger log = LoggerFactory.getLogger(TransOrderCustomRepositoryImpl.class);
    private final EntityManager entityManager;

    @Override
    public List<String> findOperatorIdByVehicleDiagramItemId(Long vehicleDiagramItemId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        // From t_trans_order
        Root<TransOrder> transOrder = query.from(TransOrder.class);

        // Subquery for c_vehicle_diagram_item_trailer
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<VehicleDiagramItemTrailer> trailer = subquery.from(VehicleDiagramItemTrailer.class);
        subquery.select(trailer.get("id"))
            .where(cb.equal(trailer.get("vehicleDiagramItem").get("id"), vehicleDiagramItemId));

        // Main query - select multiple operator IDs using multiselect
        query.multiselect(
                transOrder.get("shipperOperatorId"),
                transOrder.get("carrierOperatorId"),
                transOrder.get("carrier2OperatorId")
            )
            .where(cb.in(transOrder.get("vehicleDiagramItemTrailerId")).value(subquery))
            .distinct(true);

        try {
            List<Object[]> results = entityManager.createQuery(query).getResultList();
            if (results.isEmpty()) {
                return Arrays.asList(null, null, null);
            }

            Object[] firstRow = results.get(0);
            return Arrays.asList(
                firstRow[0] != null ? firstRow[0].toString() : null,
                firstRow[1] != null ? firstRow[1].toString() : null,
                firstRow[2] != null ? firstRow[2].toString() : null
            );
        } catch (Exception e) {
            log.error("Error when getting operator ids for vehicle_diagram_item_id: {}", vehicleDiagramItemId, e);
            return Arrays.asList(null, null, null);
        }
    }

}
