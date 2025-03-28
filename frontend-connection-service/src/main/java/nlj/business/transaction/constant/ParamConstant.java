package nlj.business.transaction.constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * パラメータ定数。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ParamConstant {

    public static final String DEFAULT_PAGE_NO = "1";
    public static final String DEFAULT_PAGE_LIMIT = "5";

    public static final String TRSP_STRT_TXT = "trsp_op_strt_area_line_one_txt";
    public static final String TRSP_END_TXT = "trsp_op_end_area_line_one_txt";

    public static final String MAX_TRSP_STRT_DATE = "max_trsp_op_date_trm_strt_date";
    public static final String MIN_TRSP_STRT_DATE = "min_trsp_op_date_trm_strt_date";
    public static final String MAX_TRSP_END_DATE = "max_trsp_op_date_trm_end_date";
    public static final String MIN_TRSP_END_DATE = "min_trsp_op_date_trm_end_date";
    public static final String MAX_TRSP_STRT_TIME = "max_trsp_op_plan_date_trm_strt_time";
    public static final String MIN_TRSP_STRT_TIME = "min_trsp_op_plan_date_trm_strt_time";
    public static final String MAX_TRSP_END_TIME = "max_trsp_op_plan_date_trm_end_time";
    public static final String MIN_TRSP_END_TIME = "min_trsp_op_plan_date_trm_end_time";
    public static final String ADVANCED_CONDITIONS = "advanced_conditions";
    public static final String SHIPPER_CID = "shipper_cid";
    public static final String CARRIER_CID = "carrier_cid";
    public static final String CID = "cid";
    public static final Long _72H = 72L;
    public static final Long _3H = 3L;
    public static final Long _2H = 2L;
    public static final Long _1H = 1L;
    public static final String UnknownError = "不明なエラー";

    public static class TransportAbility {

        public static final String ID = "id";
        public static final String SORT = "sort";
        public static final String ORDER = "order";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String PAGE = "page";
        public static final String LIMIT = "limit";
        public static final String ORDER_ID = "order_id";
    }

    public static class TransportPlan {

        public static final String ID = "id";
        public static final String SORT = "sort";
        public static final String ORDER = "order";
        public static final String TRANSPORT_PLAN_ID = "transport_plan_id";
        public static final String TRANSPORT_PLAN_ITEM_ID = "transport_plan_item_id";
        public static final String PAGE = "page";
        public static final String LIMIT = "limit";
    }

    public static class TransPreOrder {

        public static final String PAGE = "page";
        public static final String LIMIT = "limit";
    }

    public static class Transaction {

        public static final String ID = "id";
        public static final String TARGET = "target";
    }

    public static class TransOrderSearchStatus {

        public static final String PROPOSE = "propose";
        public static final String APPROVAL = "approval";
        public static final String CONTRACT = "contract";
        public static final String PAYMENT = "payment";
        public static final String TRANSPORT = "transport";
        public static final String COMPLETE = "complete";
        public static final String CONFIRM = "confirm";

        public static List<String> getListStatus() throws IllegalAccessException {
            Class<?> targetClass = TransOrderSearchStatus.class;
            Field[] fields = targetClass.getDeclaredFields();
            List<String> values = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(null);
                if (value instanceof String) {
                    values.add(String.valueOf(value));
                }
            }
            return values;
        }
    }

    public class OrderStatus {

        public static final String PROPOSE = "propose";
        public static final String APPROVAL = "approval";
        public static final String CONTRACT = "contract";
        public static final String PAYMENT = "payment";
        public static final String TRANSPORT = "transport";
        public static final String COMPLETE = "complete";
        public static final String CONFIRM = "confirm";

        public static final int PROPOSE_110 = 110;
        public static final int PROPOSE_111 = 111;
        public static final int PROPOSE_120 = 120;
        public static final int PROPOSE_121 = 121;
        public static final int APPROVAL_130 = 130;
        public static final int APPROVAL_131 = 131;
        public static final int CONTRACT_140 = 140;
        public static final int PAYMENT_150 = 150;
        public static final int TRANSPORT_160 = 160;
        public static final int COMPLETE_161 = 161;


    }

    public static final class Status {

        public static final int INITIALIZED = 0;
        public static final int MARKET = 1;
        public static final int AUTOMATIC_MATCHING = 2;
        public static final int AWAITING_CONFIRMATION = 3;
        public static final int CONTRACT = 4;
        public static final int PAYMENT = 5;
        public static final int TRANSPORT = 6;
        public static final int COMPLETED = 7;
        public static final String START = "start";
        public static final String END = "end";
        public static final int DIAGRAM_START = 2;
        public static final int DIAGRAM_END = 3;
        public static final String SUCCESS = "Success";
        public static final String FAIL = "Fail";
        public static final int WAITING_TO_START = 1;
    }

    public static final class MatchingInterval {

        public static final String SHIPPER = "shipper";
        public static final String CARRIER = "carrier";
        public static final String CARRIER2 = "carrier2";
        public static final String CARRIER2_EMERGENCY = "carrier2_emergency";
    }

    public static final class TransType {

        public static final Integer CARRIER_SHIPPER = 0;
        public static final Integer CARRIER_CARRIER = 1;

        public static List<Integer> getListStatus() {
            return List.of(CARRIER_CARRIER, CARRIER_SHIPPER);
        }
    }

    public static final class ZLWeb {

        public static final String ZLWEB_URL = "/api/v1/zl_web/send_data";
        public static final String UPDATE_TIME = "/api/v1/zl_web/update_time";
        public static final String MANAGEMENT_NUMBER_PREFIX = "NLJ";
    }

    public static final class AdvanceStatus {

        public static final String PROPOSE = "propose";
        public static final String CONTRACT = "contract";
        public static final String DECISION_TRANSPORT = "decisionTransport";
        public static final String TRANSPORT = "transport";
        public static final String PAYMENT = "payment";
        public static final String COMPLETE = "complete";

        public static final String LIST_STATUS_PROPOSE = "(110, 111, 120, 121, 132, 133, 210, 211, 220, 221, 232, 233)";
        public static final String LIST_STATUS_CONTRACT = "(130, 131, 140, 230, 231, 240)";
        public static final String LIST_STATUS_DECISION_TRANSPORT = "(151, 251)";
        public static final String LIST_STATUS_TRANSPORT = "(160, 260)";
        public static final String LIST_STATUS_PAYMENT = "(150, 250)";
        public static final String LIST_STATUS_COMPLETE = "(161, 261)";
    }

    public static final class WeatherInformation {

        public static final int ERROR_CODE_0 = 1000;
        public static final int ERROR_CODE_1 = 1001;
        public static final int ERROR_CODE_2 = 1002;
        public static final int ERROR_CODE_3 = 1003;
        public static final int ERROR_CODE_4 = 1004;
        public static final int ERROR_CODE_5 = 1005;
        public static final int ERROR_CODE_6 = 1006;
        public static final int ERROR_CODE_25 = 1025;
        public static final int ERROR_CODE_26 = 1026;
        public static final String START_STATUS = "start";
        public static final String END_STATUS = "end";
        public static final String LABEL_DELAY = "遅延発生";
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

        public static final int INCIDENT_TYPE_WEATHER_STOP = 2;
        public static final int INCIDENT_TYPE_TRAFFIC_STOP = 3;
    }
}
