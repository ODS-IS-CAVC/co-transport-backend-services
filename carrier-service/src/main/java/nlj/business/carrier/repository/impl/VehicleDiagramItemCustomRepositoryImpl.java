package nlj.business.carrier.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.repository.VehicleDiagramItemCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramItemCustomRepositoryImpl implements VehicleDiagramItemCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<VehicleDiagramItem> findListByDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(14);
        CriteriaQuery<VehicleDiagramItem> query = cb.createQuery(VehicleDiagramItem.class);
        Root<VehicleDiagramItem> varRoot = query.from(VehicleDiagramItem.class);
        query.select(varRoot);
        query.where(cb.and(
            cb.between(varRoot.get("day"), startDate, endDate)
        ));
        List<VehicleDiagramItem> results = entityManager.createQuery(query).getResultList();
        return results.isEmpty() ? null : results;

    }

    @Override
    public Page<VehicleDiagramItem> searchAllItemByDiagramId(Long headId, String operatorId, LocalDate minStartDate,
        LocalDate maxStartDate, String tripName, List<Integer> statusList, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<VehicleDiagramItem> query = cb.createQuery(VehicleDiagramItem.class);
        Root<VehicleDiagramItem> root = query.from(VehicleDiagramItem.class);

        List<Predicate> predicates = buildPredicates(cb, root, headId, operatorId, tripName, minStartDate, maxStartDate,
            statusList);
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream()
                .map(order -> order.isAscending()
                    ? cb.asc(root.get(order.getProperty()))
                    : cb.desc(root.get(order.getProperty())))
                .toList();
            query.orderBy(orders);
        }

        TypedQuery<VehicleDiagramItem> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<VehicleDiagramItem> items = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<VehicleDiagramItem> countRoot = countQuery.from(VehicleDiagramItem.class);

        List<Predicate> countPredicates = buildPredicates(cb, countRoot, headId, operatorId, tripName, minStartDate,
            maxStartDate, statusList);
        countQuery.select(cb.count(countRoot)).where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(items, pageable, totalCount);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<VehicleDiagramItem> root,
        Long headId, String operatorId, String tripName,
        LocalDate startDate, LocalDate endDate, List<Integer> statusList) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("vehicleDiagram").get("id"), headId));
        predicates.add(cb.equal(root.get("operatorId"), operatorId));

        if (!BaseUtil.isNull(tripName)) {
            predicates.add(cb.like(cb.lower(root.get("tripName")), "%" + tripName.toLowerCase() + "%"));
        }

        if (startDate != null && endDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("day"), startDate));
            predicates.add(cb.lessThanOrEqualTo(root.get("day"), endDate));
        }

//        if (statusList != null && !statusList.isEmpty()) {
//            predicates.add(root.get("status").in(statusList));
//        }

        return predicates;
    }

    @Override
    public List<VehicleDiagramItem> findAllVehicleDiagramItemByStatus(Integer status) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramItem> query = cb.createQuery(VehicleDiagramItem.class);
        Root<VehicleDiagramItem> root = query.from(VehicleDiagramItem.class);

        ZoneId japanZone = ZoneId.of("Asia/Tokyo");
        LocalDateTime japanDateTime = LocalDateTime.now(japanZone);
        LocalTime currentTime = japanDateTime.toLocalTime();
        LocalDate currentDay = japanDateTime.toLocalDate();
        List<Predicate> predicates = new ArrayList<>();

        if (status == 0) { // Start condition
            predicates.add(cb.or(
                // Case 1: Current day and time >= departure_time
                cb.and(
                    cb.equal(root.get("day"), currentDay),
                    cb.lessThanOrEqualTo(root.get("departureTime"), currentTime)
                ),
                // Case 2: Past days
                cb.lessThan(root.get("day"), currentDay)
            ));

            // Status is 0 or 1 or null
            predicates.add(cb.or(
                cb.equal(root.get("status"), ParamConstant.VehicleDiagramItemStatus.STATUS_0),
                cb.equal(root.get("status"), ParamConstant.VehicleDiagramItemStatus.STATUS_1),
                cb.isNull(root.get("status"))
            ));
        } else if (status == 1) { // End condition
            predicates.add(cb.or(
                // Case 1: Current day and time >= arrival_time
                cb.and(
                    cb.equal(root.get("day"), currentDay),
                    cb.lessThanOrEqualTo(root.get("arrivalTime"), currentTime)
                ),
                // Case 2: Past days
                cb.lessThan(root.get("day"), currentDay)
            ));

            // Status is 2
            predicates.add(cb.equal(root.get("status"), ParamConstant.VehicleDiagramItemStatus.STATUS_2));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<VehicleDiagramItem> results = entityManager.createQuery(query).getResultList();
        return results.isEmpty() ? new ArrayList<>() : results;
    }
}
