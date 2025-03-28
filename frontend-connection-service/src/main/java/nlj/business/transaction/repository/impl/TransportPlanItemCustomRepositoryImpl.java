package nlj.business.transaction.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.domain.shipper.TransportPlan;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import nlj.business.transaction.repository.TransportPlanItemCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画項目カスタムリポジトリの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransportPlanItemCustomRepositoryImpl implements TransportPlanItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 輸送計画項目を検索するためのクエリを作成する
     *
     * @param searchRequest 交通計画公開検索
     */
    @Override
    @Transactional
    public Page<TransportPlanItem> search(TransportPlanPublicSearch searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransportPlanItem> query = cb.createQuery(TransportPlanItem.class);
        Root<TransportPlanItem> root = query.from(TransportPlanItem.class);

        // Perform join without fetch to avoid in-memory pagination issues
        Join<TransportPlanItem, TransportPlan> transportPlanJoin = root.join("transportPlan", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            predicates.add(cb.equal(transportPlanJoin.get("departureFrom"), searchRequest.getDepId()));
        }
        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            predicates.add(cb.equal(transportPlanJoin.get("arrivalTo"), searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.greaterThanOrEqualTo(transportPlanJoin.get("collectionDate"),
                LocalDate.parse(searchRequest.getDepDate(), inputDateFormatter)));
        }

        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.lessThanOrEqualTo(transportPlanJoin.get("collectionDate"),
                LocalDate.parse(searchRequest.getArrDate(), inputDateFormatter)));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        // Pagination
        int pageNo = Integer.parseInt(searchRequest.getPage()) - 1; // PageRequest is 0-based
        int pageLimit = Integer.parseInt(searchRequest.getLimit());
        TypedQuery<TransportPlanItem> cd = entityManager.createQuery(query);
        cd.setFirstResult(pageNo * pageLimit);
        cd.setMaxResults(pageLimit);

        List<TransportPlanItem> resultPage = cd.getResultList();
        return new PageImpl<>(resultPage, PageRequest.of(pageNo, pageLimit), resultPage.size());
    }

    /**
     * 提案計画
     */
    @Override
    public TransportPlanItem getTransportPlanItemProposal() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransportPlanItem> query = cb.createQuery(TransportPlanItem.class);
        Root<TransportPlanItem> root = query.from(TransportPlanItem.class);
        root.fetch("transportPlan", JoinType.LEFT);
        root.fetch("transportPlanTrailers", JoinType.LEFT);

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

    /**
     * マッチング検索
     *
     * @param departureFrom 出発地
     * @param arrivalTo     目的地
     * @param maxPayload    最大積載量
     * @param totalLength   総長
     * @param totalWidth    総幅
     * @param totalHeight   総高
     * @param departureTime 出発時間
     * @param price         価格
     * @return マッチング検索結果
     */
    @Override
    public List<TransportPlanItem> searchMatching(Long departureFrom, Long arrivalTo, BigDecimal maxPayload,
        BigDecimal totalLength, BigDecimal totalWidth, BigDecimal totalHeight, LocalTime departureTime,
        BigDecimal price) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TransportPlanItem> cq = cb.createQuery(TransportPlanItem.class);

        Root<TransportPlanItem> root = cq.from(TransportPlanItem.class);

        List<Predicate> predicates = new ArrayList<>();

        if (departureFrom != null) {
            predicates.add(cb.equal(root.get("departureFrom"), departureFrom));
        }
        if (arrivalTo != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), arrivalTo));
        }
        if (maxPayload != null) {
            predicates.add(cb.equal(root.get("weight"), maxPayload));
        }
        if (totalLength != null) {
            predicates.add(cb.equal(root.get("totalLength"), totalLength));
        }
        if (totalWidth != null) {
            predicates.add(cb.equal(root.get("totalWidth"), totalWidth));
        }
        if (totalHeight != null) {
            predicates.add(cb.equal(root.get("totalHeight"), totalHeight));
        }
        if (departureTime != null) {
            predicates.add(cb.and(
                cb.lessThanOrEqualTo(root.get("collectionTimeFrom"), departureTime),
                cb.greaterThanOrEqualTo(root.get("collectionTimeTo"), departureTime)
            ));
        }
        if (price != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerUnit"), price));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<TransportPlanItem> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
