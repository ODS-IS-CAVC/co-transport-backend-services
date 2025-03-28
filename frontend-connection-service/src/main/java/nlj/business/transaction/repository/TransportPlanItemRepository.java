package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画アイテムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanItemRepository extends JpaRepository<TransportPlanItem, Long>,
    TransportPlanItemCustomRepository {

    List<TransportPlanItem> findByStatus(Integer status);

    List<TransportPlanItem> findByTransportPlanId(Long transportPlanId);

    List<TransportPlanItem> findAllByStatus(Integer status);
}
