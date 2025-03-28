package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemDeleteRequest;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemRegisterRequest;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;

/**
 * <PRE>
 * 要求輸送計画明細サービス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ReqTrspPlanLineItemService {

    /**
     * 輸送計画を登録.<BR>
     *
     * @param request   要求輸送計画明細登録リクエスト
     * @param xTracking Xトラッキング
     */
    void registerTrspPlan(ReqTrspPlanLineItemRegisterRequest request, String xTracking);

    /**
     * 輸送計画を削除.<BR>
     *
     * @param request   要求輸送計画明細削除リクエスト
     * @param xTracking Xトラッキング
     */
    void deleteTrspPlan(ReqTrspPlanLineItemDeleteRequest request, String xTracking);

    /**
     * 輸送計画を検索.<BR>
     *
     * @param request 要求輸送計画明細検索リクエスト
     * @return 共通レスポンスDTO
     */
    CommonResponseDTO searchTrspPlan(ReqTrspPlanLineItemSearchRequest request);
}
