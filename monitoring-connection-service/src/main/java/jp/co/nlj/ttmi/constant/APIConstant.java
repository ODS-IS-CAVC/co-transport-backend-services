package jp.co.nlj.ttmi.constant;

/**
 * <PRE>
 * API定数クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";
    public static final String API_KEY = "apiKey";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    public static final String DATA_CHANNEL_CLIENT_ID = "TestApUser_115 ";
    public static final String DATA_CHANNEL_CLIENT_SECRET = "WmWZ2vW91KpzPK3xGPnFEM7C4aw6XfQq ";
    public static final int CACHE_EXPIRED_TOKEN = 30;
    public static final String CACHE_KEY_TOKEN = "service-ttmi-data-channel-access-token";
    public static final String DATA_MODEL_TYPE = "test1";

    public static final class AUTH {

        public static final String PREFIX = "/auth";
        public static final String LOGIN = PREFIX + "/login";
        public static final String CLIENT = PREFIX + "/client";
        public static final String REFRESH = PREFIX + "/refresh";
    }

    public static final String VERIFY_TOKEN = "api/v2/systemAuth/token";

    public static final class TrackBySipReq {

        public static final String PREFIX = API + "/track/sip";

        public static final String DATA_CHANNEL_PREFIX = "/trackBySIP";
    }

    public static final class VehicleIncident {

        public static final String PREFIX = API + "/vehicle_incident_info";
    }

    public static final class OracleApi {

        public static final String PREFIX = API + "/zl_web";
        public static final String SEND_DATA = "/send_data";
        public static final String UPDATE_TIME = "/update_time";
    }
}
