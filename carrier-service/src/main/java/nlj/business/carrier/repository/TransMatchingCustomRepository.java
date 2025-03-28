package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.dto.vehicleDiagram.response.CarrierServiceTransMatchingResponse;

/**
 * <PRE>
 * 輸送サービスマッチングカスタムリポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMatchingCustomRepository {

    /**
     * 車両ダイアグラムアイテムトレーラIDのリストで輸送サービスマッチングを検索する。<BR>
     *
     * @param ids 車両ダイアグラムアイテムトレーラIDのリスト
     * @return 輸送サービスマッチングのリスト
     */
    List<CarrierServiceTransMatchingResponse> findAllByVehicleDiagramItemTrailerIdIn(List<Long> ids);
}
