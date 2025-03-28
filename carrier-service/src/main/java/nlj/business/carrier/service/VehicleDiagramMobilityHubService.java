package nlj.business.carrier.service;

import java.time.LocalDateTime;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;

/**
 * <PRE>
 * 車両ダイアグラム モビリティ ハブ サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramMobilityHubService {

    /**
     * 車両ダイアグラムモビリティハブを保存する。
     *
     * @param vehicleDiagramItemId        車両ダイアグラム明細のID
     * @param vehicleDiagramItemTrailerId 車両ダイアグラム明細トレーラーのID
     * @param mobilityHubId               モビリティハブのID
     * @param sizeClass                   サイズクラス
     * @param validFrom                   有効期間開始日時
     * @param validUntil                  有効期間終了日時
     * @param mhReservationId             モビリティハブ予約ID
     * @param freightId                   貨物ID
     * @param truckId                     トラックID
     * @param type                        タイプ
     * @param vehicleType                 車両タイプ
     * @param vehicleDiagramAllocationId  車両ダイアグラム割り当てID
     * @return 保存された車両ダイアグラムモビリティハブ
     */
    VehicleDiagramMobilityHub saveVehicleDiagramMobilityHub(Long vehicleDiagramItemId, Long vehicleDiagramItemTrailerId,
        String mobilityHubId, String sizeClass, LocalDateTime validFrom, LocalDateTime validUntil,
        String mhReservationId, String freightId, String truckId, int type, int vehicleType,
        Long vehicleDiagramAllocationId);

}
