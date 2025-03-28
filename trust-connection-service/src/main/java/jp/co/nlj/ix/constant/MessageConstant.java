package jp.co.nlj.ix.constant;

/**
 * <PRE>
 * MessageConstantクラスは、メッセージの定数を定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MessageConstant {

    public static final class System {

        public static final String SYSTEM_ERR_001 = "system_err_001";
    }

    public static final class APIResponses {

        public static final String SUCCESS_CODE = "201";
        public static final String SUCCESS_MSG = "Request processed successfully with no response body.";
        public static final String BAD_REQUEST_CODE = "400";
        public static final String BAD_REQUEST_MSG = "Bad Request - The server could not understand the request";
        public static final String UNAUTHORIZED_CODE = "401";
        public static final String UNAUTHORIZED_MSG = "Unauthorized - Authentication is required and has failed or has not yet been provided.";
        public static final String FORBIDDEN_CODE = "403";
        public static final String FORBIDDEN_MSG = "Forbidden - The client does not have access rights to the content.";
        public static final String NOT_FOUND_CODE = "404";
        public static final String NOT_FOUND_MSG = "Not Found - The server could not find the requested resource.";
        public static final String SERVER_ERROR_CODE = "500";
        public static final String SERVER_ERROR_MSG = "Internal Server Error - The server encountered an unexpected condition that prevented it from fulfilling the request.";
        public static final String ERROR_CODE = "500";
        public static final String ERROR_MSG = "システムの内部にてエラーが発生している場合";
        public static final String REQUEST_SUCCESS_CODE = "200";
        public static final String BAD_REQUEST_MSG_JA = "リクエスト自体に問題がある場合";
        public static final String SERVER_ERROR_MSG_JA = "システムの内部にてエラーが発生している場合";
        public static final String GET_VEHICLE_SUCCESS_MSG = "指定した車輌種別に基づいて、車輛マスタを正常に取得しました。";
        public static final String REGISTER_VEHICLE_SUCCESS_MSG = "車輛マスタを正常に登録しました。";
        public static final String DELETE_VEHICLE_SUCCESS_MSG = "車輛マスタを正常に削除しました。";
        public static final String SUCCESS_CODE_200 = "200";
        public static final String SUCCESS_MSG_JA = "車輛マスタを正常に登録しました。";
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
        public static final String VALID_NUMERIC = "valid_numeric";
        public static final String VALID_TRACTOR_TYPE = "valid_tractor_type";
        public static final String VALID_CORRECTION_CODE_CANCEL_REASON = "valid_correction_code_cancel_reason";
        public static final String VALID_PACKING_CODE = "valid_packing_code";
        public static final String VALID_HYPHEN = "valid_hyphen";
        public static final String VALID_INTEGER_FORMAT = "valid_integer_format";
        public static final String VALID_MIN_VALUE = "valid_min_value";
        public static final String VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE = "valid_hazardous_material_vehicle_type";
        public static final String VALID_POWER_GATE_TYPE = "valid_power_gate_type";
        public static final String VALID_CRANE_EQUIPPED_TYPE = "valid_crane_equipped_type";
        public static final String VALID_MAX_LWR_TMP = "valid_max_lwr_tmp";
        public static final String VALID_WING_DOOR_TYPE = "valid_wing_door_type";
        public static final String VALID_REFRIGERATION_UNIT_TYPE = "valid_refrigeration_unit_type";
        public static final String VALID_LENGTH_FIELD = "valid_length_field";
        public static final String VALID_XTRACKING_FORMAT = "valid_xtracking_format";
        public static final String VALID_MIN_MAX_VALUE = "valid_min_max_value";
        public static final String OPERATION_NOTIFY_OPERATOR_ID_NOT_VALID = "operation_notify_operator_id_not_valid";
        public static final String OPERATION_NOTIFY_OPERATOR_ID_NOT_MATCH = "operation_notify_operator_id_not_match";
        public static final String VALID_BOOLEAN = "valid_boolean";
    }

    public static final class ShipperTrspCapacity {

        public static final String SHIPPER_TRSP_CAPACITY_PARAM = "soa.code.shipper_trsp_capicity_param";
    }

    public static final class ShipperOperationPlans {

        public static final String OPERATION_ID_NOT_MATCH = "soa.code.operation_id_not_match";
        public static final String OPERATION_ID_NOT_NULL_OR_EMPTY = "soa.code.operation_id_not_null";
        public static final String PROPOSE_ID_NOT_NULL_OR_EMPTY = "soa.code.propose_id_not_null";
        public static final String PROPOSE_ID_NOT_VALID = "soa.code.propose_id_not_valid";
        public static final String TRANS_ORDER_NOT_FOUND = "soa.code.trans_order_not_found";
    }

}
