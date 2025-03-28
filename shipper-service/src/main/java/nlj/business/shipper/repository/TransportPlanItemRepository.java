package nlj.business.shipper.repository;

import java.util.List;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画アイテムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanItemRepository extends JpaRepository<TransportPlanItem, Long> {

    List<TransportPlanItem> findByTransportPlanId(Long transportPlanId);

    void deleteAllByTransportPlan(TransportPlan transportPlan);

    TransportPlanItem getTransportPlanItemById(Long id);

    List<TransportPlanItem> findAllByCargoInfoId(Long id);
} 