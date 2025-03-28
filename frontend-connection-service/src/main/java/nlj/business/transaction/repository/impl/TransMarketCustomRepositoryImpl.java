package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import nlj.business.transaction.domain.trans.TransMarket;
import nlj.business.transaction.repository.TransMarketCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランザクションマーケットリポジトリの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@AllArgsConstructor
public class TransMarketCustomRepositoryImpl implements TransMarketCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送計画を取得する。
     *
     * @param month 運送計画の月
     * @return 運送計画リスト
     */
    @Override
    public List<TransMarket> search(String month) {

        int year = Integer.parseInt(month.substring(0, 4));
        int monthValue = Integer.parseInt(month.substring(4, 6));
        YearMonth yearMonth = YearMonth.of(year, monthValue);

        LocalDate firstDayOfMonth = yearMonth.atDay(1);

        LocalDate startOfGrid = firstDayOfMonth.minusDays(firstDayOfMonth.getDayOfWeek().getValue() % 7);

        LocalDate endOfGrid = startOfGrid.plusDays(34);

        YearMonth startMonth = YearMonth.from(startOfGrid);
        YearMonth endMonth = YearMonth.from(endOfGrid);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransMarket> cq = cb.createQuery(TransMarket.class);
        Root<TransMarket> root = cq.from(TransMarket.class);

        List<Predicate> predicates = new ArrayList<>();

        for (YearMonth monthInRange = startMonth;
            !monthInRange.isAfter(endMonth);
            monthInRange = monthInRange.plusMonths(1)) {

            int startDay = monthInRange.equals(startMonth) ? startOfGrid.getDayOfMonth() : 1;
            int endDay = monthInRange.equals(endMonth) ? endOfGrid.getDayOfMonth() : monthInRange.lengthOfMonth();
            addMonthDayPredicate(predicates, cb, root, monthInRange, startDay, endDay);
        }

        cq.select(root).where(cb.or(predicates.toArray(new Predicate[0])));

        cq.orderBy(cb.asc(root.get("month")), cb.asc(root.get("day")));

        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * 運送計画の月と日を条件にして運送計画を取得する。
     *
     * @param predicates 条件リスト
     * @param cb         条件ビルダー
     * @param root       ルート
     * @param month      運送計画の月
     * @param startDay   運送計画の開始日
     * @param endDay     運送計画の終了日
     */
    private void addMonthDayPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<TransMarket> root,
        YearMonth month, int startDay, int endDay) {
        String formattedMonth = month.toString().replace("-", "");
        Predicate monthPredicate = cb.equal(root.get("month"), formattedMonth);

        String startDayStr = String.format("%02d", startDay);
        String endDayStr = String.format("%02d", endDay);

        Predicate dayPredicate = cb.between(root.get("day"), startDayStr, endDayStr);
        predicates.add(cb.and(monthPredicate, dayPredicate));
    }
}
