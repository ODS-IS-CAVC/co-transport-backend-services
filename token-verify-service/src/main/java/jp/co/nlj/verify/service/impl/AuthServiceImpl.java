package jp.co.nlj.verify.service.impl;

import com.next.logistic.security.config.NljAuthProperties;
import jp.co.nlj.verify.constant.APIConstant;
import jp.co.nlj.verify.dto.auth.request.TokenDTO;
import jp.co.nlj.verify.dto.auth.response.VerifyTokenResponseDTO;
import jp.co.nlj.verify.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 認証サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NljAuthProperties authProperties;

    /**
     * トークン検証を行う。<BR>
     *
     * @param idToken トークン
     * @return トークン検証レスポンスDTO
     */
    @Override
    public VerifyTokenResponseDTO verifyToken(String idToken) {
        HttpHeaders headers = buildHeader();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setIdToken(idToken);
        HttpEntity<TokenDTO> entity = new HttpEntity<>(tokenDTO, headers);

        ResponseEntity<VerifyTokenResponseDTO> response = restTemplate.postForEntity(authProperties.getVerifyToken(),
            entity, VerifyTokenResponseDTO.class);
        return response.getBody();
    }

    /**
     * ヘッダーを作成する。<BR>
     *
     * @return ヘッダー
     */
    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }
}
