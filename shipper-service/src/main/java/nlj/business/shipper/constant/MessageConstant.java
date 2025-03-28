package nlj.business.shipper.constant;

/**
 * <PRE>
 * メッセージ定数クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MessageConstant {

    public static final class SystemMessage {

        public static final String SYSTEM_ERR_001 = "system_err_001";
    }

    public static final class Validate {

        public static final String VALID_NOT_NULL = "valid_not_null";
        public static final String VALID_MIN_LENGTH = "valid_min_length";
        public static final String VALID_MAX_LENGTH = "valid_max_length";
        public static final String VALID_PRECISION = "valid_precision";
        public static final String VALID_SCALE = "valid_scale";
        public static final String VALID_POSITIVE_NUMBER = "valid_positive_number";
        public static final String VALID_DATE_FORMAT = "valid_date_format";
        public static final String VALID_START_DATE_AFTER_END_DATE = "start_date_after_end_date";
        public static final String VALID_START_TIME_AFTER_END_TIME = "start_time_after_end_time";
        public static final String VALID_NUMERIC = "valid_numeric";
        public static final String VALID_ENUM_VALUE = "valid_enum_value";
        public static final String VALID_FILE_TYPE = "valid_file_type";
        public static final String VALID_FILE_SIZE = "valid_file_size";
        public static final String VALID_MISSING_REQUIRED_COLUMNS = "valid_missing_required_columns";
        public static final String VALID_FILE_READ = "valid_file_read";
        public static final String VALID_FILE_DATA = "valid_file_data";
        public static final String VALID_CARGO_STATUS = "soa.code.valid_cargo_status";
        public static final String VALID_FILE_IMPORT = "soa.code.valid_file_import";
    }

    public static final class APIResponses {

        public static final String SUCCESS_CODE = "200";
        public static final String SUCCESS_MSG = "車輛マスタを正常に登録しました。";
        public static final String BAD_REQUEST_CODE = "400";
        public static final String BAD_REQUEST_MSG = "リクエスト自体に問題がある場合";
        public static final String ERROR_CODE = "500";
        public static final String ERROR_MSG = "システムの内部にてエラーが発生している場合";
    }

    public static final class TransportPlanMessage {

        public static final String TRANSPORT_PLAN_NOT_FOUND = "transport_plan_not_found";
        public static final String TRANSPORT_PLAN_EXIST = "transport_plan_exist";
        public static final String TRANSPORT_PLAN_DELETE_FAILED_MATCHING_EXISTS = "transport_plan_delete_failed_matching_exists";
        public static final String CNS_LINE_ITEM_BY_DATE_HAVE_IN_TRANS_ORDER = "soa.code.cns_line_item_by_date_have_in_trans_order";
    }
}
