package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramItemOperationTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細オペレーショントラッキングリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemOperationTrackingRepository extends
    JpaRepository<VehicleDiagramItemOperationTracking, Long> {

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細オペレーショントラッキングを検索する。
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細オペレーショントラッキングのリスト
     */
    List<VehicleDiagramItemOperationTracking> findAllByVehicleDiagramItemId(Long id);
}
