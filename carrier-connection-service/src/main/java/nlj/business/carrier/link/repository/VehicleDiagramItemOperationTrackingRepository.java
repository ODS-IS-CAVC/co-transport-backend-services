package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.VehicleDiagramItemOperationTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムアイテム追跡リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemOperationTrackingRepository extends
    JpaRepository<VehicleDiagramItemOperationTracking, Long> {

    /**
     * 車両ダイアグラムアイテムID検索.<BR>
     *
     * @param id 車両ダイアグラムアイテムID
     * @return 車両ダイアグラムアイテム追跡
     */
    List<VehicleDiagramItemOperationTracking> findAllByVehicleDiagramItemId(Long id);
}
