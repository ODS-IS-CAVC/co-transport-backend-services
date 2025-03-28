package nlj.business.transaction.service;

import java.util.List;
import nlj.business.transaction.dto.MarketVehicleDiagramItemTrailerDTO;

/**
 * <PRE>
 * 市場輸送計画品目サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface MarketVehicleDiagramItemTrailerService {

    /**
     * 販売中の市場輸送計画品目を取得する。
     *
     * @return 販売中の市場輸送計画品目のリスト
     */
    List<MarketVehicleDiagramItemTrailerDTO> findOnSale();
}
