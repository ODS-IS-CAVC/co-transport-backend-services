package nlj.business.carrier.constant;

/**
 * <PRE>
 * API定数クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";

    public static final class VehicleDiagram {

        public static final String PREFIX = API + "/delivery-ability";
        public static final String ABILITY_ITEMS = "/{abilityId}/items";
    }

    public static final class VehicleDiagramItemTrailer {

        public static final String PREFIX = API + "/item-trailer/status";
    }

    public static final class VehicleDiagramItem {

        public static final String PREFIX = API + "/item";
        public static final String SIP_TRACK = "/sip-track";
        public static final String IS_PRIVATE = "/{id}/private";
        public static final String DEPARTURE_ARRIVAL_TIME = "/time/{id}";
        public static final String DEPARTURE_ARRIVAL_TIME_BY_DIAGRAM_ITEM_ID = "/time/diagram_item/{id}";
        public static final String UPDATE_STATUS = "/status/{id}/{type}";
        public static final String UPDATE_TIME = "/time/{id}";
        public static final String IS_EMERGENCY = "/{id}/emergency";
        public static final String UPDATE_ITEM_STATUS = "/status";
    }

    public static final class DeliveryAbilityItem {

        public static final String PREFIX = API + "/delivery-ability-item";
        public static final String PATH_VARIABLE_ID = "/{id}";
        public static final String TRACKING = "/{id}/tracking";

        public static final String OPERATION_TRACKING = "/{id}/operation-tracking";
    }

    public static final class VehicleDiagramAllocation {

        public static final String PREFIX = API + "/delivery-ability";
        public static final String PATH_VARIABLE_ALLOCATION = "/{id}/vehicle";
    }

    public static final class VehicleInfo {

        public static final String PREFIX = API + "/vehicle";
        public static final String UPDATE = "/{id}";
        public static final String REGISTER = "";
        public static final String FIND_ALL = "";
        public static final String DETAIL = "/{id}";
        public static final String IMPORT = "/import";

    }

    public static final class CommonDownloadAndUpload {

        public static final String PREFIX = API;
        public static final String DOWNLOAD = "/download";
        public static final String UPLOAD_TEMPLATE = "/upload-template";

    }

    public static final class VehicleDiagramPrice {

        public static final String PREFIX = API + "/delivery-ability";
        public static final String PATH_VARIABLE_ID = "/{id}/price";
    }

    public static final class SemiDynamicInfo {

        public static final String PREFIX = API + "/semiDynamicInfo";
        public static final String GET_CURRENT = "/current";
        public static final String GET_CURRENT_SEMI_DYNAMIC_INFO_SUMMARY = "現在のセミダイナミック情報取得";
        public static final String GET_CURRENT_SEMI_DYNAMIC_INFO_DESCRIPTION = "現在のセミダイナミック情報取得";
        public static final String GET_BEFORE_START = "/beforeStart";
        public static final String GET_BEFORE_START_SEMI_DYNAMIC_INFO_SUMMARY = "開始前のセミダイナミック情報取得";
        public static final String GET_BEFORE_START_SEMI_DYNAMIC_INFO_DESCRIPTION = "開始前のセミダイナミック情報取得";
    }

    public static final class AreaLocation {

        public static final String PREFIX = API + "/locations";
    }

    public static final class ValidateSemiDynamicInfo {

        public static final String PREFIX = API + "/validate_semi_dynamic_info";
        public static final String VALIDATE_SEMI_DYNAMIC_INFO_SUMMARY = "セミダイナミック情報の検証";
        public static final String VALIDATE_SEMI_DYNAMIC_INFO_DESCRIPTION = "セミダイナミック情報の検証";
    }

    public static final class TransportOrder {

        public static final String SEARCH_BY_TRAILER_ID = "/api/v1/transport-order";
    }
}
