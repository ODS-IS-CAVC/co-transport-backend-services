package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <PRE>
 * 市場輸送計画品目サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface IXBurningService {

    /**
     * IXバーングを登録する。
     *
     * @param transOrderId   トランスオーダーID
     * @param servletRequest HTTPリクエスト
     */
    void registerIXBurning(Long transOrderId, HttpServletRequest servletRequest);
}
