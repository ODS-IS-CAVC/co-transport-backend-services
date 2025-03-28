package nlj.business.carrier.constant;

/**
 * <PRE>
 * データベース定数クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class DataBaseConstant {

    public static final String CARRIER = "c_";
    public static final Integer TRACTOR_TYPE = 1;
    public static final String COl_TYPE_JSONB = "jsonb";
    public static final String JSONB_TRANSFORMER = "?::jsonb";
    public static final String COL_TYPE_TEXT = "text";

    public static final class VehicleDiagram {

        public static final String TABLE = CARRIER + "vehicle_diagram";
        public static final String ID = "id";
        public static final String CORPORATE_ID = "corporate_id";
        public static final String DIAGRAM_HEAD_ID = "diagram_head_id";
        public static final String ROUND_TRIP_TYPE = "round_trip_type";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String DAY_WEEK = "day_week";
        public static final String ADJUSTMENT_PRICE = "adjustment_price";
        public static final String COMMON_PRICE = "common_price";
        public static final String CUT_OFF_PRICE = "cut_off_price";
        public static final String STATUS = "status";
    }

    public static class VehicleDiagramItem {

        public static final String TABLE = CARRIER + "vehicle_diagram_item";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String DAY = "day";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String SIP_TRACK_ID = "sip_track_id";
        public static final String STATUS = "status";
        public static final String MAPPED_BY = "vehicleDiagramItem";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "ARRIVAL_TO";
        public static final String ONE_WAY_TIME = "one_way_time";
        public static final String IS_PRIVATE = "is_private";
        public static final String INCIDENT_TYPE = "incident_type";
        public static final String INCIDENT_MSG = "incident_msg";
        public static final String IS_EMERGENCY = "is_emergency";
    }

    public static class CarrierOperator {

        public static final String TABLE = CARRIER + "carrier_operator";
        public static final String ID = "id";
        public static final String OPERATOR_CODE = "operator_code";
        public static final String OPERATOR_NAME = "operator_name";
        public static final String POSTAL_CODE = "postal_code";
        public static final String ADDRESS_1 = "address_1";
        public static final String ADDRESS_2 = "address_2";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String MANAGER_NAME = "manager_name";
        public static final String IMAGE_LOGO = "image_logo";
        public static final String NOTES = "notes";
        public static final String STATUS = "status";
        public static final String CREATED_DATE = "created_date";
        public static final String CREATED_USER = "created_user";
        public static final String UPDATED_DATE = "updated_date";
        public static final String UPDATED_USER = "updated_user";
        public static final String EMAIL = "email";
    }
//  public static class VehicleDiagramItemTrailer {
//      public static final String TABLE = CARRIER + "vehicle_diagram_item_trailer";
//      public static final String ID = "id";
//      public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
//      public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
//      public static final String VEHICLE_DIAGRAM_ALLOCATION_ID = "vehicle_diagram_allocation_id";
//      public static final String DAY = "day";
//      public static final String TRIP_NAME = "trip_name";
//      public static final String DEPARTURE_TIME = "departure_time";
//      public static final String ARRIVAL_TIME = "arrival_time";
//      public static final String ADJUSTMENT_PRICE = "adjustment_price";
//  }

    public static class VehicleDiagramHead {

        public static final String TABLE = CARRIER + "vehicle_diagram_head";
        public static final String ID = "id";
        public static final String CORPORATE_ID = "corporate_id";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String ONE_WAY_TIME = "one_way_time";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String REPEAT_DAY = "repeat_day";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String IS_ROUND_TRIP = "is_round_trip";
        public static final String ORIGIN_TYPE = "origin_type";
        public static final String IMPORT_ID = "import_id";
        public static final String STATUS = "status";
    }

    public static final class DATE_TIME_FORMAT {

        public static final String DATE_FORMAT = "yyyyMMdd";

        public static final String DATE_FORMAT_HYPHEN = "yyyy-MM-dd";
        public static final String TIME_FORMAT_HHMMSS = "HHmmss";
        public static final String TIME_FORMAT_HHMM = "HHmm";
        public static final String DATE_TIME_FORMAT_HHMM = "yyyyMMddHHmmss";
        public static final String LOCAL_DATE_TIME_FORMAT = "yyyyMMdd HHmmss";
        public static final String DATE_TIME_FORMAT_JAPAN = "yyyy年MM月dd日(E) HH:mm";
        public static final String DATE_TIME_FORMAT_JAPAN_HHMM = "HH:mm";
    }

    public static final class VehicleDiagramAllocation {

        public static final String TABLE = CARRIER + "vehicle_diagram_allocation";
        public static final String ID = "id";
        public static final String CORPORATE_ID = "corporate_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String VEHICLE_INFO_ID = "vehicle_info_id";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String DISPLAY_ORDER = "display_order";
        public static final String ASSIGN_TYPE = "assign_type";
    }

    public static final class VehicleDiagramImport {

        public static final String TABLE = CARRIER + "vehicle_diagram_import";
        public static final String ID = "id";
        public static final String FILE_PATH = "file_path";
        public static final String NUMBER_SUCCESS = "number_success";
        public static final String NUMBER_FAILURE = "number_failure";
        public static final String NUMBER_TRACTOR_SUCCESS = "number_tractor_success";
        public static final String NUMBER_TRACTOR_FAILURE = "number_tractor_failure";
        public static final String NUMBER_TRAILER_SUCCESS = "number_trailer_success";
        public static final String NUMBER_TRAILER_FAILURE = "number_trailer_failure";
        public static final String NUMBER_ALLOCATION_SUCCESS = "number_allocation_success";
        public static final String NUMBER_ALLOCATION_FAILURE = "number_allocation_failure";
        public static final String STATUS = "status";
    }

    public static final class VehicleInfo {

        public static final String TABLE = CARRIER + "vehicle_info";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String REGISTRATION_AREA_CODE = "registration_area_code";
        public static final String REGISTRATION_GROUP_NUMBER = "registration_group_number";
        public static final String REGISTRATION_CHARACTER = "registration_character";
        public static final String REGISTRATION_NUMBER_1 = "registration_number_1";
        public static final String REGISTRATION_NUMBER_2 = "registration_number_2";
        public static final String VEHICLE_CODE = "vehicle_code";
        public static final String VEHICLE_NAME = "vehicle_name";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String VEHICLE_SIZE = "vehicle_size";
        public static final String TEMPERATURE_RANGE = "temperature_range";
        public static final String MAX_PAYLOAD = "max_payload";
        public static final String TOTAL_LENGTH = "total_length";
        public static final String TOTAL_WIDTH = "total_width";
        public static final String TOTAL_HEIGHT = "total_height";
        public static final String GROUND_CLEARANCE = "ground_clearance";
        public static final String DOOR_HEIGHT = "door_height";
        public static final String BODY_SPECIFICATION = "body_specification";
        public static final String BODY_SHAPE = "body_shape";
        public static final String BODY_CONSTRUCTION = "body_construction";
        public static final String IMAGES = "images";
        public static final String STATUS = "status";
        public static final String DELETE_FLAG = "delete_flag";
        public static final String NO_AVAIL_START_DATE = "no_avail_start_date";
        public static final String NO_AVAIL_END_DATE = "no_avail_end_date";
        public static final String NO_AVAIL_STATUS = "no_avail_status";
        public static final String GIAI = "giai";
    }

    public static final class VehicleNoAvailable {

        public static final String TABLE = CARRIER + "vehicle_no_available";
        public static final String ID = "id";
        public static final String VEHICLE_INFO_ID = "vehicle_info_id";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String STATUS = "status";
        public static final String DAY_WEEK = "day_week";
    }

    public static final class VehicleDiagramItemTrailer {

        public static final String TABLE = CARRIER + "vehicle_diagram_item_trailer";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String VEHICLE_DIAGRAM_ALLOCATION_ID = "vehicle_diagram_allocation_id";
        public static final String DAY = "day";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String PRICE = "price";
        public static final String MOBILITY_HUB_ID = "mobility_hub_id";
        public static final String RESERVATION_STATUS = "reservation_status";
        public static final String SIZE_CLASS = "size_class";
        public static final String VALID_FROM = "valid_from";
        public static final String VALID_UNTIL = "valid_until";
        public static final String MH_RESERVATION_ID = "mh_reservation_id";
        public static final String STATUS = "status";
        public static final String FREIGHT_RATE = "freight_rate_type";
        public static final String CUT_OFF_PRICE = "cut_off_price";
    }

    public static final class VehicleInfoImport {

        public static final String ID = "id";
        public static final String TABLE = CARRIER + "cargo_info_import";
        public static final String OPERATOR_ID = "operator_id";
        public static final String FILE_PATH = "file_path";
        public static final String NUMBER_SUCCESS = "number_success";
        public static final String NUMBER_FAILURE = "number_failure";
        public static final String STATUS = "status";
    }

    public static final class DiagramItemTracking {

        public static final String TABLE = CARRIER + "diagram_item_tracking";
        public static final String TABLE_OPERATION = CARRIER + "diagram_item_operation_tracking";

        public static final String ID = "id";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String OPERATION_DATE = "operation_date";
        public static final String OPERATION_TIME = "operation_time";
        public static final String STATUS = "status";
        public static final String LABEL = "label";
        public static final String MESSAGE = "message";
        public static final String CREATED_DATE = "created_date";
        public static final String CREATED_USER = "created_user";
        public static final String UPDATED_DATE = "updated_date";
        public static final String UPDATED_USER = "updated_user";
    }

    public static final class VehicleDiagramMobilityHub {

        public static final String TABLE = CARRIER + "vehicle_diagram_mobility_hub";
        public static final String ID = "id";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String VEHICLE_DIAGRAM_ALLOCATION_ID = "vehicle_diagram_allocation_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String MOBILITY_HUB_ID = "mobility_hub_id";
        public static final String FREIGHT_ID = "freight_id";
        public static final String TYPE = "type";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String RESERVATION_STATUS = "reservation_status";
        public static final String TRUCK_ID = "truck_id";
        public static final String SIZE_CLASS = "size_class";
        public static final String VALID_FROM = "valid_from";
        public static final String VALID_UNTIL = "valid_until";
        public static final String SLOT_ID = "slot_id";
        public static final String MH_RESERVATION_ID = "mh_reservation_id";
        public static final String STATUS = "status";
    }

    public static class AreaMaster {

        public static final String TABLE = "area_master";
        public static final String ID = "id";
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String DISPLAY_ORDER = "display_order";
        public static final String STATUS = "status";
    }

    public static class LocationMaster {

        public static final String TABLE = "location_master";
        public static final String ID = "id";
        public static final String AREA_ID = "area_id";
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String POSTAL_CODE = "postal_code";
        public static final String MOBILITY_HUB_ID = "mobility_hub_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String DISPLAY_ORDER = "display_order";
        public static final String STATUS = "status";
    }

    public static final class TransOrder {

        public static final String TABLE = "t_trans_order";
        public static final String ID = "id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String CARRIER_OPERATOR_CODE = "carrier_operator_code";
        public static final String CARRIER_OPERATOR_NAME = "carrier_operator_name";
        public static final String SHIPPER_OPERATOR_CODE = "shipper_operator_code";
        public static final String SHIPPER_OPERATOR_NAME = "shipper_operator_name";
        public static final String CARRIER2_OPERATOR_ID = "carrier2_operator_id";
        public static final String CARRIER2_OPERATOR_CODE = "carrier2_operator_code";
        public static final String CARRIER2_OPERATOR_NAME = "carrier2_operator_name";
        public static final String REQ_CNS_LINE_ITEM_ID = "req_cns_line_item_id";
        public static final String CNS_LINE_ITEM_ID = "cns_line_item_id";
        public static final String CNS_LINE_ITEM_BY_DATE_ID = "cns_line_item_by_date_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String VEHICLE_AVB_RESOURCE_ITEM_ID = "vehicle_avb_resource_item_id";
        public static final String CARRIER_OPERATOR_ID = "carrier_operator_id";
        public static final String SHIPPER_OPERATOR_ID = "shipper_operator_id";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String TRANSPORT_DATE = "transport_date";
        public static final String PRICE = "price";
        public static final String CONTRACT_FILE = "contract_file";
        public static final String STATUS = "status";
        public static final String REQUEST_COLLECTION_TIME_FROM = "request_collection_time_from";
        public static final String REQUEST_COLLECTION_TIME_TO = "request_collection_time_to";
        public static final String PROPOSE_PRICE = "propose_price";
        public static final String PROPOSE_DEPARTURE_TIME = "propose_departure_time";
        public static final String PROPOSE_ARRIVAL_TIME = "propose_arrival_time";
        public static final String REQUEST_PRICE = "request_price";
        public static final String SHIPPER_CONTRACT_FILE = "shipper_contract_file";
        public static final String CARRIER_CONTRACT_FILE = "carrier_contract_file";
        public static final String CARRIER2_CONTRACT_FILE = "carrier2_contract_file";
        public static final String REQUEST_SNAPSHOT = "request_snapshot";
        public static final String PROPOSE_SNAPSHOT = "propose_snapshot";
        public static final String PARENT_ORDER_ID = "parent_order_id ";
        public static final String TRANS_MATCHING_ID = "trans_matching_id";
        public static final String TRSP_INSTRUCTION_ID = "trsp_instruction_id";
        public static final String TRANSPORT_PLAN_ITEM_ID = "transport_plan_item_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String NEGOTIATION_DATA = "negotiation_data";
        public static final String SERVICE_NAME = "service_name";
        public static final String ITEM_NAME_TXT = "item_name_txt";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String CREATED_USER = "created_user";
        public static final String CREATED_DATE = "created_date";
        public static final String UPDATED_USER = "updated_user";
        public static final String UPDATED_DATE = "updated_date";
        public static final String IS_EMERGENCY = "is_emergency";

        public static final class TransType {

            public static final Integer SHIPPER_REQUEST = 0;
            public static final Integer CARRIER_REQUEST = 1;
        }
    }

    public static final class TransMatching {

        public static final String REQ_CNS_LINE_ITEM_ID = "req_cns_line_item_id";
        public static final String CNS_LINE_ITEM_ID = "cns_line_item_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String STATUS = "status";
    }
}
