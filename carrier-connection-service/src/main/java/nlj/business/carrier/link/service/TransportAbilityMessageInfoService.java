package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;

/**
 * <PRE>
 * 輸送能力メッセージ情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportAbilityMessageInfoService {

    /**
     * 輸送能力メッセージ情報を登録または更新.<BR>
     *
     * @param dto       運送能力DTO
     * @param xTracking Xトラッキング
     */
    void registerOrUpdateTransportInfor(ShipperTransportCapacityRegisterDTO dto, String xTracking);

    /**
     * 輸送能力メッセージ情報を削除.<BR>
     *
     * @param dto 運送能力DTO
     */
    void deleteTransportInfor(ShipperTransportCapacityRegisterDTO dto);

    void deleteTransportInforYamato(String trspCliPrtyHeadOffId, String trspCliPrtyBrncOffId, String serviceNo,
        String serviceStrtDate);

}
