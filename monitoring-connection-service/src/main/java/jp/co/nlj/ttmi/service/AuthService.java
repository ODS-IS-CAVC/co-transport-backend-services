package jp.co.nlj.ttmi.service;

import com.next.logistic.model.SystemLogin;
import com.next.logistic.model.UserLogin;
import jp.co.nlj.ttmi.dto.authen.response.TokenResponseDTO;

/**
 * <PRE>
 * 認証サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface AuthService {

    TokenResponseDTO loginUser(UserLogin userLogin);

    TokenResponseDTO loginSystem(SystemLogin systemLogin);

    TokenResponseDTO refreshToken(String refreshToken);
}
