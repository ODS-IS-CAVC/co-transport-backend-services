package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.shipperTrspCapacity.AttributeDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportAbilityLineItemService {

    /**
     * 運送能力を取得する。
     *
     * @param queryParams 運送能力検索パラメータ
     * @param page        ページ番号
     * @param limit       ページサイズ
     * @return 運送能力の属性DTO
     */
    AttributeDTO getAllTransportAbilityLineItems(ShipperTransportCapacitySearchDTO queryParams, int page, int limit);
}
