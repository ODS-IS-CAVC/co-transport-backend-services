package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.shipperOperationPlans.request.CarrierOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.CarrierOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.CarrierOperatorPlansRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansInsertResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansUpdateResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;

/**
 * <PRE>
 * 輸送計画明細サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarrierOperationPlansService {

    /**
     * 輸送計画を検索する。
     *
     * @param request 検索リクエスト
     * @return 検索結果
     */
    TrspPlanLineItemSearchResponse searchTransportPlan(TrspPlanLineItemSearchRequest request);

    /**
     * アイテムを挿入する。
     *
     * @param request 挿入リクエスト
     * @return 挿入結果
     */
    CarrierOperatorPlansInsertResponse insertItem(CarrierOperatorPlansRequest request);

    /**
     * アイテムを更新する。
     *
     * @param request     更新リクエスト
     * @param proposeId   提案ID
     * @param operationId 操作ID
     * @return 更新結果
     */
    CarrierOperatorPlansUpdateResponse updateItem(CarrierOperatorPlansRequest request, String proposeId,
        String operationId);

    /**
     * 車両運用の承認を行う。
     *
     * @param carrierOperationApprovalRequestDTO 承認リクエストDTO
     * @param operationIdParam                   操作IDパラメータ
     * @param proposeId                          提案ID
     * @return 承認結果
     */
    CarrierOperationApprovalResponseDTO carrierOperationApproval(
        CarrierOperationApprovalRequestDTO carrierOperationApprovalRequestDTO, String operationIdParam,
        String proposeId);
}
