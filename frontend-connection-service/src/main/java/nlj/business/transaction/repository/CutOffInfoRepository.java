package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.CutOffInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 切断情報のリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CutOffInfoRepository extends JpaRepository<CutOffInfo, Long> {

    List<CutOffInfo> findByVehicleAvbResourceId(Long vehicleAvbResourceId);
}
