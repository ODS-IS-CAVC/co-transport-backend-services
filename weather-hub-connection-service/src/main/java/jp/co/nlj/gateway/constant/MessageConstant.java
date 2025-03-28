package jp.co.nlj.gateway.constant;

/**
 * MessageConstantクラスは、メッセージの定数を定義するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MessageConstant {

    public static final class System {

        public static final String SYSTEM_ERR_001 = "system_err_001";
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
        public static final String VALID_NUMERIC = "valid_numeric";
        public static final String VALID_TRACTOR_TYPE = "valid_tractor_type";
        public static final String VALID_CORRECTION_CODE_CANCEL_REASON = "valid_correction_code_cancel_reason";
        public static final String VALID_PACKING_CODE = "valid_packing_code";
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
}
