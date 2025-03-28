package jp.co.nlj.ix.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.dto.sendMail.SendMailRequest;

/**
 * <PRE>
 * メールサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface MailService {

    /**
     * IXメールを送信する。
     *
     * @param sendMailRequest    送信するメールのリクエスト
     * @param httpServletRequest HTTPリクエスト
     */
    void sendIXMail(SendMailRequest sendMailRequest, HttpServletRequest httpServletRequest);
}
