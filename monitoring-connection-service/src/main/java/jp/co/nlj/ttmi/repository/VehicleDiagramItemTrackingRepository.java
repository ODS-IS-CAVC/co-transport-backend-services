package jp.co.nlj.ttmi.repository;

import java.util.List;
import jp.co.nlj.ttmi.domain.VehicleDiagramItemTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運行ダイアグラムアイテムトラッキングリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrackingRepository extends JpaRepository<VehicleDiagramItemTracking, Long> {

    List<VehicleDiagramItemTracking> findAllByVehicleDiagramItemId(Long id);
}
