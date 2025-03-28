package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面アイテムのカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemCustomRepository {

    List<VehicleDiagramItem> search(TransportAbilityPublicSearch searchRequest);

    VehicleDiagramItem getTransportAbilityProposal();
}
