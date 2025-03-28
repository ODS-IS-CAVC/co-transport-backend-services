package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.trspAbilityLineItem.response.TrspAbilityLineItemResponseDTO;

/**
 * <PRE>
 * 運送能力明細サービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrspAbilityLineItemService {

    /**
     * 運送能力明細を登録.<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @param remoteIp         リモートIP
     * @return 運送能力明細DTO
     */
    TrspAbilityLineItemResponseDTO registerTrspAbilityLineItem(CommonRequestDTO commonRequestDTO, String remoteIp);
}
