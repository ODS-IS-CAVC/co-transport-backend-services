package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportAbilityLineItemService {

    /**
     * 運送能力を取得.<BR>
     *
     * @param queryParams 運送能力検索リクエスト
     * @return 運送能力DTO
     */
    ShipperTransportCapacityRegisterDTO getAllTransportAbilityLineItems(ShipperTransportCapacitySearchDTO queryParams);
}
