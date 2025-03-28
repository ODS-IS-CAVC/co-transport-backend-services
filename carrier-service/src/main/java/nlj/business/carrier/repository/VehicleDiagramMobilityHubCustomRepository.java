package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブカスタムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramMobilityHubCustomRepository {

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラムモビリティハブを検索する。
     *
     * @param vehicleDiagramItemId 車両ダイアグラム明細ID
     * @return 車両ダイアグラムモビリティハブのリスト
     */
    List<VehicleDiagramMobilityHub> findByVehicleDiagramItemId(Long vehicleDiagramItemId);

    /**
     * 車両ダイアグラム明細IDのリストで車両ダイアグラムモビリティハブを検索する。
     *
     * @param vehicleDiagramItemIds 車両ダイアグラム明細IDのリスト
     * @return 車両ダイアグラムモビリティハブのリスト
     */
    List<VehicleDiagramMobilityHub> findByVehicleDiagramItemIds(List<Long> vehicleDiagramItemIds);
}
