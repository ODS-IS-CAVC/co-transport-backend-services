package nlj.business.carrier.link.service;

import java.time.LocalDateTime;
import nlj.business.carrier.link.domain.VehicleDiagramMobilityHub;

/**
 * <PRE>
 * 車両ダイアグラム モビリティ ハブ サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramMobilityHubService {

    /**
     * 車両ダイアグラム モビリティ ハブ を保存.<BR>
     *
     * @param vehicleDiagramItemId        車両ダイアグラムアイテムID
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレーラーID
     * @param mobilityHubId               モビリティハブID
     * @param sizeClass                   サイズクラス
     * @param validFrom                   有効開始日時
     * @param validUntil                  有効終了日時
     * @param mhReservationId             モビリティハブ予約ID
     * @param freightId                   貨物ID
     * @param truckId                     トラックID
     * @param type                        タイプ
     * @param vehicleType                 車両タイプ
     */
    VehicleDiagramMobilityHub saveVehicleDiagramMobilityHub(Long vehicleDiagramItemId, Long vehicleDiagramItemTrailerId,
        String mobilityHubId, String sizeClass, LocalDateTime validFrom, LocalDateTime validUntil,
        String mhReservationId, String freightId, String truckId, int type, int vehicleType);
}
