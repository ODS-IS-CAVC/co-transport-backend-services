package jp.co.nlj.ix.service.impl;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.NextWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.MessageConstant;
import jp.co.nlj.ix.dto.sendMail.SendMailRequest;
import jp.co.nlj.ix.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * メールサービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final NljAuthProperties authProperties;
    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;

    /**
     * IXメールを送信する。
     *
     * @param sendMailRequest    送信メールリクエスト
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void sendIXMail(SendMailRequest sendMailRequest, HttpServletRequest httpServletRequest) {
        String urlSendIXMail = urlProperties.getDomainTransaction().concat("/api/v1/transport-order/send-mail");
        HttpHeaders httpHeaders = setHeader(httpServletRequest);
        HttpEntity<SendMailRequest> requestEntity =
            new HttpEntity<>(sendMailRequest, httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                urlSendIXMail,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call Carrier Service : " + response);
        } catch (Exception e) {
            log.error("ERROR call Carrier Service : {}", e.getMessage());
            NextWebUtil.throwCustomException(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstant.System.SYSTEM_ERR_001);
        }
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param httpServletRequest HTTPリクエスト
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
