package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.domain.carrier.VehicleDiagram;
import nlj.business.transaction.domain.carrier.VehicleDiagramAllocation;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import nlj.business.transaction.domain.carrier.VehicleInfo;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.repository.VehicleDiagramItemTrailerCustomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両図形アイテムトレイラのカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramItemTrailerCustomRepositoryImpl implements VehicleDiagramItemTrailerCustomRepository {

    private final EntityManager entityManager;

    /**
     * マッチング検索を行う。
     *
     * @param transportPlanItem 運送計画アイテム
     * @return マッチング検索結果
     */
    @Override
    @Transactional
    public List<VehicleDiagramItemTrailer> getListMatching(TransportPlanItem transportPlanItem) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramItemTrailer> query = cb.createQuery(VehicleDiagramItemTrailer.class);
        Root<VehicleDiagramItemTrailer> root = query.from(VehicleDiagramItemTrailer.class);

        Join<VehicleDiagramItemTrailer, VehicleDiagram> joinVehicleDiagram = root.join("vehicleDiagram", JoinType.LEFT);
        Join<VehicleDiagramItemTrailer, VehicleDiagramItem> joinVehicleDiagramItem = root.join("vehicleDiagramItem",
            JoinType.LEFT);
        Join<VehicleDiagramItemTrailer, VehicleDiagramAllocation> joinVehicleDiagramAllocation = root.join(
            "vehicleDiagramAllocation", JoinType.LEFT);
        Join<VehicleDiagramAllocation, VehicleInfo> joinVehicleInfo = joinVehicleDiagramAllocation.join("vehicleInfo",
            JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(
            cb.equal(joinVehicleDiagram.get("departureFrom"), transportPlanItem.getDepartureFrom())
        );
        predicates.add(
            cb.equal(joinVehicleDiagram.get("arrivalTo"), transportPlanItem.getArrivalTo())
        );
        predicates.add(
            cb.equal(joinVehicleInfo.get("maxPayload"), transportPlanItem.getWeight())
        );
        predicates.add(
            cb.equal(joinVehicleInfo.get("totalLength"), transportPlanItem.getTotalLength())
        );
        predicates.add(
            cb.equal(joinVehicleInfo.get("totalWidth"), transportPlanItem.getTotalWidth())
        );
        predicates.add(
            cb.equal(joinVehicleInfo.get("totalHeight"), transportPlanItem.getTotalHeight())
        );
        predicates.add(
            cb.greaterThanOrEqualTo(root.get("departureTime"), transportPlanItem.getCollectionTimeFrom())
        );
        predicates.add(
            cb.lessThanOrEqualTo(root.get("departureTime"), transportPlanItem.getCollectionTimeTo())
        );
        predicates.add(
            cb.lessThanOrEqualTo(joinVehicleDiagramItem.get("price"), transportPlanItem.getPricePerUnit())
        );
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 車両図形アイテムトレイラIDを取得する。
     *
     * @param vehicleDiagramId 車両図形ID
     * @return 車両図形アイテムトレイラIDのリスト
     */
    @Override
    public List<Long> getVehicleDiagramItemTrailerIdsByVehicleDiagramId(Long vehicleDiagramId) {
        String query = "SELECT id FROM c_vehicle_diagram_item_trailer WHERE vehicle_diagram_item_id = :vehicleDiagramId";
        Query dataQuery = entityManager.createNativeQuery(query);
        dataQuery.setParameter("vehicleDiagramId", vehicleDiagramId);
        return dataQuery.getResultList();
    }
}
