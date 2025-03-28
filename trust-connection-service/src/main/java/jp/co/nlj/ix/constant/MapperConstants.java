package jp.co.nlj.ix.constant;

/**
 * <PRE>
 * マッパー定数クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MapperConstants {

    public static final String TO_DEMO_ENTITY = "toDemoEntity";
    public static final String TO_DEMO_RESPONSE = "toDemoResponse";
    public static final String TO_DEMO_RESPONSES = "toDemoResponses";
    public static final String TO_VEHICLE_INFO = "toVehicleInfo";
    public static final String TO_MAX_CARRYING_CAPACITY = "toMaxCarryingCapacity";
    public static final String TO_HAZARDOUS_MATERIAL_INFO = "toHazardousMaterialInfo";
    public static final String TO_VEHICLE_INFO_DTO = "toVehicleInfoDTO";
    public static final String TO_MOTAS_INFO_DTO = "toMotasInfoDTO";
    public static final String TO_VEHICLE_DETAILS_DTO = "toVehicleDetailsDTO";
    public static final String TO_MAX_CARRYING_CAPACITY_DTO = "toMaxCarryingCapacityDTO";
    public static final String TO_HAZARDOUS_MATERIAL_INFO_DTO = "toHazardousMaterialInfoDTO";
    public static final String STRING_TO_LOCAL_DATE = "stringToLocalDate";
    public static final String DATE_TO_STRING = "dateToString";
    public static final String STRING_TO_LOCAL_TIME = "stringToLocalTime";
    public static final String TIME_TO_STRING = "timeToString";
    public static final String INTEGER_TO_BIGDECIMAL = "integerToBigDecimal";
    public static final String NUMBER_TO_INTEGER = "numberToInteger";
    public static final String TRIM_VALUE = "trim_value";

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
    }

    public static final class TrspPlanItem {

        public static final String TRSP_PLAN_ITEM_ERR_001 = "soa.code.trsp_plan_item_err_001";
        public static final String REQ_TRSP_PLAN_ITEM_NOT_FOUND = "soa.code.req_trsp_plan_item_not_found";
    }

    public static final class TransOrderError {

        public static final String PROPOSE_NOT_NULL = "propose_not_null";
        public static final String USER_COMPANY_NOT_NULL = "user_company_not_null";
        public static final String TRANS_ORDER_NOT_NULL = "trans_order_not_null";
        public static final String TRANS_PLAN_NOT_NULL = "trans_plan_not_null";
        public static final String TRANS_PLAN_ITEM_NOT_NULL = "trans_plan_item_not_null";
        public static final String VEHICLE_DIAGRAM_ITEM_NOT_NULL = "vehicle_diagram_item_not_null";
        public static final String VEHICLE_AVAILABILITY_RESOURCE_ITEM_NOT_NULL = "vehicle_availability_resource_item_not_null";
        public static final String VEHICLE_AVAILABILITY_RESOURCE_ITEM_NOT_NULL_BY_STATUS = "vehicle_availability_resource_item_not_null_by_status";
        public static final String VEHICLE_AVAILABILITY_RESOURCE_ITEM_NOT_NULL_BY_STATUS_SHIPPER = "vehicle_availability_resource_item_not_null_by_status_shipper";
        public static final String CNS_LINE_ITEM_BY_DATE_NOT_NULL = "cns_line_item_by_date_not_null";
        public static final String TRANS_PLAN_ID_NOT_NULL = "trans_plan_id_not_null";
        public static final String TRANS_PLAN_ID_NOT_FOUND_IN_CNS_LINE_ITEM_BY_DATE = "trans_plan_id_not_found_in_cns_line_item_by_date";
        public static final String TRANS_PLAN_ID_NOT_FOUND_IN_CNS_LINE_ITEM_BY_DATE_STATUS = "trans_plan_id_not_found_in_cns_line_item_by_date_status";
        public static final String OPERATION_ID_NOT_FOUND_IN_VEHICLE_AVAILABILITY_RESOURCE_ITEM = "operation_id_not_found_in_vehicle_availability_resource_item";
    }
}
