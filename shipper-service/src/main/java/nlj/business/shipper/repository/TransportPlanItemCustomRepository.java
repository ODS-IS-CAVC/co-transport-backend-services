package nlj.business.shipper.repository;

import nlj.business.shipper.domain.TransportPlanItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanItemCustomRepository {

    Page<TransportPlanItem> searchTrspPlanItem(String operatorId, String trspName, Pageable pageable);
}
