package nlj.business.transaction.repository.impl;

import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.request.MarketTransportPlanItemSearch;
import nlj.business.transaction.repository.MarketTransportPlanItemCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 市場輸送計画アイテムのためのカスタムリポジトリの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class MarketTransportPlanItemCustomRepositoryImpl implements MarketTransportPlanItemCustomRepository {

    private final EntityManager entityManager;

    /**
     * 市場輸送計画アイテムを検索するクエリの作成
     *
     * @param searchRequest マーケット輸送計画項目検索
     */
    @Override
    public Page<MarketTransportPlanItem> search(MarketTransportPlanItemSearch searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarketTransportPlanItem> query = cb.createQuery(MarketTransportPlanItem.class);

        Root<MarketTransportPlanItem> root = query.from(MarketTransportPlanItem.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            predicates.add(cb.equal(root.get("departureFrom"), searchRequest.getDepId()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            predicates.add(cb.equal(root.get("arrivalTo"), searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.greaterThanOrEqualTo(root.get("collectionDate"),
                LocalDate.parse(searchRequest.getDepDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.lessThanOrEqualTo(root.get("collectionDate"),
                LocalDate.parse(searchRequest.getArrDate(), inputDateFormatter)));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Pagination
        int pageNo = Integer.parseInt(searchRequest.getPage()) - 1; // PageRequest is 0-based
        int pageLimit = Integer.parseInt(searchRequest.getLimit());
        TypedQuery<MarketTransportPlanItem> cd = entityManager.createQuery(query);
        cd.setFirstResult(pageNo * pageLimit);
        cd.setMaxResults(pageLimit);

        List<MarketTransportPlanItem> resultPage = cd.getResultList();
        return new PageImpl<>(resultPage, PageRequest.of(pageNo, pageLimit), resultPage.size());
    }
}
