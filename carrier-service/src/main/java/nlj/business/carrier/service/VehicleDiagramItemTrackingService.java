package nlj.business.carrier.service;

import jakarta.servlet.http.HttpServletRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingResponseDTO;

/**
 * <PRE>
 * 車両ダイアグラム明細の追跡情報を取得するサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramItemTrackingService {

    /**
     * 車両ダイアグラム明細の追跡情報を取得する。
     *
     * @param itemId             車両ダイアグラム明細のID
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラム明細追跡レスポンスDTO
     */
    VehicleDiagramItemTrackingResponseDTO getVehicleDiagramItemTracking(Long itemId,
        HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラム明細の操作追跡情報を取得する。
     *
     * @param itemId             車両ダイアグラム明細のID
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラム明細追跡レスポンスDTO
     */
    VehicleDiagramItemTrackingResponseDTO getOperationTracking(Long itemId, HttpServletRequest httpServletRequest);
}
