package jp.co.nlj.ttmi.service.impl;

import com.next.logistic.exception.NextWebException;
import jp.co.nlj.ttmi.configuration.OAuth2TTMIConfig;
import jp.co.nlj.ttmi.configuration.TokenCacheTTMI;
import jp.co.nlj.ttmi.dto.trackBySip.response.TokenResponse;
import jp.co.nlj.ttmi.service.TokenTTMIService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * トークンTTMIサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TokenTTMIServiceImpl implements TokenTTMIService {

    private final Logger logger = LoggerFactory.getLogger(TokenTTMIServiceImpl.class);
    private final RestTemplate restTemplate;
    private final OAuth2TTMIConfig authProperties;

    /**
     * 有効トークン取得。<BR>
     *
     * @return トークン
     */
    public String getValidToken() {
        String token = null;
        try {
            logger.info("Start get token ttmi");
            if (!TokenCacheTTMI.isTokenValid()) {
                logger.info("Start call get token ttmi");
                refreshToken();
            }
            logger.info("Success get token ttmi");
            token = TokenCacheTTMI.getAccessToken();
        } catch (NextWebException exception) {
            logger.error("ERROR call get token ttmi");
        }
        return token;
    }

    /**
     * トークン更新。<BR>
     *
     * @param refreshToken リフレッシュトークン
     * @return TokenResponseDTO
     */
    private synchronized void refreshToken() {
        if (!TokenCacheTTMI.isTokenValid()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(authProperties.getClientId(), authProperties.getClientSecret());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                authProperties.getTokenUrl(),
                HttpMethod.POST,
                request,
                TokenResponse.class
            );

            if (response.getBody() != null) {
                TokenCacheTTMI.setTokenInfo(
                    response.getBody().getAccessToken(),
                    response.getBody().getExpiresIn()
                );
            }
        }
    }
}