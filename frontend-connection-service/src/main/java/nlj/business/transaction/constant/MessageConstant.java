package nlj.business.transaction.constant;

/**
 * <PRE>
 * メッセージ定数。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MessageConstant {

    public static final class Validate {

        public static final String VALID_PARAM = "valid_param";
        public static final String VALID_NOT_NULL = "valid_not_null";
        public static final String VALID_MIN_LENGTH = "valid_min_length";
        public static final String VALID_MAX_LENGTH = "valid_max_length";
        public static final String VALID_PRECISION = "valid_precision";
        public static final String VALID_SCALE = "valid_scale";
        public static final String VALID_POSITIVE_NUMBER = "valid_positive_number";
        public static final String VALID_DATE_FORMAT = "valid_date_format";
        public static final String VALID_START_DATE_AFTER_END_DATE = "start_date_after_end_date";
        public static final String VALID_TIME_FORMAT = "valid_time_format";
        public static final String VALID_DATE_TIME_MAX_MIN = "valid_date_time_format_max_min";
        public static final String VALID_NUMBER = "valid_number";
        public static final String TRSP_PLAN_ITEM_404 = "trsp_plan_item_404";
        public static final String TRANS_MATCHING_404 = "trans_matching_404";
        public static final String TRANS_MATCHING_PRE_ORDER_EXIST = "trans_matching_pre_order_exist";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_404 = "vehicle_diagram_item_trailer_404";
        public static final String VALID_NOT_EQUAL_FIELD = "valid_not_equal_field";
        public static final String VALID_SHIPPER_TRANS_SEARCH_STATUS = "valid_shipper_trans_search_status";
        public static final String VALID_INTEGER_ARRAY = "valid_integer_array";
        public static final String NOT_FOUND = "not_found";
        public static final String ORDER_ID_PACKAGE_NOT_FOUND = "order_id_package_not_found";
        public static final String OPERATOR_ID_NOT_MATCH_RECORD = "operator_id_not_match_record";
        public static final String TRANS_ORDER_NOT_FOUND = "trans_order_not_found";
        public static final String LAUNCHED = "launched";
        public static final String VALID_ALLOW_VALUE = "valid_allow_value";
        public static final String VALID_TRANS_TYPE = "valid_trans_type";
    }

    public static final class System {

        public static final String SYSTEM_ERR_001 = "system_err_001";
        public static final String NOT_FOUND = "not_found";
        public static final String TRANS_ORDER_NOT_EXIST = "soa.code.trans_order_not_exist";
    }

    public static final class ValidateCommonMessage {

        public static final String MESSAGE_ERROR_1 = "soa.code.delay_30_minutes";
        public static final String MESSAGE_ERROR_2 = "soa.code.stop_vehicle_due_to_rainfall";
        public static final String MESSAGE_ERROR_3 = "soa.code.stop_vehicle_due_to_wind_speed";
        public static final String MESSAGE_ERROR_4 = "soa.code.delay_vehicle_due_to_traffic_jam";
    }

    public static final class WeatherInformation {

        public static final int ERROR_CODE_0 = 1000;
        public static final int ERROR_CODE_1 = 1001;
        public static final int ERROR_CODE_2 = 1002;
        public static final int ERROR_CODE_3 = 1003;
        public static final int ERROR_CODE_4 = 1004;
        public static final int ERROR_CODE_5 = 1005;

        public static final String STATUS_ERROR = "error";
        public static final String STATUS_SUCCESS = "success";
        // 1000
        public static final String MESSAGE_ERROR_0 = "正常走行可能";
        // 1001
        public static final String MESSAGE_ERROR_1 = "悪天候により30分遅延が発生する可能性があります。輸送の安全を確保した上、注意して走行してください。";
        // 1002
        public static final String MESSAGE_ERROR_2 = "暴風や豪雨が発生する可能性があります。輸送を一時中止し、天候回復まで待機してください。３時間後、天候が回復見込みです。";
        // 1003
        public static final String MESSAGE_ERROR_3 = "異常気象の状況が発生する可能性があります。輸送計画のキャンセルし、日程の変更をしてください。";
        // 1004
        public static final String MESSAGE_ERROR_4 = "通行止めが発生しました。４時間の遅れが発生する可能性があります。輸送計画の変更を検討してください。";
        // 1005
        public static final String MESSAGE_ERROR_5 = "通行止めが発生しました。輸送計画の変更を検討してください。";
    }
}