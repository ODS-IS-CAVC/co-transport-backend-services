package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.TransactionTimeCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画明細カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransactionTimeCustomRepositoryImpl implements TransactionTimeCustomRepository {

    private final EntityManager entityManager;

    /**
     * 運送計画明細の最大更新日を取得する。
     *
     * @param target 運送計画明細のターゲット
     * @return 運送計画明細の最大更新日
     */
    @Override
    public String getMaxUpdatedDate(String target) {
        String table;

        if ("shipper".equals(target)) {
            table = "req_cns_line_item";
        } else if ("carrier".equals(target)) {
            table = "vehicle_avb_resource";
        } else if ("carrier2".equals(target)) {
            table = "cns_line_item";
        } else {
            return null;
        }

        String queryString = "SELECT MAX(UPDATED_DATE) FROM " + table;
        Query query = entityManager.createNativeQuery(queryString);
        Object result = query.getSingleResult();

        if (result != null) {
            return result.toString();
        }

        return null;
    }

    /**
     * 運送計画明細の更新IDを取得する。
     *
     * @param target     運送計画明細のターゲット
     * @param lastUpdate 運送計画明細の更新日
     * @return 運送計画明細の更新IDリスト
     */
    @Override
    public List<Long> getUpdatedIds(String target, String lastUpdate) {
        String table;
        if ("shipper".equals(target)) {
            table = "req_cns_line_item";
        } else if ("carrier".equals(target)) {
            table = "vehicle_avb_resource";
        } else if ("carrier2".equals(target)) {
            table = "cns_line_item";
        } else {
            return null;
        }

        String reformattedLastUpdate;
        try {
            LocalDate date = LocalDate.parse(lastUpdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            reformattedLastUpdate = date.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format for lastUpdate: " + lastUpdate, e);
        }

        String queryString =
            "SELECT id FROM " + table + " WHERE UPDATED_DATE > TO_TIMESTAMP(:time, 'yyyy-MM-dd HH24:mi:ss')";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("time", reformattedLastUpdate);

        List<Long> result = query.getResultList();
        return result;
    }
}
