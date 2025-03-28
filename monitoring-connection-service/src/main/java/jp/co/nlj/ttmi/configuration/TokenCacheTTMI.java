package jp.co.nlj.ttmi.configuration;

/**
 * <PRE>
 * TTMIトークンキャッシュクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class TokenCacheTTMI {

    private static String accessToken;
    private static long expirationTime;

    public static String getAccessToken() {
        return TokenCacheTTMI.accessToken;
    }

    public static void setTokenInfo(String token, long expiresIn) {
        TokenCacheTTMI.accessToken = token;
        TokenCacheTTMI.expirationTime = System.currentTimeMillis() + (expiresIn * 1000);
    }

    public static boolean isTokenValid() {
        return TokenCacheTTMI.accessToken != null && System.currentTimeMillis() < TokenCacheTTMI.expirationTime;
    }
}
