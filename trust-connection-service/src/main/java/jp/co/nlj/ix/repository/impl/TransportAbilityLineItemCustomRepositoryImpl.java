package jp.co.nlj.ix.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.CarInfo;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import jp.co.nlj.ix.repository.TransportAbilityLineItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransportAbilityLineItemCustomRepositoryImpl implements TransportAbilityLineItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 輸送計画アイテムを検索する。
     *
     * @param params 検索条件
     * @param page   ページ番号
     * @param limit  ページサイズ
     * @return 検索結果のページ
     */
    @Override
    public Page<VehicleAvbResourceItem> searchTransportPlanItem(ShipperTransportCapacitySearchDTO params, int page,
        int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> root = query.from(VehicleAvbResourceItem.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Long> carInfoSubquery = query.subquery(Long.class);
        Root<CarInfo> carInfoRoot = carInfoSubquery.from(CarInfo.class);
        carInfoSubquery.select(carInfoRoot.get("id"));

        List<Predicate> carInfoPredicates = new ArrayList<>();
        carInfoPredicates.add(cb.equal(carInfoRoot.get("id"), root.get("carInfoId")));

        if (!BaseUtil.isNull(params.getServiceNo())) {
            carInfoPredicates.add(cb.equal(carInfoRoot.get("serviceNo"), params.getServiceNo()));
        }
        if (!BaseUtil.isNull(params.getServiceName())) {
            carInfoPredicates.add(cb.like(carInfoRoot.get("serviceName"), "%" + params.getServiceName() + "%"));
        }
        if (!BaseUtil.isNull(params.getCarMaxLoadCapacity1Meas())) {
            carInfoPredicates.add(
                cb.equal(carInfoRoot.get("carMaxLoadCapacity1Meas"), params.getCarMaxLoadCapacity1Meas()));
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);

        if (!BaseUtil.isNull(params.getMaxTrspOpDateTrmStrtDate()) && !BaseUtil.isNull(
            params.getMinTrspOpDateTrmStrtDate())
            && params.getMaxTrspOpDateTrmStrtDate().equals(params.getMinTrspOpDateTrmStrtDate())) {
            Expression<Long> dateDifference = cb.abs(cb.function(
                "TIMESTAMPDIFF", Long.class,
                cb.literal(ChronoUnit.DAYS),
                carInfoRoot.get("serviceStrtDate"),
                cb.literal(LocalDate.parse(params.getMaxTrspOpDateTrmStrtDate(), dateFormatter))
            ));
            query.orderBy(cb.asc(dateDifference));
        } else {
            if (!BaseUtil.isNull(params.getMaxTrspOpDateTrmStrtDate())) {
                carInfoPredicates.add(cb.lessThanOrEqualTo(carInfoRoot.get("serviceStrtDate"),
                    LocalDate.parse(params.getMaxTrspOpDateTrmStrtDate(), dateFormatter)));
            }
            if (!BaseUtil.isNull(params.getMinTrspOpDateTrmStrtDate())) {
                carInfoPredicates.add(cb.greaterThanOrEqualTo(carInfoRoot.get("serviceStrtDate"),
                    LocalDate.parse(params.getMinTrspOpDateTrmStrtDate(), dateFormatter)));
            }
        }

        if (!BaseUtil.isNull(params.getMaxTrspOpDateTrmEndDate())) {
            carInfoPredicates.add(cb.lessThanOrEqualTo(carInfoRoot.get("serviceEndDate"),
                LocalDate.parse(params.getMaxTrspOpDateTrmEndDate(), dateFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpDateTrmEndDate())) {
            carInfoPredicates.add(cb.greaterThanOrEqualTo(carInfoRoot.get("serviceEndDate"),
                LocalDate.parse(params.getMinTrspOpDateTrmEndDate(), dateFormatter)));
        }

        if (!BaseUtil.isNull(params.getMaxTrspOpPlanDateTrmStrtTime()) && !BaseUtil.isNull(
            params.getMinTrspOpPlanDateTrmStrtTime())
            && params.getMaxTrspOpPlanDateTrmStrtTime().equals(params.getMinTrspOpPlanDateTrmStrtTime())) {
            Expression<Long> dateDifference = cb.abs(cb.function(
                "TIMESTAMPDIFF", Long.class,
                cb.literal(ChronoUnit.MINUTES),
                carInfoRoot.get("serviceStrtTime"),
                cb.literal(LocalTime.parse(params.getMaxTrspOpPlanDateTrmStrtTime(), timeFormatter))
            ));
            query.orderBy(cb.asc(dateDifference));
        } else {
            if (!BaseUtil.isNull(params.getMaxTrspOpPlanDateTrmStrtTime())) {
                carInfoPredicates.add(cb.lessThanOrEqualTo(carInfoRoot.get("serviceStrtTime"),
                    LocalTime.parse(params.getMaxTrspOpPlanDateTrmStrtTime(), timeFormatter)));
            }
            if (!BaseUtil.isNull(params.getMinTrspOpPlanDateTrmStrtTime())) {
                carInfoPredicates.add(cb.greaterThanOrEqualTo(carInfoRoot.get("serviceStrtTime"),
                    LocalTime.parse(params.getMinTrspOpPlanDateTrmStrtTime(), timeFormatter)));
            }
        }

        if (!BaseUtil.isNull(params.getMaxTrspOpPlanDateTrmEndTime())) {
            carInfoPredicates.add(cb.lessThanOrEqualTo(carInfoRoot.get("serviceEndTime"),
                LocalTime.parse(params.getMaxTrspOpPlanDateTrmEndTime(), timeFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpPlanDateTrmEndTime())) {
            carInfoPredicates.add(cb.greaterThanOrEqualTo(carInfoRoot.get("serviceEndTime"),
                LocalTime.parse(params.getMinTrspOpPlanDateTrmEndTime(), timeFormatter)));
        }

        if (!carInfoPredicates.isEmpty()) {
            carInfoSubquery.where(cb.and(carInfoPredicates.toArray(new Predicate[0])));
        }

        Subquery<Long> vehicleAvailabilityResourceSubquery = query.subquery(Long.class);
        Root<VehicleAvailabilityResource> vehicleAvailabilityResourceRoot = vehicleAvailabilityResourceSubquery.from(
            VehicleAvailabilityResource.class);
        vehicleAvailabilityResourceSubquery.select(vehicleAvailabilityResourceRoot.get("id"));

        List<Predicate> vehicleAvailabilityPredicates = new ArrayList<>();
        vehicleAvailabilityPredicates.add(
            cb.equal(vehicleAvailabilityResourceRoot.get("id"), root.get("vehicleAvbResourceId")));

        if (!BaseUtil.isNull(params.getTrspOpStrtAreaLineOneTxt())) {
            vehicleAvailabilityPredicates.add(cb.equal(vehicleAvailabilityResourceRoot.get("trspOpStrtAreaLineOneTxt"),
                params.getTrspOpStrtAreaLineOneTxt().toLowerCase()
            ));
        }
        if (!BaseUtil.isNull(params.getTrspOpEndAreaLineOneTxt())) {
            vehicleAvailabilityPredicates.add(cb.equal(vehicleAvailabilityResourceRoot.get("trspOpEndAreaLineOneTxt"),
                params.getTrspOpEndAreaLineOneTxt().toLowerCase()
            ));
        }

        if (!vehicleAvailabilityPredicates.isEmpty()) {
            vehicleAvailabilityResourceSubquery.where(cb.and(vehicleAvailabilityPredicates.toArray(new Predicate[0])));
        }

        predicates.add(cb.in(root.get("carInfoId")).value(carInfoSubquery));
        predicates.add(cb.in(root.get("vehicleAvbResourceId")).value(vehicleAvailabilityResourceSubquery));

        if (params.getAdvanced() != null) {
            predicates.add(cb.or(
                cb.equal(root.get("status"), DataBaseConstant.VehicleAvbResourceItem.Status.MARKET),
                cb.equal(root.get("status"), DataBaseConstant.VehicleAvbResourceItem.Status.AUTOMATIC_MATCHING)
            ));

            if (params.getAdvanced().getFreightRateMin() != null && params.getAdvanced().getFreightRateMax() != null
                && params.getAdvanced().getFreightRateMin().equals(params.getAdvanced().getFreightRateMax())) {
                Expression<Double> priceDifference = cb.abs(
                    cb.diff(root.get("price"), cb.literal(params.getAdvanced().getFreightRateMin())));
                query.orderBy(cb.asc(priceDifference));
            } else {
                if (params.getAdvanced().getFreightRateMin() != null) {
                    predicates.add(
                        cb.greaterThanOrEqualTo(root.get("price"), params.getAdvanced().getFreightRateMin()));
                }
                if (params.getAdvanced().getFreightRateMax() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("price"), params.getAdvanced().getFreightRateMax()));
                }
            }

            if (params.getAdvanced().getDayWeek() != null && !params.getAdvanced().getDayWeek().isEmpty()) {
                Expression<Integer> isoDayOfWeek = cb.function("DATE_PART", Integer.class, cb.literal("ISODOW"),
                    root.get("day"));
                predicates.add(isoDayOfWeek.in(params.getAdvanced().getDayWeek()));
            }
            if (params.getAdvanced().getCid() != null) {
                predicates.add(cb.equal(root.get("operatorCode"), params.getAdvanced().getCid()));
            }
            if (params.getAdvanced().getKeyword() != null) {
                String keyword = "%" + params.getAdvanced().getKeyword() + "%";
                Predicate vehicleNamePredicate = cb.like(root.get("vehicleName"), keyword);
                Predicate tripNamePredicate = cb.like(root.get("tripName"), keyword);
                predicates.add(cb.or(vehicleNamePredicate, tripNamePredicate));
            }
            if (params.getAdvanced().getTemperatureRange() != null &&
                !params.getAdvanced().getTemperatureRange().isEmpty()) {
                String tempRangeString = params.getAdvanced().getTemperatureRange().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
                predicates.add(cb.like(
                    root.get("temperatureRange"),
                    cb.literal("%" + tempRangeString + "%")
                ));
            }
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.orderBy(cb.desc(root.get("createdDate")));

        TypedQuery<VehicleAvbResourceItem> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page * limit);
        typedQuery.setMaxResults(limit);

        long totalRecords = typedQuery.getResultList().size();
        return new PageImpl<>(typedQuery.getResultList(), PageRequest.of(page, limit), totalRecords);

    }

}
