package jp.co.nlj.ttmi.constant;

/**
 * <PRE>
 * データベース定数クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class DataBaseConstant {

    public static final class DATE_TIME_FORMAT {

        public static final String DATE_FORMAT = "yyyyMMdd";
        public static final String TIME_FORMAT_HHMMSS = "HHmmss";
        public static final String TIME_FORMAT_HHMM = "HHmm";
    }

    public static class TracBySipReq {

        public static final String TABLE = "TM_TRACK_BY_SIP_REQ";
        public static final String ID = "ID";
        public static final String REQUEST = "REQUEST";
    }

    public static final class Incident {

        public static final String TABLE = "TM_INCIDENT_INFO";
        public static final String ID = "ID";
        public static final String INSTRUCTION_ID = "INSTRUCTION_ID";
        public static final String VEHICLE_ID = "VEHICLE_ID";
        public static final String INCIDENT_ID = "INCIDENT_ID";
        public static final String INCIDENT_JSON = "INCIDENT_JSON";
        public static final String OPERATOR_ID = "OPERATOR_ID";
    }

    public static final class DiagramItemTracking {

        public static final String TABLE = "TM_DIAGRAM_ITEM_TRACKING";
        public static final String ID = "ID";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "VEHICLE_DIAGRAM_ITEM_ID";
        public static final String OPERATION_DATE = "OPERATION_DATE";
        public static final String OPERATION_TIME = "OPERATION_TIME";
        public static final String STATUS = "STATUS";
        public static final String LABEL = "LABEL";
        public static final String MESSAGE = "MESSAGE";
        public static final String CREATED_DATE = "CREATED_DATE";
        public static final String CREATED_USER = "CREATED_USER";
        public static final String UPDATED_DATE = "UPDATED_DATE";
        public static final String UPDATED_USER = "UPDATED_USER";
    }

    public static final class IOrderDetailDiv {

        public static final String TABLE = "I_ORDER_DETAIL_DIV";
        public static final String ORDER_DETAIL_DIV_KEY = "ORDER_DETAIL_DIV_KEY";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String DETAIL_NO = "DETAIL_NO";
        public static final String DETAIL_SERIAL_NUMBER = "DETAIL_SERIAL_NUMBER";
        public static final String SHIPMENT_TASK_KEY = "SHIPMENT_TASK_KEY";
        public static final String FINAL_LOADING_DATETIME = "FINAL_LOADING_DATETIME";
        public static final String TAG_PRINT_FLG = "TAG_PRINT_FLG";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String UNIT = "UNIT";
        public static final String FINAL_PRINT_DATETIME = "FINAL_PRINT_DATETIME";
        public static final String REALLOCATE_FLG = "REALLOCATE_FLG";
        public static final String TAG_ADD_FLG = "TAG_ADD_FLG";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String CREATE_USER = "CREATE_USER";
        public static final String CREATE_DT = "CREATE_DT";
        public static final String UPD_USER = "UPD_USER";
        public static final String UPD_DT = "UPD_DT";
    }

    public static final class IOrderInfo {

        public static final String TABLE = "I_ORDER_INFO";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String ORDER_DATE = "ORDER_DATE";
        public static final String OWNER_COMPANY_CODE = "OWNER_COMPANY_CODE";
        public static final String OWNER_USER_ID = "OWNER_USER_ID";
        public static final String OWNER_COMPANY_NAME_ABBREVIATION = "OWNER_COMPANY_NAME_ABBREVIATION";
        public static final String OWNER_USER_NAME = "OWNER_USER_NAME";
        public static final String OWNER_PHONE = "OWNER_PHONE";
        public static final String ORDER_NUMBER = "ORDER_NUMBER";
        public static final String SHIPMENT_SHIPPER_ID = "SHIPMENT_SHIPPER_ID";
        public static final String SHIPMENT_SHIPPER_NAME = "SHIPMENT_SHIPPER_NAME";
        public static final String SHIP_FROM = "SHIP_FROM";
        public static final String SHIP_TO = "SHIP_TO";
        public static final String DROP_OFF_DATE = "DROP_OFF_DATE";
        public static final String DROP_OFF_TIME_FROM = "DROP_OFF_TIME_FROM";
        public static final String DROP_OFF_TIME_TO = "DROP_OFF_TIME_TO";
        public static final String DROP_OFF_LOCATION_KEY = "DROP_OFF_LOCATION_KEY";
        public static final String DELIVERY_DATE = "DELIVERY_DATE";
        public static final String DELIVERY_TIME_FROM = "DELIVERY_TIME_FROM";
        public static final String DELIVERY_TIME_TO = "DELIVERY_TIME_TO";
        public static final String DELIVERY_LOCATION_KEY = "DELIVERY_LOCATION_KEY";
        public static final String DROP_OFF_COMPANY_NAME = "DROP_OFF_COMPANY_NAME";
        public static final String DROP_OFF_VEHICLE_SPEC = "DROP_OFF_VEHICLE_SPEC";
        public static final String DROP_OFF_VEHICLE_NUMBER = "DROP_OFF_VEHICLE_NUMBER";
        public static final String DROP_OFF_DRIVER_NAME = "DROP_OFF_DRIVER_NAME";
        public static final String DROP_OFF_MOBILE_PHONE = "DROP_OFF_MOBILE_PHONE";
        public static final String DROP_OFF_MAIL = "DROP_OFF_MAIL";
        public static final String DELIVERY_COMPANY_NAME = "DELIVERY_COMPANY_NAME";
        public static final String DELIVERY_VEHICLE_SPEC = "DELIVERY_VEHICLE_SPEC";
        public static final String DELIVERY_VEHICLE_NUMBER = "DELIVERY_VEHICLE_NUMBER";
        public static final String DELIVERY_DRIVER_NAME = "DELIVERY_DRIVER_NAME";
        public static final String DELIVERY_MOBILE_PHONE = "DELIVERY_MOBILE_PHONE";
        public static final String DELIVERY_MAIL = "DELIVERY_MAIL";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String STATUS_KBN = "STATUS_KBN";
        public static final String TRUNK_LINE_LOADING_KBN = "TRUNK_LINE_LOADING_KBN";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String CREATE_USER = "CREATE_USER";
        public static final String CREATE_DT = "CREATE_DT";
        public static final String UPD_USER = "UPD_USER";
        public static final String UPD_DT = "UPD_DT";
    }

    public static final class IShipmentStatus {

        public static final String TABLE = "I_SHIPMENT_STATUS";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String DESTINATION_CENTER_KEY = "DESTINATION_CENTER_KEY";
        public static final String SHIPMENT_KBN = "SHIPMENT_KBN";
        public static final String DELIVERY_SERVICE_KEY = "DELIVERY_SERVICE_KEY";
        public static final String SHIPMENT_STATUS_KBN = "SHIPMENT_STATUS_KBN";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String CREATE_USER = "CREATE_USER";
        public static final String CREATE_DT = "CREATE_DT";
        public static final String UPD_USER = "UPD_USER";
        public static final String UPD_DT = "UPD_DT";
    }

    public static final class IShipmentTaskList {

        public static final String TABLE = "I_SHIPMENT_TASK_LIST";
        public static final String SHIPMENT_TASK_KEY = "SHIPMENT_TASK_KEY";
        public static final String SHIPMENT_KBN = "SHIPMENT_KBN";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String OWNER_COMPANY_CODE = "OWNER_COMPANY_CODE";
        public static final String OWNER_COMPANY_NAME_ABBREVIATION = "OWNER_COMPANY_NAME_ABBREVIATION";
        public static final String OWNER_USER_NAME = "OWNER_USER_NAME";
        public static final String OWNER_PHONE = "OWNER_PHONE";
        public static final String SHIPMENT_SHIPPER_ID = "SHIPMENT_SHIPPER_ID";
        public static final String SHIPMENT_SHIPPER_NAME = "SHIPMENT_SHIPPER_NAME";
        public static final String SHIP_FROM = "SHIP_FROM";
        public static final String SHIP_TO = "SHIP_TO";
        public static final String DESTINATION_DATE = "DESTINATION_DATE";
        public static final String DESTINATION_TIME_FROM = "DESTINATION_TIME_FROM";
        public static final String DESTINATION_TIME_TO = "DESTINATION_TIME_TO";
        public static final String DESTINATION_CENTER_NAME_ABBREVIATION = "DESTINATION_CENTER_NAME_ABBREVIATION";
        public static final String SHIPPING_COMPANY_NAME = "SHIPPING_COMPANY_NAME";
        public static final String VEHICLE_SPEC = "VEHICLE_SPEC";
        public static final String VEHICLE_NUMBER = "VEHICLE_NUMBER";
        public static final String DRIVER_NAME = "DRIVER_NAME";
        public static final String MOBILE_PHONE = "MOBILE_PHONE";
        public static final String MAIL = "MAIL";
        public static final String DETAIL_NO = "DETAIL_NO";
        public static final String ITEM_CODE = "ITEM_CODE";
        public static final String ITEM_NAME = "ITEM_NAME";
        public static final String LENGTH_SIZE = "LENGTH_SIZE";
        public static final String WIDTH_SIZE = "WIDTH_SIZE";
        public static final String HEIGHT_SIZE = "HEIGHT_SIZE";
        public static final String WEIGHT = "WEIGHT";
        public static final String UNIT = "UNIT";
        public static final String FLAT_STACKING_FLG = "FLAT_STACKING_FLG";
        public static final String PACKAGE_STACKING_FLG = "PACKAGE_STACKING_FLG";
        public static final String UPPER_STAGE_USE_FLG = "UPPER_STAGE_USE_FLG";
        public static final String MEMO = "MEMO";
        public static final String TOTAL_UNIT = "TOTAL_UNIT";
        public static final String TRUNK_LINE_ALLOCATION_KEY = "TRUNK_LINE_ALLOCATION_KEY";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String LOADING_ATTRIBUTE1 = "LOADING_ATTRIBUTE1";
        public static final String LOADING_ATTRIBUTE2 = "LOADING_ATTRIBUTE2";
        public static final String LOADING_ATTRIBUTE3 = "LOADING_ATTRIBUTE3";
        public static final String LOADING_ATTRIBUTE4 = "LOADING_ATTRIBUTE4";
        public static final String LOADING_ATTRIBUTE5 = "LOADING_ATTRIBUTE5";
        public static final String DESTINATION_DATETIME = "DESTINATION_DATETIME";
        public static final String TRUNK_CENTER_NAME_ABBREVIATION = "TRUNK_CENTER_NAME_ABBREVIATION";
        public static final String TRUNK_SCHEDULE_DATE = "TRUNK_SCHEDULE_DATE";
        public static final String TRUNK_SCHEDULE_TIME = "TRUNK_SCHEDULE_TIME";
        public static final String TRUNK_SCHEDULE_DATETIME = "TRUNK_SCHEDULE_DATETIME";
        public static final String TRUNK_SHIPPING_NAME = "TRUNK_SHIPPING_NAME";
        public static final String ORDER_DATE = "ORDER_DATE";
        public static final String ORDER_NUMBER = "ORDER_NUMBER";
        public static final String DELIVERY_DATE = "DELIVERY_DATE";
        public static final String DELIVERY_TIME_FROM = "DELIVERY_TIME_FROM";
        public static final String DELIVERY_TIME_TO = "DELIVERY_TIME_TO";
        public static final String TRACTOR_UNIT = "TRACTOR_UNIT";
        public static final String TRAILER_UNIT = "TRAILER_UNIT";
    }

    public static final class IVehicleSchedule {

        public static final String TABLE = "I_VEHICLE_SCHEDULE";
        public static final String VEHICLE_SCHEDULE_KEY = "VEHICLE_SCHEDULE_KEY";
        public static final String OPE_SERVICE_KEY = "OPE_SERVICE_KEY";
        public static final String CLOCK_IN_DATETIME = "CLOCK_IN_DATETIME";
        public static final String CLOCK_OUT_DATETIME = "CLOCK_OUT_DATETIME";
        public static final String DEPARTURE_DATE = "DEPARTURE_DATE";
        public static final String DEPARTURE_DATETIME = "DEPARTURE_DATETIME";
        public static final String ARRIVAL_DATETIME = "ARRIVAL_DATETIME";
        public static final String MEAL_ALLOWANCE_KBN = "MEAL_ALLOWANCE_KBN";
        public static final String TRIP_KBN = "TRIP_KBN";
        public static final String WORKING_KBN = "WORKING_KBN";
        public static final String WORK_ON_TIME = "WORK_ON_TIME";
        public static final String POINT_CALL_TIME = "POINT_CALL_TIME";
        public static final String COMPANY_CODE = "COMPANY_CODE";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class IVehicleScheduleRest {

        public static final String TABLE = "I_VEHICLE_SCHEDULE_REST";
        public static final String VEHICLE_SCHEDULE_KEY = "VEHICLE_SCHEDULE_KEY";
        public static final String SEQ_NO = "SEQ_NO";
        public static final String REST_STOP_POINT = "REST_STOP_POINT";
        public static final String REST_DATETIME_FROM = "REST_DATETIME_FROM";
        public static final String REST_DATETIME_TO = "REST_DATETIME_TO";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class IOrderDetail {

        public static final String TABLE = "I_ORDER_DETAIL";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String DETAIL_NO = "DETAIL_NO";
        public static final String SHIPMENT_SHIPPER_ID = "SHIPMENT_SHIPPER_ID";
        public static final String ITEM_CODE = "ITEM_CODE";
        public static final String ITEM_NAME = "ITEM_NAME";
        public static final String LENGTH_SIZE = "LENGTH_SIZE";
        public static final String WIDTH_SIZE = "WIDTH_SIZE";
        public static final String HEIGHT_SIZE = "HEIGHT_SIZE";
        public static final String WEIGHT = "WEIGHT";
        public static final String UNIT = "UNIT";
        public static final String FLAT_STACKING_FLG = "FLAT_STACKING_FLG";
        public static final String PACKAGE_STACKING_FLG = "PACKAGE_STACKING_FLG";
        public static final String UPPER_STAGE_USE_FLG = "UPPER_STAGE_USE_FLG";
        public static final String MEMO = "MEMO";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String CREATE_USER = "CREATE_USER";
        public static final String CREATE_DT = "CREATE_DT";
        public static final String UPD_USER = "UPD_USER";
        public static final String UPD_DT = "UPD_DT";
        public static final String ORDER_NUMBER = "ORDER_NUMBER";
        public static final String SHIP_FROM = "SHIP_FROM";
        public static final String SHIP_TO = "SHIP_TO";
    }

    public static final class IVehicleSchedulePlan {

        public static final String TABLE = "I_VEHICLE_SCHEDULE_PLAN";
        public static final String VEHICLE_SCHEDULE_KEY = "VEHICLE_SCHEDULE_KEY";
        public static final String OPE_SERVICE_ORDER = "OPE_SERVICE_ORDER";
        public static final String OPE_SERVICE_KEY = "OPE_SERVICE_KEY";
        public static final String OPERATION_DATETIME = "OPERATION_DATETIME";
        public static final String DELIVERY_SERVICE_KEY = "DELIVERY_SERVICE_KEY";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class MOperatingService {

        public static final String TABLE = "M_OPERATING_SERVICE";
        public static final String OPE_SERVICE_KEY = "OPE_SERVICE_KEY";
        public static final String OPE_SERVICE_NAME = "OPE_SERVICE_NAME";
        public static final String OPE_SERVICE_NAME_ABBREVIATION = "OPE_SERVICE_NAME_ABBREVIATION";
        public static final String ROUTE_KEY = "ROUTE_KEY";
        public static final String SAME_VEHICLE_KEYWORD = "SAME_VEHICLE_KEYWORD";
        public static final String CLOCK_IN_TIME = "CLOCK_IN_TIME";
        public static final String CLOCK_OUT_TIME = "CLOCK_OUT_TIME";
        public static final String MEAL_ALLOWANCE_KBN = "MEAL_ALLOWANCE_KBN";
        public static final String TRIP_KBN = "TRIP_KBN";
        public static final String WORKING_KBN = "WORKING_KBN";
        public static final String WORK_ON_TIME = "WORK_ON_TIME";
        public static final String POINT_CALL_TIME = "POINT_CALL_TIME";
        public static final String OWNED_COMPANY_CODE = "OWNED_COMPANY_CODE";
        public static final String LOGISTICS_COMPANY_CODE = "LOGISTICS_COMPANY_CODE";
        public static final String SHOW_DATE_FROM = "SHOW_DATE_FROM";
        public static final String SHOW_DATE_TO = "SHOW_DATE_TO";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class MOperatingServicePlan {

        public static final String TABLE = "M_OPERATING_SERVICE_PLAN";
        public static final String OPE_SERVICE_KEY = "OPE_SERVICE_KEY";
        public static final String OPE_SERVICE_ORDER = "OPE_SERVICE_ORDER";
        public static final String LOCATION_KEY = "LOCATION_KEY";
        public static final String OPERATION_KBN = "OPERATION_KBN";
        public static final String OPERATION_TIME = "OPERATION_TIME";
        public static final String SHIPPING_END_POINT = "SHIPPING_END_POINT";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class ILoadingInfo {

        public static final String TABLE = "I_LOADING_INFO";
        public static final String TRUNK_LINE_ALLOCATION_KEY = "TRUNK_LINE_ALLOCATION_KEY";
        public static final String VEHICLE_SCHEDULE_KEY = "VEHICLE_SCHEDULE_KEY";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String DETAIL_NO = "DETAIL_NO";
        public static final String UNIT = "UNIT";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }

    public static final class IShipmentCargoStatus {

        public static final String TABLE = "I_SHIPMENT_CARGO_STATUS";
        public static final String DESTINATION_CENTER_KEY = "DESTINATION_CENTER_KEY";
        public static final String SHIPMENT_KBN = "SHIPMENT_KBN";
        public static final String DELIVERY_SERVICE_KEY = "DELIVERY_SERVICE_KEY";
        public static final String MANAGEMENT_NUMBER = "MANAGEMENT_NUMBER";
        public static final String DETAIL_NO = "DETAIL_NO";
        public static final String CARGO_STATUS_KBN = "CARGO_STATUS_KBN";
        public static final String DELETE_FLG = "DELETE_FLG";
        public static final String PROCESS_ID = "PROCESS_ID";
    }
}
