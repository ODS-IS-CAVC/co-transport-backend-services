package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.VehicleDiagramMobilityHub;
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
     * 車両ダイアグラムアイテムID検索.<BR>
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @return 車両ダイアグラムモビリティハブ
     */
    List<VehicleDiagramMobilityHub> findByVehicleDiagramItemId(Long vehicleDiagramItemId);

    /**
     * 車両ダイアグラムアイテムID検索.<BR>
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @param type                 タイプ
     * @return 車両ダイアグラムモビリティハブ
     */
    VehicleDiagramMobilityHub findByVehicleDiagramItemIdAndType(Long vehicleDiagramItemId, int type);
}
