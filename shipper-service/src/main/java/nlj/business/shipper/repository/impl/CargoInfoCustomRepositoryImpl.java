package nlj.business.shipper.repository.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.domain.CargoInfo;
import nlj.business.shipper.repository.CargoInfoCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷物情報カスタムリポジトリ実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class CargoInfoCustomRepositoryImpl implements CargoInfoCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public Page<CargoInfo> search(List<Integer> status, Pageable pageable) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CargoInfo> query = cb.createQuery(CargoInfo.class);
        Root<CargoInfo> root = query.from(CargoInfo.class);
        Predicate userIdPredicate = cb.equal(root.get("operatorId"), user.getCompanyId());
        Predicate statusPredicate = cb.conjunction();
        if (status != null && !status.isEmpty()) {
            statusPredicate = root.get("status").in(status);
        }
        Predicate finalPredicate = cb.and(userIdPredicate, statusPredicate);
        query.where(finalPredicate);
        query.orderBy(cb.desc(root.get("id")));
        List<CargoInfo> resultList = entityManager.createQuery(query)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CargoInfo> countRoot = countQuery.from(CargoInfo.class);
        Predicate countUserIdPredicate = cb.equal(countRoot.get("operatorId"), user.getCompanyId());
        Predicate countStatusPredicate = cb.conjunction();
        if (status != null && !status.isEmpty()) {
            countStatusPredicate = countRoot.get("status").in(status);
        }
        Predicate countPredicate = cb.and(countUserIdPredicate, countStatusPredicate);
        countQuery.select(cb.count(countRoot)).where(countPredicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
}
