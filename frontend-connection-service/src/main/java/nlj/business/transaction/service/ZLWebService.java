package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;

/**
 * <PRE>
 * ZLWebサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ZLWebService {

    /**
     * ZLWebに送信する。
     *
     * @param orderId                 注文ID
     * @param departureArrivalTimeDTO 出発到着時間のDTO
     * @param request                 HTTPリクエスト
     */
    void sendToZLWeb(Long orderId, DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO,
        HttpServletRequest request);

}
