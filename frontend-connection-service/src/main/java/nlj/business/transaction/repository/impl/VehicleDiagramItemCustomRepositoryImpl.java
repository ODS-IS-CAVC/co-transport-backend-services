package nlj.business.transaction.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.domain.carrier.VehicleDiagram;
import nlj.business.transaction.domain.carrier.VehicleDiagramHead;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import nlj.business.transaction.repository.VehicleDiagramItemCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図形アイテムのカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramItemCustomRepositoryImpl implements VehicleDiagramItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 輸送計画項目を検索するためのクエリを作成する
     *
     * @param searchRequest 交通計画公開検索
     */
    @Override
    public List<VehicleDiagramItem> search(TransportAbilityPublicSearch searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramItem> query = cb.createQuery(VehicleDiagramItem.class);
        Root<VehicleDiagramItem> root = query.from(VehicleDiagramItem.class);
        Join<VehicleDiagramItem, VehicleDiagram> vehicleDiagramJoin = root.join("vehicleDiagram");
        Join<VehicleDiagram, VehicleDiagramHead> vehicleDiagramHeadJoin = vehicleDiagramJoin.join("vehicleDiagramHead");
        List<Predicate> predicates = new ArrayList<>();
        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            predicates.add(cb.equal(vehicleDiagramHeadJoin.get("departureFrom"), searchRequest.getDepId()));
        }
        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            predicates.add(cb.equal(vehicleDiagramHeadJoin.get("arrivalTo"), searchRequest.getArrId()));
        }
        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.greaterThanOrEqualTo(vehicleDiagramHeadJoin.get("startDate"),
                LocalDate.parse(searchRequest.getDepDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.lessThanOrEqualTo(vehicleDiagramHeadJoin.get("endDate"),
                LocalDate.parse(searchRequest.getArrDate(), inputDateFormatter)));
        }
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 運送能力の提案
     */
    @Override
    public VehicleDiagramItem getTransportAbilityProposal() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDiagramItem> query = cb.createQuery(VehicleDiagramItem.class);
        Root<VehicleDiagramItem> root = query.from(VehicleDiagramItem.class);
        Join<VehicleDiagramItem, VehicleDiagram> vehicleDiagramJoin = root.join("vehicleDiagram");
        Join<VehicleDiagram, VehicleDiagramHead> vehicleDiagramHeadJoin = vehicleDiagramJoin.join("vehicleDiagramHead");
        query.select(root)
            .distinct(true)
            .where(cb.equal(root.get("status"), 1))
            .orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(query)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }
}
