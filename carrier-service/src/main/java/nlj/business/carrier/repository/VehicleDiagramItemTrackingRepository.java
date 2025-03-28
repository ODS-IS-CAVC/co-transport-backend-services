package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramItemTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細トラッキングリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrackingRepository extends JpaRepository<VehicleDiagramItemTracking, Long> {

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細トラッキングを検索する。
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細トラッキングのリスト
     */
    List<VehicleDiagramItemTracking> findAllByVehicleDiagramItemId(Long id);
}
