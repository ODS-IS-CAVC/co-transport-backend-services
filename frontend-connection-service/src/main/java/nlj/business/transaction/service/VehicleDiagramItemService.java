package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.MarketVehicleDiagramItemTrailerDTO;
import nlj.business.transaction.dto.VehicleDiagramItemDTO;
import nlj.business.transaction.dto.request.MarketVehicleDiagramItemTrailerSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 輸送計画項目サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface VehicleDiagramItemService {

    /**
     * 車両ダイアグラム明細を検索する。
     *
     * @param searchRequest 検索リクエスト
     * @return 検索結果のページ
     */
    Page<MarketVehicleDiagramItemTrailerDTO> search(MarketVehicleDiagramItemTrailerSearch searchRequest);

    /**
     * 販売中の車両ダイアグラム項目を取得する。
     *
     * @return 販売中の車両ダイアグラム項目のリスト
     */
    List<VehicleDiagramItemDTO> findOnSale();

    /**
     * 車両ダイアグラム明細のステータスを更新する。
     *
     * @param id 車両ダイアグラム明細のID
     * @return 更新結果
     */
    MarketVehicleDiagramItemTrailer updateStatusOnSale(Long id);

    /**
     * 輸送能力提案を取得する。
     *
     * @return 輸送能力提案のDTO
     */
    VehicleDiagramItemDTO getTransportAbilityProposal();

    /**
     * 出発到着時間を取得する。
     *
     * @param transOrderId       輸送注文ID
     * @param httpServletRequest HTTPリクエスト
     * @return 出発到着時間のDTO
     */
    DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTime(Long transOrderId,
        HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラム項目IDによる出発到着時間を取得する。
     *
     * @param diagramItemId      車両ダイアグラム項目ID
     * @param httpServletRequest HTTPリクエスト
     * @return 出発到着時間のDTO
     */
    DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTimeByDiagramItemId(Long diagramItemId,
        HttpServletRequest httpServletRequest);

}
