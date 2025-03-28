package jp.co.nlj.gateway.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * ParamConstantクラスは、パラメータの定数を定義するためのクラスです。
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

    public static final String MSG_ID = "msg_id";
    public static final String MSG_INFOR_CLS_TYP_CD = "msg_info_cls_typ_cd";
    public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
    public static final List<String> VEHICLE_TYPE = Arrays.asList("1", "2", "3", "4", "5", "9");
    public static final List<String> FLATBED_VAN_BODY_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> CORRECTION_CODE = Arrays.asList(CorrectionCode.NEW, CorrectionCode.CHANGE,
        CorrectionCode.CANCEL);
    public static final List<String> TRACTOR = Arrays.asList("0", "1");

    public static final String DATA_MODEL_TYPE = "test1";
    public static final String X_TENANT_ID_KEY = "X-TENANT-ID";

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
}
