package nlj.business.transaction.service;

import java.util.List;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.MarketTransportPlanItemDTO;
import nlj.business.transaction.dto.TransportPlanItemDTO;
import nlj.business.transaction.dto.request.MarketTransportPlanItemSearch;
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
public interface TransportPlanItemService {

    /**
     * 市場輸送計画項目を検索する。
     *
     * @param searchRequest 検索リクエスト
     * @return 検索結果のページ
     */
    Page<MarketTransportPlanItemDTO> search(MarketTransportPlanItemSearch searchRequest);

    /**
     * 販売中の輸送計画項目を取得する。
     *
     * @return 販売中の輸送計画項目のリスト
     */
    List<TransportPlanItemDTO> findOnSale();

    /**
     * 指定されたIDの輸送計画項目のステータスを更新する。
     *
     * @param id 更新する輸送計画項目のID
     * @return 更新された輸送計画項目
     */
    MarketTransportPlanItem updateStatusOnSale(Long id);

    /**
     * 輸送計画提案を取得する。
     *
     * @return 輸送計画提案のDTO
     */
    TransportPlanItemDTO getTransportPlanProposal();

}
