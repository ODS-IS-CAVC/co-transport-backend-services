package jp.co.nlj.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.dto.reservation.request.ReservationRequestDTO;
import jp.co.nlj.gateway.dto.reservation.response.ReservationResponseDTO;

/**
 * <PRE>
 * MobilityHubInfoServiceインターフェースは、モビリティハブ情報サービスを定義するためのインターフェースです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface MobilityHubInfoService {

    /**
     * モビリティハブを予約するメソッド。
     *
     * @param httpServletRequest HTTPリクエスト
     * @param request            予約リクエストDTO
     * @return 予約レスポンスDTO
     */
    ReservationResponseDTO reserveMobilityHub(HttpServletRequest httpServletRequest, ReservationRequestDTO request);

    /**
     * モビリティハブの予約をキャンセルするメソッド。
     *
     * @param httpServletRequest HTTPリクエスト
     * @param reservationId      予約ID
     */
    void cancelMobilityHubReservation(HttpServletRequest httpServletRequest, String reservationId);
}
