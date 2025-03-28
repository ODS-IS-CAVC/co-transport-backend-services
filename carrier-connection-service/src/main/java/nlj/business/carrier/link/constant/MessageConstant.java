package nlj.business.carrier.link.constant;

/**
 * <PRE>
 * メッセージ定数.<BR>
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
        public static final String VALID_PHONE_NUMBER = "valid_phone_number";
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
        public static final String VALID_REFRIGERATION_UNIT_MIN = "valid_refrigeration_unit_min";
        public static final String VALID_REFRIGERATION_UNIT_MAX = "valid_refrigeration_unit_max";
        public static final String VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_PACKAGE_CODE = "valid_hazardous_material_vehicle_type_package_code";
        public static final String VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_VOLUME = "valid_hazardous_material_vehicle_type_volume";
        public static final String VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_GRAVITY = "valid_hazardous_material_vehicle_type_gravity";
        public static final String VALID_MAX_LOAD_QUANTITY_PACKAGE_NAME_KANJI = "valid_max_load_quantity_package_name_kanji";
        public static final String VALID_MIN_MAX_VALUE = "valid_min_max_value";
        public static final String VALID_MAX_VALUE = "valid_max_value";
        public static final String MSG_FN_STAS_CD_TYPE = "valid_msg_fn_stas_cd_type";
        public static final String TRSP_NORMAL_ABNORMAL = "valid_msg_trsp_normal_abnormal";

        public static final String TRANS_INTRUCTION_NOT_FOUND = "soa.code.trsp_instruction_not_found";

        public static final String SHIPPER_NOT_FOUND = "soa.code.shipper_info_not_found";

        public static final String CARRIER_NOT_FOUND = "soa.code.carrier_info_not_found";
    }

    public static final class VehicleInfo {

        public static final String VEHICLE_INFO_ALREADY_EXISTS = "vehicle_info_already_exists";
        public static final String VEHICLE_INFO_NOT_FOUND = "vehicle_info_not_found";
    }

    public static final class VehicleSearch {

        public static final String VEHICLE_SEARCH_ERR_001 = "vehicle_search_err_001";
    }

    public static final class TrspPlanItem {

        public static final String TRSP_PLAN_ITEM_ERR_001 = "soa.code.trsp_plan_item_err_001";
        public static final String TRSP_PLAN_ITEM_NOT_FOUND = "soa.code.trsp_plan_item_not_found";
        public static final String REQ_TRSP_PLAN_ITEM_NOT_FOUND = "soa.code.req_trsp_plan_item_not_found";
    }

    public static final class ShipperTrspCapacity {

        public static final String SHIPPER_TRSP_CAPACITY_PARAM = "soa.code.shipper_trsp_capicity_param";
    }

    public static final class TrspAbilityLineItem {

        public static final String RSP_ABILITY_LINE_ITEM_ERR_400 = "trsp_ability_line_item_err_400";
        public static final String RSP_ABILITY_LINE_ITEM_EXIST = "trsp_ability_line_item_exist";
        public static final String RSP_ABILITY_LINE_ITEM_NOT_EXIST = "trsp_ability_line_item_not_exist";
    }

    public static final class CarInfoMessage {

        public static final String CAR_INFO_EXIST = "car_info_exist";
        public static final String CAR_INFO_NOT_EXIST = "car_info_not_exist";
    }

    public static final class TrspAbilityMsgInfo {

        public static final String MSG_INFO_REQUIRED_FIELDS = "msg_info_required_fields";
    }

    public static final class TrspAbilityLineItemMsg {

        public static final String REGISTRATION_SUCCESS = "登録が完了しました。";
    }

    public static final class CnsLineItemByDate {

        public static final String CNS_LINE_ITEM_BY_DATE_NOT_FOUND = "cns_line_item_by_date_not_found";
    }

    public static final class ReqCnsLineItem {

        public static final String REQ_CNS_LINE_ITEM_NOT_FOUND = "req_cns_line_item_not_found";
    }

    public static final class ReqTrspPlanMsgInfo {

        public static final String REQ_TRSP_PLAN_MSG_INFO_NOT_FOUND = "req_trsp_plan_msg_info_not_found";
    }

    public static final class VehicleAvbResourceItem {

        public static final String VEHICLE_AVB_RESOURCE_ITEM_NOT_FOUND = "vehicle_avb_resource_item_not_found";
    }

    public static final class VehicleAvbResource {

        public static final String VEHICLE_AVB_RESOURCE_NOT_FOUND = "vehicle_avb_resource_not_found";
    }

    public static final class VehicleDiagramItemTracking {

        public static final String INCIDENT_LABEL = "遅延発生";
        public static final String STOP_LABEL = "運行停止";
        public static final String INCIDENT_MESSAGE_TRUE = "状態 : 停止。停止位置：%s,%s";
        public static final String INCIDENT_MESSAGE_WITH_DELAY = "遅延が発生しました。予定到着時刻は%d時%d分です";
        public static final String INCIDENT_MESSAGE_ID = "(イベントID：%s)";
    }

    public static final class VehicleDiagramMobilityHub {

        public static final String ERROR_INSERT_UPDATE_VEHICLE_DIAGRAM_MOBILITY_HUB = "error_insert_update_vehicle_diagram_mobility_hub";
    }

    public static final class ValidateCommonMessage {

        public static final String MESSAGE_ERROR_1 = "soa.code.delay_30_minutes";
        public static final String MESSAGE_ERROR_2 = "soa.code.stop_vehicle_due_to_rainfall";
        public static final String MESSAGE_ERROR_3 = "soa.code.stop_vehicle_due_to_wind_speed";
        public static final String MESSAGE_ERROR_4 = "soa.code.delay_am_vehicle_due_to_traffic_jam";
        public static final String MESSAGE_ERROR_5 = "soa.code.delay_all_day_vehicle_due_to_traffic_jam";
    }

    public static final class SemiDynamicInfo {

        public static final int ERROR_CODE_0 = 1000;
        public static final int ERROR_CODE_1 = 1001;
        public static final int ERROR_CODE_2 = 1002;
        public static final int ERROR_CODE_3 = 1003;
        public static final int ERROR_CODE_4 = 1004;
        public static final int ERROR_CODE_5 = 1005;
    }
}
