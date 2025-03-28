package nlj.business.auth.service;

import com.next.logistic.model.SystemLogin;
import com.next.logistic.model.UserLogin;
import nlj.business.auth.dto.auth.response.TokenResponseDTO;

/**
 * <PRE>
 * 認証サービスインターフェース<BR>
 * </PRE>
 * 
 * @author Next Logistics Japan
 */
public interface AuthService {

    TokenResponseDTO loginUser(UserLogin userLogin);

    TokenResponseDTO loginSystem(SystemLogin systemLogin);

    TokenResponseDTO refreshToken(String refreshToken);
}
