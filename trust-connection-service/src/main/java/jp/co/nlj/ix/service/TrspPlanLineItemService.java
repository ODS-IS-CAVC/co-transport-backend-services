package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemRequest;

/**
 * <PRE>
 * 輸送計画明細サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrspPlanLineItemService {

    /**
     * 輸送計画を更新する。
     *
     * @param trspPlanLineItemRequest 輸送計画リクエスト
     * @param instructionId           輸送指示ID
     */
    void updateTrspPlan(TrspPlanLineItemRequest trspPlanLineItemRequest, String instructionId);
}
