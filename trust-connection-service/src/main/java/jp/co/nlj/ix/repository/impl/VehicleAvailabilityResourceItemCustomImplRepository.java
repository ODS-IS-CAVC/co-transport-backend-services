package jp.co.nlj.ix.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.repository.VehicleAvailabilityResourceItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両稼働リソースリポジトリ実装クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleAvailabilityResourceItemCustomImplRepository implements
    VehicleAvailabilityResourceItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 車両予約リソースIDで車両予約リソース明細を検索する
     *
     * @param vehicleAvbResourceId 車両予約リソースID
     * @return 車両予約リソース明細
     */
    @Override
    public List<VehicleAvbResourceItem> findByVehicleAvbResourceId(Long vehicleAvbResourceId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> varRoot = query.from(VehicleAvbResourceItem.class);
        query.select(varRoot)
            .where(cb.equal(varRoot.get("vehicleAvailabilityResource"), vehicleAvbResourceId));
        List<VehicleAvbResourceItem> results = entityManager.createQuery(query).getResultList();
        return results.isEmpty() ? null : results;
    }

    /**
     * 車両予約リソースID、出発時間、料金、ステータスで車両予約リソース明細を検索する
     *
     * @param vehicleAvbResourceId 車両予約リソースID
     * @param departureTimeMin     出発時間最小
     * @param departureTimeMax     出発時間最大
     * @param price                料金
     * @param statuses             ステータス
     * @return 車両予約リソース明細
     */
    @Override
    public List<VehicleAvbResourceItem> findByVehicleAvbResourceIdAndDepartureTimeAndPriceAndStatus(
        Long vehicleAvbResourceId,
        LocalTime departureTimeMin, LocalTime departureTimeMax, BigDecimal price, List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> varRoot = query.from(VehicleAvbResourceItem.class);
        query.select(varRoot);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(varRoot.get("vehicleAvailabilityResource"), vehicleAvbResourceId));
        if (departureTimeMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(varRoot.get("departureTimeMin"), departureTimeMin));
        }
        if (departureTimeMax != null) {
            predicates.add(cb.lessThanOrEqualTo(varRoot.get("departureTimeMax"), departureTimeMax));
        }
        if (price != null) {
            predicates.add(cb.equal(varRoot.get("price"), price));
        }
        predicates.add(cb.in(varRoot.get("status")).value(statuses));
        query.where(predicates.toArray(new Predicate[0]));
        List<VehicleAvbResourceItem> results = entityManager.createQuery(query).getResultList();

        return results.isEmpty() ? null : results;
    }

}
