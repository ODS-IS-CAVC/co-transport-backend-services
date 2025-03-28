package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;

/**
 * <PRE>
 * 配送マッチング。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TTMITrackingBySipService {

    /**
     * 配送マッチングを登録する。
     *
     * @param transOrderId   トランス注文ID
     * @param method         HTTPメソッド
     * @param servletRequest HTTPリクエスト
     */
    void registerTTMITrackingBySip(Long transOrderId, HttpMethod method, HttpServletRequest servletRequest);
}
