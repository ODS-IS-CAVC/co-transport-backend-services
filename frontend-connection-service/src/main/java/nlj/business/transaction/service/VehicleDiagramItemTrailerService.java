package nlj.business.transaction.service;

import nlj.business.transaction.dto.VehicleDiagramItemTrailerDTO;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 車両図面アイテムトレーラーサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface VehicleDiagramItemTrailerService {

    /**
     * 輸送能力提案を取得する。
     *
     * @return 輸送能力提案のDTO
     */
    VehicleDiagramItemTrailerDTO getTransportAbilityProposal();
}
