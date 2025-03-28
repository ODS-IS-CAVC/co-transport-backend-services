package jp.co.nlj.verify.service;

import jp.co.nlj.verify.dto.auth.response.VerifyTokenResponseDTO;

/**
 * <PRE>
 * 認証サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface AuthService {

    VerifyTokenResponseDTO verifyToken(String idToken);
}
