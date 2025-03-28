package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemDeleteRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemRegisterRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;

/**
 * <PRE>
 * 輸送計画明細サービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrspPlanLineItemService {

    /**
     * 輸送計画明細を登録.<BR>
     *
     * @param trspPlanLineItemRegisterRequest 輸送計画明細登録リクエスト
     * @param xTracking                       Xトラッキング
     */
    void registerTrspPlan(TrspPlanLineItemRegisterRequest trspPlanLineItemRegisterRequest, String xTracking);

    /**
     * 輸送計画明細を削除.<BR>
     *
     * @param trspPlanLineItemRegisterRequest 輸送計画明細削除リクエスト
     * @param xTracking                       Xトラッキング
     */
    void deleteTrspPlan(TrspPlanLineItemDeleteRequest trspPlanLineItemRegisterRequest, String xTracking);

    void deleteTrspPlanYamato(String trspInstructionId, String serviceNo, String serviceStrtDate,
        String cnsgPrtyHeadOffId, String cnsgPrtyBrncOffId, String trspRqrPrtyHeadOffId, String trspRqrPrtyBrncOffId);

    /**
     * 輸送計画明細を検索.<BR>
     *
     * @param request 輸送計画明細検索リクエスト
     * @return 輸送計画明細DTO
     */
    CommonResponseDTO searchTrspPlan(TrspPlanLineItemSearchRequest request);
}
