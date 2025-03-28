package jp.co.nlj.ix.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.util.List;
import jp.co.nlj.ix.domain.CarInfo;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.repository.VehicleAvailabilityResourceCustomRepository;
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
public class VehicleAvailabilityResourceImplRepository implements VehicleAvailabilityResourceCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送能力管理IDで車両稼働リソースを検索する
     *
     * @param trspOpDate            運送能力管理ID
     * @param trspOpDateTrmStrtDate 運送能力管理日付
     * @param serviceNo             車両サービス番号
     * @return 車両稼働リソースリスト
     */
    @Override
    public List<VehicleAvailabilityResource> findByTrspOpDate(Long trspOpDate, LocalDate trspOpDateTrmStrtDate,
        String serviceNo, String operatorId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvailabilityResource> query = cb.createQuery(VehicleAvailabilityResource.class);
        Root<VehicleAvailabilityResource> varRoot = query.from(VehicleAvailabilityResource.class);

        Join<Object, Object> carInfoJoin = varRoot.join("carInfo");

        Join<Object, Object> transportAbilityLineItemJoin = carInfoJoin.join("transportAbilityLineItem");

        query.select(varRoot)
            .where(cb.equal(transportAbilityLineItemJoin.get("id"), trspOpDate),
                cb.equal(varRoot.get("trspOpDateTrmStrtDate"), trspOpDateTrmStrtDate),
                !BaseUtil.isNull(serviceNo) ? cb.equal(carInfoJoin.get("serviceNo"), serviceNo) : cb.conjunction(),
                cb.equal(varRoot.get("operatorId"), operatorId));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 輸送計画明細項目を検索する。
     *
     * @param operatorId オペレーターコード
     * @param serviceNo  車両サービス番号
     * @param startDate  輸送開始日
     * @return 輸送計画明細項目のリスト
     */
    @Override
    public List<VehicleAvailabilityResource> searchByTrspPlan(String operatorId, String serviceNo,
        LocalDate startDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvailabilityResource> cq = cb.createQuery(VehicleAvailabilityResource.class);

        Root<VehicleAvailabilityResource> vehicleRoot = cq.from(VehicleAvailabilityResource.class);
        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<CarInfo> carInfoRoot = subquery.from(CarInfo.class);

        subquery.select(carInfoRoot.get("id"))
            .where(
                cb.equal(carInfoRoot.get("operatorId"), operatorId),
                cb.equal(carInfoRoot.get("serviceNo"), serviceNo),
                cb.equal(carInfoRoot.get("serviceStrtDate"), startDate)
            );

        cq.where(vehicleRoot.get("carInfo").get("id").in(subquery));
        return entityManager.createQuery(cq).getResultList();
    }
}
