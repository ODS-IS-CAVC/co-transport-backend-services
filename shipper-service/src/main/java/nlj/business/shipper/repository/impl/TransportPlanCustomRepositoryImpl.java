package nlj.business.shipper.repository.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.repository.TransportPlanCustomRepository;
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
public class TransportPlanCustomRepositoryImpl implements TransportPlanCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public Page<TransportPlan> searchTrspPlanByTrspName(String operatorId, String trspName, List<Integer> statuses,
        Long outerPackageCode, Pageable pageable) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TransportPlan> query = cb.createQuery(TransportPlan.class);
        Root<TransportPlan> transportPlan = query.from(TransportPlan.class);

        Join<TransportPlan, ?> itemsJoin = transportPlan.join("transportPlanItems", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (!BaseUtil.isNull(operatorId)) {
            predicates.add(cb.equal(transportPlan.get("shipperOperator").get("id"), operatorId));
        }

        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(transportPlan.get("status").in(statuses));
        }

        if (outerPackageCode != null) {
            predicates.add(cb.equal(itemsJoin.get("outerPackageCode"), outerPackageCode));
        }

        if (!BaseUtil.isNull(trspName)) {
            predicates.add(cb.like(itemsJoin.get("transportName"), "%" + trspName + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.select(transportPlan).distinct(true);
        query.orderBy(cb.desc(transportPlan.get("createdDate")));

        List<TransportPlan> results = entityManager.createQuery(query)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<TransportPlan> countRoot = countQuery.from(TransportPlan.class);
        Join<TransportPlan, ?> countItemsJoin = countRoot.join("transportPlanItems", JoinType.LEFT);

        List<Predicate> countPredicates = new ArrayList<>();

        if (!BaseUtil.isNull(operatorId)) {
            countPredicates.add(cb.equal(countRoot.get("shipperOperator").get("id"), operatorId));
        }

        if (statuses != null && !statuses.isEmpty()) {
            countPredicates.add(countRoot.get("status").in(statuses));
        }

        if (outerPackageCode != null) {
            countPredicates.add(cb.equal(countItemsJoin.get("outerPackageCode"), outerPackageCode));
        }

        if (!BaseUtil.isNull(trspName)) {
            countPredicates.add(cb.like(countItemsJoin.get("transportName"), "%" + trspName + "%"));
        }

        countQuery.select(cb.countDistinct(countRoot))
            .where(cb.and(countPredicates.toArray(new Predicate[0])));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}
