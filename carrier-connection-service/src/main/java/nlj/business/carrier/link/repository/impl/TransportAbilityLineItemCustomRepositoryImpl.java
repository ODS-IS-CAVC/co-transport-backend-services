package nlj.business.carrier.link.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.DataBaseConstant;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.DriverInformation;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import nlj.business.carrier.link.repository.TransportAbilityLineItemCustomRepository;
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
     * 運送能力検索.<BR>
     *
     * @param operatorId 運送業者ID
     * @param params     運送能力検索DTO
     * @return 運送能力検索結果
     */
    @Override
    public List<TransportAbilityLineItem> searchTransportPlanItem(String operatorId,
        ShipperTransportCapacitySearchDTO params) {
        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
        EntityGraph<TransportAbilityLineItem> graph = entityManager.createEntityGraph(TransportAbilityLineItem.class);
        Subgraph<CarInfo> carInfosGraph = graph.addSubgraph("carInfos");
        Subgraph<VehicleAvailabilityResource> vehicleResourceGraph = carInfosGraph.addSubgraph(
            "vehicleAvailabilityResources");
        vehicleResourceGraph.addSubgraph("cutOffInfos");
        vehicleResourceGraph.addSubgraph("freeTimeInfos");
        Subgraph<DriverInformation> driverInfoGraph = graph.addSubgraph("driverInformations");
        driverInfoGraph.addSubgraph("driverAvailabilityTimes");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransportAbilityLineItem> query = cb.createQuery(TransportAbilityLineItem.class);
        Root<TransportAbilityLineItem> root = query.from(TransportAbilityLineItem.class);

        Join<TransportAbilityLineItem, CarInfo> carInfoJoin = root.join("carInfos", JoinType.LEFT);
        Join<CarInfo, VehicleAvailabilityResource> vehicleAvailabilityJoin = carInfoJoin.join(
            "vehicleAvailabilityResources", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("operatorId"), operatorId));

        if (!BaseUtil.isNull(params.getServiceNo())) {
            predicates.add(cb.equal(carInfoJoin.get("serviceNo"), params.getServiceNo()));
        }

        if (!BaseUtil.isNull(params.getServiceName())) {
            predicates.add(cb.equal(carInfoJoin.get("serviceName"), params.getServiceName()));
        }
        if (!BaseUtil.isNull(params.getCarMaxLoadCapacity1Meas())) {
            predicates.add(cb.equal(carInfoJoin.get("carMaxLoadCapacity1Meas"), params.getCarMaxLoadCapacity1Meas()));
        }
        if (!BaseUtil.isNull(params.getTrspOpStrtAreaLineOneTxt())) {
            predicates.add(cb.like(cb.lower(vehicleAvailabilityJoin.get("trspOpStrtAreaLineOneTxt")),
                "%" + params.getTrspOpStrtAreaLineOneTxt().toLowerCase() + "%"));
        }
        if (!BaseUtil.isNull(params.getTrspOpEndAreaLineOneTxt())) {
            predicates.add(cb.like(cb.lower(vehicleAvailabilityJoin.get("trspOpEndAreaLineOneTxt")),
                "%" + params.getTrspOpEndAreaLineOneTxt().toLowerCase() + "%"));
        }
        if (!BaseUtil.isNull(params.getMaxTrspOpDateTrmStrtDate())) {
            predicates.add(cb.lessThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpDateTrmStrtDate"),
                LocalDate.parse(params.getMaxTrspOpDateTrmStrtDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpDateTrmStrtDate())) {
            predicates.add(cb.greaterThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpDateTrmStrtDate"),
                LocalDate.parse(params.getMinTrspOpDateTrmStrtDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(params.getMaxTrspOpDateTrmEndDate())) {
            predicates.add(cb.lessThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpDateTrmEndDate"),
                LocalDate.parse(params.getMaxTrspOpDateTrmEndDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpDateTrmEndDate())) {
            predicates.add(cb.greaterThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpDateTrmEndDate"),
                LocalDate.parse(params.getMinTrspOpDateTrmEndDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(params.getMaxTrspOpPlanDateTrmStrtTime())) {
            predicates.add(cb.lessThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpPlanDateTrmStrtTime"),
                LocalTime.parse(params.getMaxTrspOpPlanDateTrmStrtTime(), inputTimeFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpPlanDateTrmStrtTime())) {
            predicates.add(cb.greaterThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpPlanDateTrmStrtTime"),
                LocalTime.parse(params.getMinTrspOpPlanDateTrmStrtTime(), inputTimeFormatter)));
        }
        if (!BaseUtil.isNull(params.getMaxTrspOpPlanDateTrmEndTime())) {
            predicates.add(cb.lessThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpPlanDateTrmEndTime"),
                LocalTime.parse(params.getMaxTrspOpPlanDateTrmEndTime(), inputTimeFormatter)));
        }
        if (!BaseUtil.isNull(params.getMinTrspOpPlanDateTrmEndTime())) {
            predicates.add(cb.greaterThanOrEqualTo(vehicleAvailabilityJoin.get("trspOpPlanDateTrmEndTime"),
                LocalTime.parse(params.getMinTrspOpPlanDateTrmEndTime(), inputTimeFormatter)));
        }

        query.select(root).distinct(true).where(cb.and(predicates.toArray(new Predicate[0])));
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.loadgraph", graph);
        return entityManager.createQuery(query).setHint("jakarta.persistence.loadgraph", graph).getResultList();
    }
}
