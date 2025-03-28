package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.CnsLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 貨物明細リポジトリ
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CnsLineItemRepository extends JpaRepository<CnsLineItem, Long> {

    /**
     * 輸送計画明細項目IDで削除.<BR>
     *
     * @param ids 輸送計画明細項目ID
     */
    void deleteAllByTrspPlanLineItem_IdIn(List<Long> ids);

    /**
     * 輸送計画明細項目IDで検索.<BR>
     *
     * @param ids 輸送計画明細項目ID
     * @return 貨物明細
     */
    List<CnsLineItem> findAllByTrspPlanLineItem_IdIn(List<Long> ids);

}
