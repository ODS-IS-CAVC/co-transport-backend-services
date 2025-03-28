package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面アイテムトレーラーのカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrailerCustomRepository {

    List<VehicleDiagramItemTrailer> getListMatching(TransportPlanItem transportPlanItem);

    List<Long> getVehicleDiagramItemTrailerIdsByVehicleDiagramId(Long vehicleDiagramId);

}
