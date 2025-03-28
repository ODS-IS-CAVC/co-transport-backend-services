package nlj.business.shipper.repository.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.repository.TransportPlanItemCustomRepository;
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
public class TransportPlanItemCustomRepositoryImpl implements TransportPlanItemCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public Page<TransportPlanItem> searchTrspPlanItem(String operatorId, String trspName, Pageable pageable) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransportPlanItem> query = cb.createQuery(TransportPlanItem.class);
        Root<TransportPlanItem> transportPlanItem = query.from(TransportPlanItem.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!BaseUtil.isNull(operatorId)) {
            predicates.add(cb.equal(transportPlanItem.get("shipperOperator").get("id"), operatorId));
        }

        if (!BaseUtil.isNull(trspName)) {
            predicates.add(cb.like(transportPlanItem.get("transportName"), "%" + trspName + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<TransportPlanItem> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<TransportPlanItem> results = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<TransportPlanItem> countRoot = countQuery.from(TransportPlanItem.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if (!BaseUtil.isNull(operatorId)) {
            countPredicates.add(cb.equal(countRoot.get("shipperOperator").get("id"), operatorId));
        }

        if (!BaseUtil.isNull(trspName)) {
            countPredicates.add(cb.like(countRoot.get("transportName"), "%" + trspName + "%"));
        }
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}

