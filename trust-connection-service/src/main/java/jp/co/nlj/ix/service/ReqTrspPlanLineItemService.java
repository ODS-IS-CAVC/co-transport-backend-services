package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.commonBody.response.CommonResponseDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemDeleteRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemRegisterRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;

/**
 * <PRE>
 * 要求輸送計画明細サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ReqTrspPlanLineItemService {

    /**
     * 輸送計画を登録する。
     *
     * @param request   輸送計画登録リクエスト
     * @param xTracking トラッキング情報
     */
    void registerTrspPlan(ReqTrspPlanLineItemRegisterRequest request, String xTracking);

    /**
     * 輸送計画を削除する。
     *
     * @param request   輸送計画削除リクエスト
     * @param xTracking トラッキング情報
     */
    void deleteTrspPlan(ReqTrspPlanLineItemDeleteRequest request, String xTracking);

    /**
     * 輸送計画を検索する。
     *
     * @param request 輸送計画検索リクエスト
     * @return 検索結果の共通レスポンスDTO
     */
    CommonResponseDTO searchTrspPlan(ReqTrspPlanLineItemSearchRequest request);
}
