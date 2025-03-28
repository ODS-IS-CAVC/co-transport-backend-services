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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.ParamConstant.CarrierDeleteFlag;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.repository.VehicleInfoCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブカスタムリポジトリ実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleInfoCustomRepositoryImpl implements VehicleInfoCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    public Page<VehicleInfo> search(List<Integer> temperatureRange,
        List<Integer> vehicleType, Pageable pageable) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleInfo> query = cb.createQuery(VehicleInfo.class);
        Root<VehicleInfo> root = query.from(VehicleInfo.class);

        Predicate userIdPredicate = cb.equal(root.get("operatorId"), user.getCompanyId());
        Predicate deletePredicate = cb.or(
            cb.equal(root.get("deleteFlag"), CarrierDeleteFlag.NOT_DELETE),
            cb.isNull(root.get("deleteFlag"))
        );

        Predicate temperaturePredicate = cb.conjunction();
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            temperaturePredicate = temperatureRange.stream()
                .map(temp -> cb.like(root.get("temperatureRange"), "%" + temp + "%"))
                .reduce(cb::or)
                .orElse(cb.conjunction());
        }

        Predicate vehicleTypePredicate = cb.conjunction();
        if (vehicleType != null && !vehicleType.isEmpty()) {
            vehicleTypePredicate = root.get("vehicleType").in(vehicleType);
        }

        Predicate finalPredicate = cb.and(userIdPredicate, temperaturePredicate, vehicleTypePredicate, deletePredicate);
        query.where(finalPredicate);
        query.orderBy(cb.desc(root.get("id")));

        List<VehicleInfo> resultList = entityManager.createQuery(query)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<VehicleInfo> countRoot = countQuery.from(VehicleInfo.class);
        Predicate countUserIdPredicate = cb.equal(countRoot.get("operatorId"), user.getCompanyId());
        Predicate countDeletePredicate = cb.or(
            cb.equal(countRoot.get("deleteFlag"), CarrierDeleteFlag.NOT_DELETE),
            cb.isNull(countRoot.get("deleteFlag"))
        );

        Predicate countTemperaturePredicate = cb.conjunction();
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            countTemperaturePredicate = temperatureRange.stream()
                .map(temp -> cb.like(countRoot.get("temperatureRange"), "%" + temp + "%"))
                .reduce(cb::or)
                .orElse(cb.conjunction());
        }

        Predicate countVehicleTypePredicate = cb.conjunction();
        if (vehicleType != null && !vehicleType.isEmpty()) {
            countVehicleTypePredicate = countRoot.get("vehicleType").in(vehicleType);
        }

        Predicate countPredicate = cb.and(countUserIdPredicate, countTemperaturePredicate, countVehicleTypePredicate,
            countDeletePredicate);
        countQuery.select(cb.count(countRoot)).where(countPredicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);

    }

    @Override
    public String getTruckId(Long vehicleId) {
        VehicleInfo vehicleInfo = entityManager.find(VehicleInfo.class, vehicleId);
        if (vehicleInfo == null) {
            return null;
        } else {
            return vehicleInfo.getRegistrationAreaCode() + " " + vehicleInfo.getRegistrationGroupNumber() + " "
                + vehicleInfo.getRegistrationCharacter() + " " + vehicleInfo.getRegistrationNumber1();
        }
    }


}
