package nlj.business.shipper.repository;

import java.util.List;
import nlj.business.shipper.domain.TransportPlan;
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
public interface TransportPlanCustomRepository {

    Page<TransportPlan> searchTrspPlanByTrspName(String operatorId, String trspName, List<Integer> statuses,
        Long outerPackageCode, Pageable pageable);
}
