package jp.co.nlj.ix.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * ParamConstantクラスは、パラメータの定数を定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ParamConstant {

    public static final String SERVICE_NO = "service_no";
    public static final String SERVICE_NAME = "service_name";
    public static final String CAR_MAX_LOAD_CAPACITY1 = "car_max_load_capacity1_meas";
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
    public static final String CID = "cid";

    public static final String MSG_ID = "msg_id";
    public static final String MSG_INFOR_CLS_TYP_CD = "msg_info_cls_typ_cd";
    public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
    public static final List<String> VEHICLE_TYPE = Arrays.asList("1", "2", "3", "4", "5", "9");
    public static final List<String> FLATBED_VAN_BODY_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> CORRECTION_CODE = Arrays.asList(CorrectionCode.NEW, CorrectionCode.CHANGE,
        CorrectionCode.CANCEL);
    public static final List<String> TRACTOR = Arrays.asList("0", "1");
    public static final String REGEX_HALF_WIDTH = "^[\\u0020-\\u007E\\uFF61-\\uFFDC\\uFFA0-\\uFFBE\\uFFE8-\\uFFEE]+$";
    public static final String REGEX_FULL_WIDTH =
        "^(?!.*[\\uFF65-\\uFF9F])[\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\u3400-\\u4DBF\\u4E00-\\u9FFF\\uF900-\\uFAFF\\uFF00-\\uFFEF]+$";
    public static final List<String> HAZARDOUS_MATERIAL_VEHICLE_TYPE = Arrays.asList("0", "1");
    public static final List<String> POWER_GATE_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> CRANE_EQUIPPED_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> WING_DOOR_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> REFRIGERATION_UNIT_TYPE = Arrays.asList("1", "2", "9");
    public static final String VALIDATE_TIME_ERROR = "24";
    public static final int MIN_LENGTH_OF_TIME = 2;
    public static final String TRIM_SPACE_TWO_BYTE = "^[\\u3000]+|[\\u3000]+$";
    public static final String REGEX_REGISTRATION_NUMBER =
        "^[\\u4E00-\\u9FFF\\uFF21-\\uFF5A\\uFF10-\\uFF19\\u3000]{4,}[\\u4E00-\\u9FFF\\uFF21-\\uFF5A\\uFF10-\\uFF19\\u3000]{3,}[\\u3040-\\u309F\\uFF21-\\uFF5A\\u3000]{1}[\\uFF10-\\uFF19\\u3000]{4,}[\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF01-\\uFF9F]*$";
    public static final String REGEX_DIMENSION_CODE = "^(?:AC|BA|BG|BK|BL|BR|BT|BX|CA|CAP|CC|CM2|CM3|CM|CP|CS|CY|DM3|DR|DZN|ETC|FC|FT|G|GAL|GR|GRO|HR|IN|KG|KL|KM|KPC|L|LB|M2|M3|M|MG|ML|MM|OZ|PCE|PK|PL|PR|QT|RL|RN|SA|SET|ST|TB|TNE|UT|VA|VL|W|WH|YD)$";
    public static final String REQUIRED_VALUE = "1";
    public static final String REGEX_PACKAGE_CODE = "^(BA|BK|BO|CA|DR|CY|BX|PU|PA|EN|BGP|RL|BGC|CO|BGO|COF|BG|RP|CT|HA|CP|NA|PL|RB|CR|FC|BJ|MP|ETC)$";
    public static final int DIMENSION_PACKAGE_LENGTH = 3;

    public static String[] getPackingCode() throws IllegalAccessException {
        Class<?> packingCodeClass = PackingCode.class;
        Field[] fields = packingCodeClass.getDeclaredFields();
        String[] values = new String[fields.length];
        int index = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof String) {
                values[index++] = (String) value;
            }
        }
        return values;
    }

    public static final class CharacterSign {

        public static final String DOT = ".";
        public static final String NEGATIVE_SIGN = "-";
    }

    public static final class CorrectionCode {

        public static final String NEW = "1";
        public static final String CHANGE = "2";
        public static final String CANCEL = "3";
    }

    public static final class PackingCode {

        public static final String BARREL = "BA";
        public static final String BASKET = "BK";
        public static final String BOTTLE = "BO";
        public static final String CAN = "CA";
        public static final String DRUM = "DR";
        public static final String CYLINDER = "CY";
        public static final String BOX = "BX";
        public static final String TRAY = "PU";
        public static final String PAIL = "PA";
        public static final String ENVELOP = "EN";
        public static final String PAPER_BAG = "BGP";
        public static final String ROLL = "RL";
        public static final String CLOTH_BAG_BAG = "BGC";
        public static final String CONTAINER = "CO";
        public static final String POLY_BAG = "BGO";
        public static final String FLEXIBLE_CONTAINER = "COF";
        public static final String BAG = "BG";
        public static final String REAM_PACKAGING = "RP";
        public static final String CARTON = "CT";
        public static final String HANGER = "HA";
        public static final String CORRUGATED_BOX = "CP";
        public static final String BARE = "NA";
        public static final String PALLET = "PL";
        public static final String ROLL_BOX = "RB";
        public static final String CRATE = "CR";
        public static final String FOLDING_CONTAINER = "FC";
        public static final String BANJU = "BJ";
        public static final String UNPACKAGED = "MP";
        public static final String OTHER = "ETC";
    }

    public static final class DataModelType {

        public static final String TEST_1 = "test1";
    }

    public static final class TransOrderStatus {

        public static final List<Integer> TRANS_ORDER_STATUS_LIST = Arrays.asList(0, 1, 2, 3);
        public static final String SHIPPER_REQUEST = "110";
        public static final String SHIPPER_RE_REQUEST = "111";
        public static final String CARRIER_PROPOSAL = "120";
        public static final String CARRIER_RE_PROPOSAL = "121";
        public static final String SHIPPER_APPROVE = "130";
        public static final String CARRIER_APPROVE = "131";
        public static final String MAKE_PAYMENT = "140";
        public static final String START_TRANSPORT = "150";
        public static final String COMPLETE_TRANSPORT = "151";
        public static final String CARRIER1_REQUEST = "210";
        public static final String CARRIER1_RE_REQUEST = "211";
        public static final String CARRIER2_PROPOSAL = "220";
        public static final String CARRIER2_RE_PROPOSAL = "221";
        public static final String CARRIER1_APPROVE = "230";
        public static final String CARRIER2_APPROVE = "231";
        public static final String CARRIER_MAKE_PAYMENT = "240";
        public static final String CARRIER_START_TRANSPORT = "250";
        public static final String CARRIER_COMPLETE_TRANSPORT = "251";
    }

    public static final class TransOrderType {

        public static final String SHIPPER_REQUEST = "0";
        public static final String CARRIER_REQUEST = "1";
    }

    public static final class HTMLTemplates {

        public static final String MAIL_TEMPLATE = "mail-template";
    }

    public static final class Status {

        public static final Integer INITIALIZED = 0;
        public static final Integer MARKET = 1;
        public static final Integer AUTOMATIC_MATCHING = 2;
        public static final Integer AWAITING_CONFIRMATION = 3;
        public static final Integer CONTRACT = 4;
        public static final Integer PAYMENT = 5;
        public static final Integer TRANSPORT = 6;
        public static final Integer COMPLETED = 7;
        public static final Integer SHIPPER_CANCEL = 8;
        public static final Integer CARRIER_CANCEL = 9;
    }
}
