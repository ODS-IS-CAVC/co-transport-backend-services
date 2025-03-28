package jp.co.nlj.gateway.constant;

/**
 * APIConstantクラスは、APIの定数を定義するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";
    public static final String STATUS_BAD_REQUEST_CODE = "400";
    public static final String STATUS_BAD_REQUEST_DESCRIPTION = "リクエスト自体に問題がある場合";
    public static final String STATUS_SERVER_ERROR_CODE = "500";
    public static final String STATUS_SERVER_ERROR_DESCRIPTION = "NG";
    public static final String STATUS_OK_CODE = "200";
    public static final String STATUS_OK_DESCRIPTION = "OK";
    public static final String API_KEY = "apiKey";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int CACHE_EXPIRED_TOKEN = 30;
    public static final String CACHE_KEY_TOKEN = "data-channel-access-token";

    public static final class SemiDynamicInfo {

        public static final String PREFIX = API + "/semiDynamicInfo";
        public static final String GET_SEMI_DYNAMIC_INFO_SUMMARY = "準動的情報取得";
        public static final String GET_SEMI_DYNAMIC_INFO_DESCRIPTION = "準動的情報取得";
        public static final String GET_SEMI_DYNAMIC_INFO_PARAM = "encodedData";
    }

    public static final class VehicleIncidentInfo {

        public static final String PREFIX = API + "/vehicleIncidentInfo";
        public static final String REGISTER_VIHICLE_INCIDENT_INFO_SUMMARY = "車両インシデント情報登録";
        public static final String REGISTER_VIHICLE_INCIDENT_INFO_DESCRIPTION = "車両インシデント情報登録";
    }

    public static final class MobilityHub {

        public static final String PREFIX = API + "/Resources";
        public static final String RESERVATION_ID = "keyFilter";
        public static final String RESERVE_MOBILITY_HUB_SUMMARY = "MobilityHubを予約する";
        public static final String RESERVE_MOBILITY_HUB_DESCRIPTION = "MobilityHubを予約する";
        public static final String CANCEL_RESERVE_MOBILITY_HUB_SUMMARY = "MobilityHubの予約をキャンセルする";
        public static final String CANCEL_RESERVE_MOBILITY_HUB_DESCRIPTION = "MobilityHubの予約をキャンセルする";
    }

    public static final class TrackBySip {

        public static final String PREFIX = API + "/track/sip";
        public static final String CREATE_NEW_TRACK_BY_SIP_SUMMARY = "CreateTrackBySIP";
        public static final String CREATE_NEW_TRACK_BY_SIP_DESCRIPTION = "Creates a new track resource by SIP format." +
            " When 200, no response body is returned. |\n" + "        SIP標準メッセージレイアウトの運送依頼請け情報を受取るAPIです。\n"
            +
            "        運送依頼請け情報の必須項目はすべて受け取れるようになっています。REQUEST BODY SCHEMAに記した以外の項目は無視されます。\n"
            +
            "        正常に処理された場合、レスポンスボディは空となります。";
        public static final String UPDATE_TRACK_BY_SIP_SUMMARY = "UpdateTrackBySIP";
        public static final String UPDATE_TRACK_BY_SIP_DESCRIPTION = "update a new track resource by SIP format. When" +
            " 200, no response body is returned. |\n" + "        SIP標準メッセージレイアウトの運送依頼請け情報を受取るAPIです。\n"
            +
            "        運送依頼請け情報の必須項目はすべて受け取れるようになっています。REQUEST BODY SCHEMAに記した以外の項目は無視されます。\n"
            +
            "        正常に処理された場合、レスポンスボディは空となります。";
        public static final String STATUS_CODE_UPDATE_SUCCESS = "200";
        public static final String STATUS_CODE_UPDATE_SUCCESS_DESCRIPTION = "Request processed successfully with no " +
            "response body.";
        public static final String STATUS_CODE_CREATED = "201";
        public static final String STATUS_CODE_CREATED_DESCRIPTION = "Request processed successfully with no response" +
            " body.";
        public static final String STATUS_CODE_BAD_REQUEST = "400";
        public static final String STATUS_CODE_BAD_REQUEST_DESCRIPTION = "Bad Request - The server could not " +
            "understand the request due to invalid syntax.";
        public static final String STATUS_CODE_UNAUTHORIZED = "401";
        public static final String STATUS_CODE_UNAUTHORIZED_DESCRIPTION = "Unauthorized - Authentication is required " +
            "and has failed or has not yet been provided.";
        public static final String STATUS_CODE_FORBIDDEN = "403";
        public static final String STATUS_CODE_FORBIDDEN_DESCRIPTION = "Forbidden - The client does not have access " +
            "rights to the content.";
        public static final String STATUS_CODE_NOT_FOUND = "404";
        public static final String STATUS_CODE_NOT_FOUND_DESCRIPTION = "Not Found - The server could not find the " +
            "requested resource.";
        public static final String STATUS_CODE_INTERNAL_SERVER_ERROR = "500";
        public static final String STATUS_CODE_INTERNAL_SERVER_ERROR_DESCRIPTION = "Internal Server Error - The " +
            "server encountered an unexpected condition that prevented it from fulfilling the request.";
    }

}
