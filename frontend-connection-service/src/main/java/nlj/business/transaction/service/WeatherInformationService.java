package nlj.business.transaction.service;

import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.request.WeatherRequest;
import nlj.business.transaction.dto.response.VehicleDiagramStatusResponseDTO;
import nlj.business.transaction.dto.response.WeatherResponse;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 天気情報サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface WeatherInformationService {

    /**
     * 天気情報を検証する。
     *
     * @param transOrderId            トランス注文ID
     * @param departureArrivalTimeDTO 出発到着時間のDTO
     * @param weatherRequest          天気リクエスト
     * @return 検証された天気情報のレスポンス
     */
    WeatherResponse validateWeatherInformation(Long transOrderId,
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO, WeatherRequest weatherRequest);

    /**
     * 車両ダイアグラム項目IDによる天気情報を検証する。
     *
     * @param diagramItemId           車両ダイアグラム項目ID
     * @param departureArrivalTimeDTO 出発到着時間のDTO
     * @param timeRange               時間範囲
     * @return 検証された車両ダイアグラムのステータスレスポンス
     */
    VehicleDiagramStatusResponseDTO validateWeatherInformationByDiagramItemId(Long diagramItemId,
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO, Long timeRange);

    /**
     * 天気情報のステータスを更新する。
     *
     * @param diagramItemId 車両ダイアグラム項目ID
     * @param status        ステータス
     * @param type          タイプ
     * @param message       メッセージ
     */
    void updateWeatherInformationStatus(Long diagramItemId, String status, Long type, String message);
}
