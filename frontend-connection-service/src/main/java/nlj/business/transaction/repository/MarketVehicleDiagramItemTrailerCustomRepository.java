package nlj.business.transaction.repository;

import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.dto.request.MarketVehicleDiagramItemTrailerSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;


/**
 * <PRE>
 * マーケット車両図項目トレーラーカスタムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MarketVehicleDiagramItemTrailerCustomRepository {

    Page<MarketVehicleDiagramItemTrailer> search(MarketVehicleDiagramItemTrailerSearch searchRequest);
}
