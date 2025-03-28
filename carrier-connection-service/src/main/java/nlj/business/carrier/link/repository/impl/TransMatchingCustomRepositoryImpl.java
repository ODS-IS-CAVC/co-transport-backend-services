package nlj.business.carrier.link.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.repository.TransMatchingCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画マッチングカスタムリポジトリ実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransMatchingCustomRepositoryImpl implements TransMatchingCustomRepository {

    private final EntityManager entityManager;

    /**
     * 出発地マッチング.<BR>
     *
     * @param vehicleAvbResourceItem 車両利用可能リソース項目
     * @param transType              輸送種別
     * @param statuses               ステータス
     * @return 出発地マッチング結果
     */
    @Override
    public List<CnsLineItemByDate> shippers(VehicleAvbResourceItem vehicleAvbResourceItem, Integer transType,
        List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CnsLineItemByDate> query = cb.createQuery(CnsLineItemByDate.class);
        Root<CnsLineItemByDate> root = query.from(CnsLineItemByDate.class);
        List<Predicate> predicates = new ArrayList<>();
        if (vehicleAvbResourceItem.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), vehicleAvbResourceItem.getDepartureFrom()));
        }
        if (vehicleAvbResourceItem.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), vehicleAvbResourceItem.getArrivalTo()));
        }
        if (vehicleAvbResourceItem.getDepartureTimeMin() != null
            && vehicleAvbResourceItem.getDepartureTimeMax() != null) {
            predicates.add(
                cb.lessThanOrEqualTo(root.get("collectionTimeFrom"), vehicleAvbResourceItem.getDepartureTimeMax()));
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("collectionTimeTo"), vehicleAvbResourceItem.getDepartureTimeMin()));
        }
        if (vehicleAvbResourceItem.getPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerUnit"), vehicleAvbResourceItem.getPrice()));
        }
        if (vehicleAvbResourceItem.getDay() != null) {
            predicates.add(cb.equal(root.get("collectionDate"), vehicleAvbResourceItem.getDay()));
        }
        if (transType != null) {
            predicates.add(cb.equal(root.get("transType"), transType));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("pricePerUnit")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 目的地マッチング.<BR>
     *
     * @param cnsLineItemByDate コンティネンツ行項目
     * @param transType         輸送種別
     * @param statuses          ステータス
     * @return 目的地マッチング結果
     */
    @Override
    public List<VehicleAvbResourceItem> carriers(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> root = query.from(VehicleAvbResourceItem.class);
        List<Predicate> predicates = new ArrayList<>();
        if (cnsLineItemByDate.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), cnsLineItemByDate.getDepartureFrom()));
        }
        if (cnsLineItemByDate.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), cnsLineItemByDate.getArrivalTo()));
        }
        if (cnsLineItemByDate.getCollectionTimeFrom() != null && cnsLineItemByDate.getCollectionTimeTo() != null) {
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("departureTimeMax"), cnsLineItemByDate.getCollectionTimeFrom()));
            predicates.add(cb.lessThanOrEqualTo(root.get("departureTimeMin"), cnsLineItemByDate.getCollectionTimeTo()));
        }
        if (cnsLineItemByDate.getPricePerUnit() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), cnsLineItemByDate.getPricePerUnit()));
        }
        if (cnsLineItemByDate.getCollectionDate() != null) {
            predicates.add(cb.equal(root.get("day"), cnsLineItemByDate.getCollectionDate()));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(root.get("price")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 車両情報検索.<BR>
     *
     * @param carInfo 車両情報
     * @return 車両情報
     */
    @Override
    public List<CarInfo> findCarInfoByGiai(CarInfo carInfo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarInfo> query = cb.createQuery(CarInfo.class);
        Root<CarInfo> root = query.from(CarInfo.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("serviceNo"), carInfo.getServiceNo()));
        predicates.add(cb.equal(root.get("serviceStrtDate"), carInfo.getServiceStrtDate()));
        predicates.add(cb.notEqual(root.get("giai"), carInfo.getGiai()));
        predicates.add(cb.notEqual(root.get("tractorIdcr"), 1));
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 車両マッチング.<BR>
     *
     * @param vehicleAvbResourceItem 車両利用可能リソース項目
     * @param transType              輸送種別
     * @param statuses               ステータス
     * @return 車両マッチング結果
     */
    @Override
    public List<CnsLineItemByDate> matchingCarrierPackage(VehicleAvbResourceItem vehicleAvbResourceItem,
        Integer transType, List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CnsLineItemByDate> query = cb.createQuery(CnsLineItemByDate.class);
        Root<CnsLineItemByDate> root = query.from(CnsLineItemByDate.class);
        List<Predicate> predicates = new ArrayList<>();
        if (vehicleAvbResourceItem.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), vehicleAvbResourceItem.getDepartureFrom()));
        }
        if (vehicleAvbResourceItem.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), vehicleAvbResourceItem.getArrivalTo()));
        }
        if (vehicleAvbResourceItem.getDepartureTimeMin() != null
            && vehicleAvbResourceItem.getDepartureTimeMax() != null) {
            predicates.add(
                cb.lessThanOrEqualTo(root.get("collectionTimeFrom"), vehicleAvbResourceItem.getDepartureTimeMax()));
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("collectionTimeTo"), vehicleAvbResourceItem.getDepartureTimeMin()));
        }
        if (vehicleAvbResourceItem.getPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerUnit"), vehicleAvbResourceItem.getPrice()));
        }
        if (vehicleAvbResourceItem.getDay() != null) {
            predicates.add(cb.equal(root.get("collectionDate"), vehicleAvbResourceItem.getDay()));
        }
        if (transType != null) {
            predicates.add(cb.equal(root.get("transType"), transType));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("pricePerUnit")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * トレーラーマッチング.<BR>
     *
     * @param cnsLineItemByDate コンティネンツ行項目
     * @param transType         輸送種別
     * @param statuses          ステータス
     * @return トレーラーマッチング結果
     */
    @Override
    public List<VehicleAvbResourceItem> matchingCarrierTrailer(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> root = query.from(VehicleAvbResourceItem.class);
        List<Predicate> predicates = new ArrayList<>();
        if (cnsLineItemByDate.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), cnsLineItemByDate.getDepartureFrom()));
        }
        if (cnsLineItemByDate.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), cnsLineItemByDate.getArrivalTo()));
        }
        if (cnsLineItemByDate.getCollectionTimeFrom() != null && cnsLineItemByDate.getCollectionTimeTo() != null) {
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("departureTimeMax"), cnsLineItemByDate.getCollectionTimeFrom()));
            predicates.add(cb.lessThanOrEqualTo(root.get("departureTimeMin"), cnsLineItemByDate.getCollectionTimeTo()));
        }
        if (cnsLineItemByDate.getPricePerUnit() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), cnsLineItemByDate.getPricePerUnit()));
        }
        if (cnsLineItemByDate.getCollectionDate() != null) {
            predicates.add(cb.equal(root.get("day"), cnsLineItemByDate.getCollectionDate()));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(root.get("price")));
        return entityManager.createQuery(query).getResultList();
    }
}
