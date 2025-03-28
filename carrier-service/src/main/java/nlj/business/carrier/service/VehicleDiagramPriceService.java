package nlj.business.carrier.service;

import nlj.business.carrier.dto.vehicleDiagramItemTrailer.request.VehicleDiagramItemTrailerRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.request.VehicleDiagramPriceRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.response.VehicleDiagramPriceResponseDTO;

/**
 * <PRE>
 * 車両図面価格サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramPriceService {

    /**
     * 車両図面価格を取得する。
     *
     * @param headId 車両図面ヘッドのID
     * @return 車両図面価格レスポンスDTO
     */
    VehicleDiagramPriceResponseDTO getVehicleDiagramPrices(Long headId);

    /**
     * 車両図面価格を更新する。
     *
     * @param headId                        車両図面ヘッドのID
     * @param vehicleDiagramPriceRequestDTO 車両図面価格リクエストDTO
     */
    void updateVehicleDiagramPrice(Long headId, VehicleDiagramPriceRequestDTO vehicleDiagramPriceRequestDTO);

    /**
     * 車両図面明細トレーラーの価格を日付ごとに追加する。
     *
     * @param vehicleDiagramId                    車両図面のID
     * @param vehicleDiagramItemTrailerRequestDTO 車両図面明細トレーラーリクエストDTO
     */
    void addPriceForVehicleDiagramItemTrailerByDate(Long vehicleDiagramId,
        VehicleDiagramItemTrailerRequestDTO vehicleDiagramItemTrailerRequestDTO);
}
