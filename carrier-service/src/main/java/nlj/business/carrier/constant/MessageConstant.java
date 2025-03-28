package nlj.business.carrier.constant;

/**
 * <PRE>
 * メッセージ定数クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MessageConstant {

    public static final class System {

        public static final String SYSTEM_ERR_001 = "system_err_001";
        public static final String NOT_FOUND = "soa.code.not_found";
        public static final String VEHICLE_INFO_EXIST_IN_ALLOCATION = "soa.code.vehicle_info_exist_in_allocation";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER = "soa.code.vehicle_diagram_item_trailer_have_in_trans_order";
    }

    public static final class APIResponses {

        public static final String SUCCESS_CODE = "200";
        public static final String SUCCESS_MSG = "車輛マスタを正常に登録しました。";
        public static final String BAD_REQUEST_CODE = "400";
        public static final String BAD_REQUEST_MSG = "リクエスト自体に問題がある場合";
        public static final String ERROR_CODE = "500";
        public static final String ERROR_MSG = "システムの内部にてエラーが発生している場合";
    }

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
        public static final String VALID_START_TIME_AFTER_END_TIME = "start_time_after_end_time";
        public static final String VALID_HALF_WIDTH = "valid_half_width";
        public static final String VALID_FULL_WIDTH = "valid_full_width";
        public static final String VALID_DATE_BEFORE_CURRENT_DATE = "valid_date_before_current_date";
        public static final String VALID_CORRECTION_CODE = "valid_correction_code";
        public static final String VALID_CONTAIN_SPECIAL_CHARACTER = "valid_contain_special_character";
        public static final String VALID_ARRAY_OR_BOOLEAN = "valid_array_or_boolean";
        public static final String VALID_HOUR_FORMAT = "valid_hour_format";
        public static final String VALID_VEHICLE_TYPE = "valid_vehicle_type";
        public static final String VALID_VAN_BODY_TYPE = "valid_van_body_type";
        public static final String VALID_REPEAT_DAY = "soa.code.valid_repeat_day";
        public static final String VALID_IS_ROUND_TRIP = "soa.code.valid_is_round_trip";
        public static final String VALID_ORIGIN_TYPE = "soa.code.valid_origin_type";
        public static final String VALID_STATUS_DIAGRAM = "soa.code.valid_status_diagram";
        public static final String VALID_ROUND_TRIP_DIRECTION = "soa.code.valid_round_trip_direction";
        public static final String VALID_DELETE_FLAG_NOT_FOUND = "soa.code.valid_delete_flag_not_found";
        public static final String VALID_CARRIER_VEHICLE_TYPE = "soa.code.valid_carrier_vehicle_type";
        public static final String VALID_ARRAY_INTEGER = "soa.code.valid_array_integer";
        public static final String VALID_ASSIGN_TYPE = "valid_assign_type";
        public static final String VALID_MISSING_REQUIRED_COLUMNS = "valid_missing_required_columns";
        public static final String VALID_FILE_READ = "valid_file_read";
        public static final String VALID_FILE_DATA = "valid_file_data";
        public static final String VALID_CUT_OFF_PRICE = "soa.code.cut_off_price_not_valid";
        public static final String VALID_NUMBER_FORMAT = "valid_number_format";
        public static final String VALID_ENUM_VALUE = "valid_enum_value";
        public static final String VALID_FILE_IMPORT = "soa.code.valid_file_import";
    }

    public static final class ValidateCommonMessage {

        public static final String ERROR_CODE_1 = "soa.code.delay_30_minutes";
        public static final String ERROR_CODE_2 = "soa.code.stop_vehicle_due_to_rainfall";
        public static final String ERROR_CODE_3 = "soa.code.stop_vehicle_due_to_wind_speed";
    }

    public static final class VehicleDiagramMessage {

        public static final String VEHICLE_DIAGRAM_HEAD_NOT_FOUND = "vehicle_diagram_head_not_found";
        public static final String VEHICLE_DIAGRAM_NOT_FOUND = "vehicle_diagram_not_found";
        public static final String VEHICLE_DIAGRAM_ITEM_NOT_FOUND = "vehicle_diagram_item_not_found";
        public static final String VEHICLE_DIAGRAM_ITEM_CAN_NOT_UPDATE_PRIVATE = "vehicle_diagram_item_can_not_update_private";
        public static final String VEHICLE_DIAGRAM_ALLOCATION_NOT_FOUND = "vehicle_diagram_allocation_not_found";
        public static final String DATE_NOT_VALID = "date_not_valid";
        public static final String STATUS_NOT_VALID = "status_not_valid";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_NOT_FOUND = "vehicle_diagram_item_trailer_not_found";
        public static final String VEHICLE_DIAGRAM_VEHICLE_INFO_NOT_FOUND = "vehicle_diagram_vehicle_info_not_found";
        public static final String LOCATION_NOT_FOUND = "location_not_found";
    }

    public static final class SemiDymanicInfoMessage {

        public static final int ERROR_CODE_0 = 1000;
        public static final int ERROR_CODE_1 = 1001;
        public static final int ERROR_CODE_2 = 1002;
        public static final int ERROR_CODE_3 = 1003;
        public static final int ERROR_CODE_4 = 1004;
        public static final int ERROR_CODE_5 = 1005;
        public static final int ERROR_CODE_6 = 1006;
        public static final int ERROR_CODE_25 = 1025;
        public static final int ERROR_CODE_26 = 1026;
        public static final int STATUS_CODE_0 = 0;
        public static final int STATUS_CODE_1 = 1;
        public static final int STATUS_CODE_2 = 2;
        public static final String START_STATUS = "start";
        public static final String END_STATUS = "end";
        public static final String LABEL_DELAY = "遅延発生";
        public static final String STATUS_ERROR = "error";
        public static final String STATUS_SUCCESS = "success";
        // 1000
        public static final String MESSAGE_ERROR_0 = "登録が完了しました。";
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

    public static final class VehicleDiagramMobilityHub {

        public static final String ERROR_INSERT_UPDATE_VEHICLE_DIAGRAM_MOBILITY_HUB = "error_insert_update_vehicle_diagram_mobility_hub";
    }

    public static final class Email {

        // mail type
        public static final String MAIL_WEATHER_STOP = "WEATHER_STOP";
        public static final String MAIL_TRAFFIC_STOP = "TRAFFIC_STOP";
        public static final String MAIL_INCIDENT_STOP = "INCIDENT_STOP";
        public static final String MAIL_TIME_CHANGE = "TIME_CHANGE";
    }

    public static final class VehicleInfo {
        public static final String LICENSE_PLATE_NUMBER_OR_GIAI_EXIST = "license_plate_number_or_giai_exist";
        public static final int TEMPERATURE_RANGE_DRY = 1;
        public static final int TRAILER = 2;
        public static final int ORDER_STATUS_NULL_AND_TOTAL_COUNT_GREATER_0 = 1;
    }
}
