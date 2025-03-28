package nlj.business.carrier.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramMobilityHubCustomRepositoryImpl implements VehicleDiagramMobilityHubCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<VehicleDiagramMobilityHub> findByVehicleDiagramItemId(Long vehicleDiagramItemId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramMobilityHub> query = cb.createQuery(VehicleDiagramMobilityHub.class);
        Root<VehicleDiagramMobilityHub> varRoot = query.from(VehicleDiagramMobilityHub.class);
        query.select(varRoot);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(varRoot.get("vehicleDiagramItemId"), vehicleDiagramItemId));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), ParamConstant.MobilityHub.RESERVATION_STATUS_3));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), ParamConstant.MobilityHub.RESERVATION_STATUS_1));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<VehicleDiagramMobilityHub> findByVehicleDiagramItemIds(List<Long> vehicleDiagramItemIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramMobilityHub> query = cb.createQuery(VehicleDiagramMobilityHub.class);
        Root<VehicleDiagramMobilityHub> varRoot = query.from(VehicleDiagramMobilityHub.class);
        query.select(varRoot);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(varRoot.get("vehicleDiagramItemId").in(vehicleDiagramItemIds));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), ParamConstant.MobilityHub.RESERVATION_STATUS_3));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), ParamConstant.MobilityHub.RESERVATION_STATUS_1));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

}
