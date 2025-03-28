package jp.co.nlj.ttmi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.config.NljUrlProperties;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.nlj.ttmi.constant.APIConstant;
import jp.co.nlj.ttmi.service.AuthService;
import jp.co.nlj.ttmi.service.TokenDataChannelCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * トークンデータチャンネルキャッシュサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Service
public class TokenDataChannelCacheServiceImpl implements TokenDataChannelCacheService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NljUrlProperties urlProperties;

    @Autowired
    private AuthService authService;

    private static final Map<String, CacheItem> tokenCache = new ConcurrentHashMap<>();

    private void addToken(String token) {
        tokenCache.put(APIConstant.CACHE_KEY_TOKEN,
            new CacheItem(token, LocalDateTime.now().plusMinutes(APIConstant.CACHE_EXPIRED_TOKEN)));
    }

    /**
     * キャッシュトークン取得。<BR>
     *
     * @return トークン
     * @throws JsonProcessingException
     */
    public String getCacheToken() throws JsonProcessingException {
        CacheItem cacheItem = tokenCache.get(APIConstant.CACHE_KEY_TOKEN);
        if (cacheItem != null && cacheItem.getExpiryTime().isAfter(LocalDateTime.now())) {
            return cacheItem.getToken();
        } else {
            tokenCache.remove(APIConstant.CACHE_KEY_TOKEN); // Remove expired token

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(urlProperties.getLoginData(), headers);
            ResponseEntity<String> response = restTemplate.exchange(urlProperties.getLoginSystem(), HttpMethod.POST,
                httpEntity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String accessToken = jsonNode.get("accessToken").asText();
            addToken(accessToken);

            return accessToken;
        }
    }

    /**
     * キャッシュアイテム。<BR>
     *
     * @author Next Logistics Japan
     */
    private static class CacheItem {

        private final String token;
        private final LocalDateTime expiryTime;

        public CacheItem(String token, LocalDateTime expiryTime) {
            this.token = token;
            this.expiryTime = expiryTime;
        }

        public String getToken() {
            return token;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
