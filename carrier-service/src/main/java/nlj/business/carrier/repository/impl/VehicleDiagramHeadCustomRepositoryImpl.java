package nlj.business.carrier.repository.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.domain.VehicleDiagramHead;
import nlj.business.carrier.repository.VehicleDiagramHeadCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムヘッダカスタムリポジトリ実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramHeadCustomRepositoryImpl implements VehicleDiagramHeadCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public List<VehicleDiagramHead> searchByDate(LocalDate startDate, LocalDate endDate) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new ArrayList<>();
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramHead> query = cb.createQuery(VehicleDiagramHead.class);
        Root<VehicleDiagramHead> root = query.from(VehicleDiagramHead.class);
        List<Predicate> predicates = new ArrayList<>();
        if (startDate != null && endDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
        } else {
            return new ArrayList<>();
        }
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
