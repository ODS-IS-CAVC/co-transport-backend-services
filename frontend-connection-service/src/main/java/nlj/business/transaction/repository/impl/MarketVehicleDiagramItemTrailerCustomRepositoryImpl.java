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
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.dto.request.MarketVehicleDiagramItemTrailerSearch;
import nlj.business.transaction.repository.MarketVehicleDiagramItemTrailerCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * マーケット車両図項目トレーラーカスタムリポジトリの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class MarketVehicleDiagramItemTrailerCustomRepositoryImpl implements
    MarketVehicleDiagramItemTrailerCustomRepository {

    private final EntityManager entityManager;

    /**
     * マーケット車両図項目トレーラを検索するクエリを作成する
     *
     * @param searchRequest 輸送能力公開検索
     */
    @Override
    public Page<MarketVehicleDiagramItemTrailer> search(MarketVehicleDiagramItemTrailerSearch searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarketVehicleDiagramItemTrailer> query = cb.createQuery(MarketVehicleDiagramItemTrailer.class);

        Root<MarketVehicleDiagramItemTrailer> root = query.from(MarketVehicleDiagramItemTrailer.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            predicates.add(cb.equal(root.get("departureFrom"), searchRequest.getDepId()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            predicates.add(cb.equal(root.get("arrivalTo"), searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(cb.greaterThanOrEqualTo(root.get("day"),
                LocalDate.parse(searchRequest.getDepDate(), inputDateFormatter)));
        }
        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            predicates.add(
                cb.lessThanOrEqualTo(root.get("day"), LocalDate.parse(searchRequest.getArrDate(), inputDateFormatter)));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Pagination
        int pageNo = Integer.parseInt(searchRequest.getPage()) - 1; // PageRequest is 0-based
        int pageLimit = Integer.parseInt(searchRequest.getLimit());
        TypedQuery<MarketVehicleDiagramItemTrailer> cd = entityManager.createQuery(query);
        cd.setFirstResult(pageNo * pageLimit);
        cd.setMaxResults(pageLimit);

        List<MarketVehicleDiagramItemTrailer> resultPage = cd.getResultList();
        return new PageImpl<>(resultPage, PageRequest.of(pageNo, pageLimit), resultPage.size());
    }
}
