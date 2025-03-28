package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面アイテムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemRepository extends JpaRepository<VehicleDiagramItem, Long> {

    List<VehicleDiagramItem> findByStatus(Integer status);

    List<VehicleDiagramItem> findByVehicleDiagramId(Long id);

}
