package nlj.business.carrier.link.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * パラメータ定数.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ParamConstant {

    public static final String SERVICE_NO = "service_no";
    public static final String SERVICE_NAME = "service_name";
    public static final String GIAI = "giai";
    public static final String TRSP_CLI_PRTY_HEAD_OFF_ID = "trsp_cli_prty_head_off_id";
    public static final String TRSP_CLI_PRTY_BRNC_OFF_ID = "trsp_cli_prty_brnc_off_id";
    public static final String TRSP_INSTRUCTION_ID = "trsp_instruction_id";
    public static final String CNSG_PRTY_HEAD_OFF_ID = "cnsg_prty_head_off_id";
    public static final String CNSG_PRTY_BRNC_OFF_ID = "cnsg_prty_brnc_off_id";
    public static final String TRSP_RQR_PRTY_HEAD_OFF_ID = "trsp_rqr_prty_head_off_id";
    public static final String TRSP_RQR_PRTY_BRNC_OFF_ID = "trsp_rqr_prty_brnc_off_id";
    public static final String SERVICE_STRT_DATE = "service_strt_date";
    public static final String CAR_MAX_LOAD_CAPACITY1 = "car_max_load_capacity1_meas";
    public static final String TRSP_STRT_TXT = "trsp_op_strt_area_line_one_txt";
    public static final String TRSP_END_TXT = "trsp_op_end_area_line_one_txt";
    public static final String TRIM_SPACE_TWO_BYTE = "^[\\u3000]+|[\\u3000]+$";
    public static final String MAX_TRSP_STRT_DATE = "max_trsp_op_date_trm_strt_date";
    public static final String MIN_TRSP_STRT_DATE = "min_trsp_op_date_trm_strt_date";
    public static final String MAX_TRSP_END_DATE = "max_trsp_op_date_trm_end_date";
    public static final String MIN_TRSP_END_DATE = "min_trsp_op_date_trm_end_date";
    public static final String MAX_TRSP_STRT_TIME = "max_trsp_op_plan_date_trm_strt_time";
    public static final String MIN_TRSP_STRT_TIME = "min_trsp_op_plan_date_trm_strt_time";
    public static final String MAX_TRSP_END_TIME = "max_trsp_op_plan_date_trm_end_time";
    public static final String MIN_TRSP_END_TIME = "min_trsp_op_plan_date_trm_end_time";
    public static final String EMAIL_POLICE = "vehicleinfointegrationsys-demonstration@hml.nttdata.co.jp";
    public static final String EMAIL_POLICE_INCIDENT = "POLICE_INCIDENT";
    public static final String EMAIL_POLICE_INCIDENT_STOP = "POLICE_INCIDENT_STOP";

    public static final String MSG_ID = "msg_id";
    public static final String MSG_INFOR_CLS_TYP_CD = "msg_info_cls_typ_cd";
    public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
    public static final List<String> MSG_FN_STAS_CD_TYPE = Arrays.asList("1", "2", "3");
    public static final List<String> TRSP_NORMAL_ABNORMAL = Arrays.asList("1", "2");
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
    public static final String REGEX_REGISTRATION_NUMBER =
        "^[\\u4E00-\\u9FFF\\uFF21-\\uFF5A\\uFF10-\\uFF19\\u3000]{4,}[\\u4E00-\\u9FFF\\uFF21-\\uFF5A\\uFF10-\\uFF19\\u3000]{3,}[\\u3040-\\u309F\\uFF21-\\uFF5A\\u3000]{1}[\\uFF10-\\uFF19\\u3000]{4,}[\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF01-\\uFF9F]*$";
    public static final String REGEX_DIMENSION_CODE = "^(?:AC|BA|BG|BK|BL|BR|BT|BX|CA|CAP|CC|CM2|CM3|CM|CP|CS|CY|DM3|DR|DZN|ETC|FC|FT|G|GAL|GR|GRO|HR|IN|KG|KL|KM|KPC|L|LB|M2|M3|M|MG|ML|MM|OZ|PCE|PK|PL|PR|QT|RL|RN|SA|SET|ST|TB|TNE|UT|VA|VL|W|WH|YD)$";
    public static final String REQUIRED_VALUE = "1";
    public static final String REGEX_PACKAGE_CODE = "^(BA|BK|BO|CA|DR|CY|BX|PU|PA|EN|BGP|RL|BGC|CO|BGO|COF|BG|RP|CT|HA|CP|NA|PL|RB|CR|FC|BJ|MP|ETC)$";
    public static final int DIMENSION_PACKAGE_LENGTH = 3;
    public static final List<String> INCIDENT_CATEGORY = Arrays.asList("VEHICLE_CONTROL_INCIDENT",
        "OPERATIONAL_BASIC_INCIDENT", "ACCIDENT", "WEATHER_INCIDENT", "");
    public static final String CAR_LICENSE_PLT_NUM_ID = "Tsukuba100Ha4187";

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

    public static final class IncidentCategory {

        public static final String VEHICLE_CONTROL_INCIDENT = "VEHICLE_CONTROL_INCIDENT";
        public static final String OPERATIONAL_BASIC_INCIDENT = "OPERATIONAL_BASIC_INCIDENT";
        public static final String ACCIDENT = "ACCIDENT";
        public static final String WEATHER_INCIDENT = "WEATHER_INCIDENT";
        public static final String TRAFFIC_INCIDENT = "TRAFFIC_INCIDENT";
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

    public static final class VehicleDiagramItemStatus {

        // start
        public static final int START = 0;
        // end
        public static final int END = 1;
        // init
        public static final int STATUS_0 = 0;
        // waiting
        public static final int STATUS_1 = 1;
        // start
        public static final int STATUS_2 = 2;
        // end
        public static final int STATUS_3 = 3;
        // cancel
        public static final int STATUS_4 = 4;
        // normal
        public static final int INCIDENT_TYPE_0 = 0;
        // incident
        public static final int INCIDENT_TYPE_1 = 1;
        // weather error
        public static final int INCIDENT_TYPE_2 = 2;
        // traffic error
        public static final int INCIDENT_TYPE_3 = 3;
        // stop error
        public static final int INCIDENT_TYPE_4 = 4;
        // delay error
        public static final int INCIDENT_TYPE_5 = 5;
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
        public static final int RESERVATION_STATUS_0 = 0;
        public static final int RESERVATION_STATUS_1 = 1;
        public static final int RESERVATION_STATUS_2 = 2;
        public static final int RESERVATION_STATUS_3 = 3;
        public static final int STATUS_0 = 0;

        public static final int ORDER_CONTRACT = 140;
    }

    public static final class TransOrderId {

        public static final int SHIPPER_AND_CARRIER = 0;
        public static final int CARRIER_AND_CARRIER = 1;
    }

    public static final class DEFAULT_ADDRESS {

        public static final String FROM = "9999900010015";
        public static final String TO = "9999900010022";
    }

    public static final class YAMATO_DEFAULT {

        public static final String FROM = "9999900010035";
        public static final String TO = "9999900010052";
        public static final String SHIPPER_ID = "cooperationSystemS4001";
    }

    public static final class CARRIER_OPERATOR_DEFAULT {

        public static final String OPERATOR_CODE = "991000001";
        public static final String OPERATOR_NAME = "Yamato";
    }

    public static final class MobilityHub {

        public static final int VEHICLE_TYPE_TRACTOR = 1;
        public static final int VEHICLE_TYPE_TRAILER = 2;
        public static final int TYPE_0 = 0;
        public static final int TYPE_1 = 1;
        public static final String SLOT_ID_PREFIX = "-";
    }
}
