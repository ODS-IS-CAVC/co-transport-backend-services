package jp.co.nlj.ix.repository.impl;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.ReqShipFromPrty;
import jp.co.nlj.ix.domain.ReqShipToPrty;
import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.repository.ReqTrspPlanLineItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class ReqTrspPlanLineItemCustomRepositoryImpl implements ReqTrspPlanLineItemCustomRepository {

    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画明細項目を検索する。
     *
     * @param request 検索リクエスト
     * @return 検索結果のリスト
     */
    @Override
    public List<ReqTrspPlanLineItem> searchTransportPlanItem(ReqTrspPlanLineItemSearchRequest request) {
        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReqTrspPlanLineItem> query = cb.createQuery(ReqTrspPlanLineItem.class);

        // Define root for the main table
        Root<ReqTrspPlanLineItem> root = query.from(ReqTrspPlanLineItem.class);

        // Use joins to link related tables
        Join<ReqTrspPlanLineItem, ReqShipFromPrty> fromPrtyJoin = root.join("reqShipFromPrty", JoinType.LEFT);
        Join<ReqTrspPlanLineItem, ReqShipToPrty> toPrtyJoin = root.join("reqShipToPrty", JoinType.LEFT);

        // List to store predicates
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(fromPrtyJoin.get("reqTrspPlanLineItem").get("id"), root.get("id")));
        predicates.add(cb.equal(toPrtyJoin.get("reqTrspPlanLineItem").get("id"), root.get("id")));
        // Add predicates for request filters
        if (!BaseUtil.isNull(request.getFromPlcCdPrtyId())) {
            predicates.add(cb.like(cb.lower(fromPrtyJoin.get("fromPlcCdPrtyId")),
                "%" + request.getFromPlcCdPrtyId().toLowerCase() + "%"));
        }

        if (!BaseUtil.isNull(request.getToPlcCdPrtyId())) {
            predicates.add(cb.like(cb.lower(toPrtyJoin.get("toPlcCdPrtyId")),
                "%" + request.getToPlcCdPrtyId().toLowerCase() + "%"));
        }

        if (request.getIstdTotlPcksQuan() != null) {
            predicates.add(cb.equal(root.get("istdTotlPcksQuan"), request.getIstdTotlPcksQuan()));
        }

        if (!BaseUtil.isNull(request.getDsedCllFromDate())) {
            LocalDate fromDate = LocalDate.parse(request.getDsedCllFromDate(), inputDateFormatter);
            predicates.add(cb.equal(root.get("dsedCllFromDate"), fromDate));
        }

        if (!BaseUtil.isNull(request.getDsedCllToDate())) {
            LocalDate toDate = LocalDate.parse(request.getDsedCllToDate(), inputDateFormatter);
            predicates.add(cb.equal(root.get("dsedCllToDate"), toDate));
        }
        // Apply predicates to query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute query
        return entityManager.createQuery(query).getResultList();
    }
}
