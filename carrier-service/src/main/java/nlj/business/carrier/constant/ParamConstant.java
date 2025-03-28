package nlj.business.carrier.constant;

import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * パラメータ定数クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ParamConstant {

    public static final List<String> VEHICLE_TYPE = Arrays.asList("1", "2", "3", "4", "5", "9");
    public static final List<String> FLATBED_VAN_BODY_TYPE = Arrays.asList("1", "2", "9");
    public static final List<String> CORRECTION_CODE = Arrays.asList("1", "2", "3");
    public static final List<String> REPEAT_DAY = Arrays.asList("0", "1");
    public static final List<String> ROUND_TRIP = Arrays.asList("0", "1");
    public static final List<String> ROUND_TRIP_DIRECTION = Arrays.asList("0", "1");
    public static final List<String> ORIGIN_TYPE = Arrays.asList("1", "2", "3");
    public static final List<String> STATUS_VEHICLE_DIAGRAM = Arrays.asList("0", "1", "2", "3");
    public static final List<String> ASSIGN_TYPE = Arrays.asList("0", "1");
    public static final List<String> CARRIER_VEHICLE_TYPE = Arrays.asList(VehicleType.TRACTOR, VehicleType.TRAILER);
    public static final List<String> CARRIER_STATUS = Arrays.asList(VehicleStatus.FREE, VehicleStatus.NOT_FREE);
    public static final List<String> CARRIER_DELETE_FLAG = Arrays.asList(CarrierDeleteFlag.NOT_DELETE,
        CarrierDeleteFlag.DELETE);
    public static final String CARRIER_NO_AVAILABLE_START_DATE = "start_date";
    public static final String CARRIER_NO_AVAILABLE_END_DATE = "end_date";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String CREATED_DATE = "createdDate";
    public static final String DESC = "desc";
    public static final String TRAILER = "trailer";
    public static final String TRIP_NAME = "tripName";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String DEPARTURE_FROM = "departureFrom";
    public static final String ARRIVAL_TO = "arrivalTo";

    public static final Long _72H = 72L;
    public static final Long _3H = 3L;
    public static final Long _2H = 2L;
    public static final Long _1H = 1L;

    public static final class VehicleType {

        public static final String TRACTOR = "1";
        public static final String TRAILER = "2";
    }

    public static final class VehicleStatus {

        public static final String FREE = "0";
        public static final String NOT_FREE = "1";
    }

    public static final class CarrierDeleteFlag {

        public static final String NOT_DELETE = "0";
        public static final String DELETE = "1";
    }

    public static class VehicleInfo {

        public static final String SORT = "sort";
        public static final String ORDER = "order";
        public static final String LIMIT = "pageSize";
        public static final String LIMIT_DEFAULT = "10";
        public static final String PAGE_NO = "page";
        public static final String DEFAULT_PAGE_NO = "1";
        public static final List<String> HEADER_IMPORT = Arrays.asList(
            "registration_area_code",
            "registration_group_number",
            "registration_character",
            "registration_number_1",
            "registration_number_2",
            "vehicle_code",
            "vehicle_name",
            "vehicle_type",
            "temperature_range",
            "max_payload",
            "total_length",
            "total_width",
            "total_height",
            "ground_clearance",
            "door_height",
            "body_specification",
            "body_shape",
            "body_construction",
            "status",
            "no_avail_start_date",
            "no_avail_end_date",
            "no_avail_status"
        );
        public static final String BOM = "\uFEFF";
    }

    public static class RoundTripType {

        public static final Integer OUTBOUND_TRIP = 0;
        public static final Integer INBOUND_TRIP = 1;
    }

    public static class VehicleDiagramImport {

        public static final List<String> HEADER_DIAGRAM_IMPORT = Arrays.asList(
            "departure_from",
            "arrival_to",
            "one_way_time",
            "start_date",
            "end_date",
            "repeat_day",
            "trailer_number",
            "is_round_trip",
            "origin_type",
            "status",
            "round_trip_type",
            "trip_name",
            "diagram_departure_from",
            "diagram_arrival_to",
            "departure_time",
            "arrival_time",
            "day_week",
            "adjustment_price",
            "common_price",
            "cut_off_price",
            "diagram_status"
        );
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
        public static final int DIAGRAM_ERROR = 1;
        public static final int DIAGRAM_START = 2;
        public static final int DIAGRAM_END = 3;
        public static final String SUCCESS = "success";
        public static final String FAIL = "fail";
        public static final String ERROR = "error";
    }

    public static class ValidateSemiDynamicInfoType {

        public static final String TYPE_1 = "72";
        public static final String TYPE_2 = "3";
        public static final String TYPE_3 = "2";
        public static final String TYPE_4 = "1";
    }

    public static final class MobilityHub {

        public static final int RESERVATION_STATUS_0 = 0;
        public static final int RESERVATION_STATUS_1 = 1;
        public static final int RESERVATION_STATUS_2 = 2;
        public static final int RESERVATION_STATUS_3 = 3;
        public static final int STATUS_0 = 0;
        public static final int VEHICLE_TYPE_TRACTOR = 1;
        public static final int VEHICLE_TYPE_TRAILER = 2;
        public static final int TYPE_0 = 0;
        public static final int TYPE_1 = 1;
        public static final String SLOT_ID_PREFIX = "-";
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

    public static final class TransOrderStatus {

        public static final Integer TRANS_TYPE_0 = 0;
        public static final Integer TRANS_TYPE_1 = 1;
        public static final Integer START_TRANSPORT = 260;
        public static final Integer COMPLETE_TRANSPORT = 261;
        public static final Integer SHIPPER_START_TRANSPORT = 160;
        public static final Integer SHIPPER_COMPLETE_TRANSPORT = 161;
    }

    public static final class VEHICLE_DIAGRAM_ITEM_STATUS {

        public static final Integer DIAGRAM_ITEM_INIT = 0;
        public static final Integer DIAGRAM_ITEM_WAITING = 1;
        public static final Integer DIAGRAM_ITEM_RUNNING = 2;
        public static final Integer DIAGRAM_ITEM_COMPLETED = 3;
        public static final Integer DIAGRAM_ITEM_CANCEL = 4;
    }

    public static final class ZLWeb {
        public static final String UPDATE_TIME = "/api/v1/zl_web/update_time";
        public static final String MANAGEMENT_NUMBER_PREFIX = "NLJ";
    }
}
