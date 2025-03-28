package nlj.business.transaction.repository;

import java.util.List;
import java.util.Optional;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 市場輸送計画品目リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MarketTransportPlanItemRepository extends JpaRepository<MarketTransportPlanItem, Long> {

    List<MarketTransportPlanItem> findByStatus(Integer status);

    Optional<MarketTransportPlanItem> findFirstByTransportPlanItemId(Long transportPlanItemId);
}
