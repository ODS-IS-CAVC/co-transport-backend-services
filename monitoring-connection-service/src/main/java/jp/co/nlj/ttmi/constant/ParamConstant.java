package jp.co.nlj.ttmi.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * パラメータ定数クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ParamConstant {

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
        "OPERATIONAL_BASIC_INCIDENT", "ACCIDENT", "WEATHER_INCIDENT", "TRAFFIC_INCIDENT");
    public static final List<String> INCIDENT_TYPE = Arrays.asList("ADK_ERROR", "ECU_ERROR", "DPD_ERROR",
        "TRANSMISSION_ERROR", "STEERING_ERROR", "EBS_ERROR", "AIR_SUSPENSION_ERROR", "TIRE_PUNCTURE", "VEHICLE_FIRE",
        "OIL_LEAKAGE", "VOLTAGE_ERROR", "FUEL_SYSTEM_ERROR", "COOLING_SYSTEM_ERROR", "CARGO_COLLAPSE",
        "AIRBAG_EXPLOSION", "SNOW_DEPTH_EXCEEDED", "WIND_SPEED_EXCEEDED", "RAINFALL_EXCEEDED", "SNOWFALL_EXCEEDED",
        "THICK_FOG", "FROZEN_ROAD_SURFACE", "OTHER_VEHICLE_ACCIDENT", "OTHER_VEHICLE_STOPPED", "TRAFFIC_CONGESTION",
        "VEHICLE_DRIVING_WRONG_WAY");
    public static final String TIME_FORMAT = "HH:mm";

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

    public static final class ZLWeb {

        public static final String LOCATION_KEY_1 = "LLC00000000000000029";
        public static final String LOCATION_KEY_2 = "LLC00000000000000030";
        public static final String OWNER_USER = "ar-01";
        public static final String LOGISTIC_COMPANY_CODE = "10NLJ010";
        public static final String DELETE_FLG = "0";
        public static final String PROCESS_ID = "ZLRCV02S20";
        public static final String DEFAULT_USER = "nljadmin01";
        public static final String DEFAULT_COMPANY_ABBREVIATION = "Company name ABBREVIATION NLJ";
        public static final String DEFAULT_SHIPPER_SHIPMENT_NAME = "ジーエム";
        public static final String STATUS_SHIPMENT_KBN = "1";
        public static final String STATUS_DELIVERY_SERVICE_KEY = "0";
        public static final String STATUS_SHIPMENT_STATUS_KBN = "01";
        public static final String ORDER_NUMBER = "1";
        public static final String SHIPMENT_KBN_4 = "4";
        public static final String SHIPMENT_KBN_3 = "3";
        public static final String DEFAULT_DESTINATION_CENTER_NAME_ABBREVIATION_FROM = "浜松";
        public static final String DEFAULT_DESTINATION_CENTER_NAME_ABBREVIATION_TO = "駿河湾沼津";
        public static final String DEFAULT_SHIP_1 = "駿河湾沼津";
        public static final String DEFAULT_SHIP_2 = "浜松";
        public static final String DEFAULT_VEHICLE_SPEC = "大型";
        public static final String OPERATION_KBN_1 = "1";
        public static final String OPERATION_KBN_2 = "2";
        public static final int OPERATION_SERIVCE_ORDER_1 = 1;
        public static final int OPERATION_SERIVCE_ORDER_2 = 2;
        public static final String END_POINT_1 = "1";
        public static final String END_POINT_2 = "2";
        public static final String CARGO_STATUS = "41";
        public static final String SAME_VEHICLE_KEYWORD = "100";
        public static final String MANAGEMENT_NUMBER = "0";
    }

    public static final class DefaultValue {

        public static final String DEFAULT_SHIP_FROM = "9999900010015";
        public static final String DEFAULT_SHIP_TO = "9999900010022";
        public static final String CAR_LICENSE_PLT_NUM_ID = "Tsukuba100Ha4187";
    }
}
