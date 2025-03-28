package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.dto.CnsLineItemByDateDTO;
import nlj.business.transaction.dto.CnsLineItemByDateResponseDTO;
import nlj.business.transaction.dto.request.CarrierListOrderSearch;
import nlj.business.transaction.dto.request.CarrierTransportPlanRequest;
import nlj.business.transaction.dto.request.CommonPagingSearch;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 日付別コンテンツ品目サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface CnsLineItemByDateService {

    /**
     * 車両の販売中の注文のページ付きリストを取得する。
     *
     * @param searchRequest 車両リスト注文の検索リクエスト
     * @return ページ付きのCnsLineItemByDateDTOリスト
     */
    Page<CnsLineItemByDateDTO> getPagedListCarrierOrderOnSale(CarrierListOrderSearch searchRequest);

    /**
     * 輸送計画のページ付きリストを取得する。
     *
     * @param searchRequest 輸送計画の検索リクエスト
     * @return ページ付きのCnsLineItemByDateResponseDTOリスト
     */
    Page<CnsLineItemByDateResponseDTO> getPagedTransportPlan(TransportPlanPublicSearch searchRequest);

    /**
     * 公開された輸送計画のページ付きリストを取得する。
     *
     * @param request            輸送計画のリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return ページ付きのCnsLineItemByDateDTOリスト
     */
    Page<CnsLineItemByDateDTO> getPagedTransportPlanPublic(CarrierTransportPlanRequest request,
        HttpServletRequest httpServletRequest);

    /**
     * 温度範囲に基づいて販売中の輸送計画のページ付きリストを取得する。
     *
     * @param temperatureRange 温度範囲のリスト
     * @param request          共通のページング検索リクエスト
     * @return ページ付きのCnsLineItemByDateリスト
     */
    Page<CnsLineItemByDate> getPagedTransportPlanSale(List<Integer> temperatureRange, CommonPagingSearch request);

    /**
     * IDに基づいてCnsLineItemByDateを取得する。
     *
     * @param id CnsLineItemByDateのID
     * @return CnsLineItemByDateオブジェクト
     */
    CnsLineItemByDate findById(Long id);
}
