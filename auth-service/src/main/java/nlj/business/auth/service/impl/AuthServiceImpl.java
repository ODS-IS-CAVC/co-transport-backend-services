package nlj.business.auth.service.impl;

import com.next.logistic.model.SystemLogin;
import com.next.logistic.model.UserLogin;
import com.next.logistic.security.config.NljAuthProperties;
import nlj.business.auth.dto.auth.request.TokenDTO;
import nlj.business.auth.dto.auth.response.TokenResponseDTO;
import nlj.business.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 認証サービス実装クラス<BR>
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

    @Override
    public TokenResponseDTO loginUser(UserLogin userLogin) {

        HttpHeaders headers = buildHeader();
        HttpEntity<UserLogin> entity = new HttpEntity<>(userLogin, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(authProperties.getLoginUser(),
            entity, TokenResponseDTO.class);
        return response.getBody();
    }

    @Override
    public TokenResponseDTO loginSystem(SystemLogin systemLogin) {
        HttpHeaders headers = buildHeader();
        HttpEntity<SystemLogin> entity = new HttpEntity<>(systemLogin, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(authProperties.getLoginSystem(),
            entity, TokenResponseDTO.class);
        return response.getBody();
    }

    @Override
    public TokenResponseDTO refreshToken(String refreshToken) {
        HttpHeaders headers = buildHeader();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRefreshToken(refreshToken);
        HttpEntity<TokenDTO> entity = new HttpEntity<>(tokenDTO, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(authProperties.getRefreshToken(),
            entity, TokenResponseDTO.class);
        return response.getBody();
    }

    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", authProperties.getApiKey());
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }
}
