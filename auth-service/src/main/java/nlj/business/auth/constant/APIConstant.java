package nlj.business.auth.constant;

/**
 * <PRE>
 * API定数クラス<BR>
 * <PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String VERIFY_TOKEN = "api/v2/systemAuth/token";

    public static final class AUTH {

        public static final String PREFIX = "/auth";
        public static final String LOGIN = PREFIX + "/login";
        public static final String CLIENT = PREFIX + "/client";
        public static final String REFRESH = PREFIX + "/refresh";
    }
}
