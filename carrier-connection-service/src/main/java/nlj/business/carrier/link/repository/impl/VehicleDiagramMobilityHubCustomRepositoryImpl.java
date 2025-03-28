package nlj.business.carrier.link.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.ParamConstant.Status;
import nlj.business.carrier.link.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.link.repository.VehicleDiagramMobilityHubCustomRepository;
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

    /**
     * 車両ダイアグラムアイテムIDとタイプで検索.<BR>
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @param type                 タイプ
     * @return 車両ダイアグラムモビリティハブ
     */
    @Override
    public VehicleDiagramMobilityHub findByVehicleDiagramItemIdAndType(Long vehicleDiagramItemId, int type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramMobilityHub> query = cb.createQuery(VehicleDiagramMobilityHub.class);
        Root<VehicleDiagramMobilityHub> varRoot = query.from(VehicleDiagramMobilityHub.class);
        query.select(varRoot);
        query.where(cb.and(
            cb.equal(varRoot.get("vehicleDiagramItemId"), vehicleDiagramItemId),
            cb.equal(varRoot.get("type"), type)
        ));
        List<VehicleDiagramMobilityHub> results = entityManager.createQuery(query).getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 車両ダイアグラムアイテムIDで検索.<BR>
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @return 車両ダイアグラムモビリティハブ
     */
    @Override
    public List<VehicleDiagramMobilityHub> findByVehicleDiagramItemId(Long vehicleDiagramItemId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramMobilityHub> query = cb.createQuery(VehicleDiagramMobilityHub.class);
        Root<VehicleDiagramMobilityHub> varRoot = query.from(VehicleDiagramMobilityHub.class);
        query.select(varRoot);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(varRoot.get("vehicleDiagramItemId"), vehicleDiagramItemId));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), Status.RESERVATION_STATUS_3));
        predicates.add(cb.notEqual(varRoot.get("reservationStatus"), Status.RESERVATION_STATUS_1));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

}

