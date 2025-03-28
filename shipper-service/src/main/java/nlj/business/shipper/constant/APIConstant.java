package nlj.business.shipper.constant;

/**
 * <PRE>
 * API定数クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";

    public static final class TransportPlan {

        public static final String PREFIX = API + "/transport-plan";
        public static final String BULK = "/bulk";
        public static final String ID = "{id}";
    }

    public static final class CargoInfo {

        public static final String PREFIX = API + "/cargo-info";
        public static final String ID = "/{id}";
        public static final String IMPORT = "/import";
    }

    public static final class TransportPlanItem {

        public static final String PREFIX = API + "/transport-plan-item";
        public static final String ID = "{id}";
        public static final String STATUS = "/status";
    }

    public static final class TransportPlanBulk {

        public static final String PREFIX = API + "/transport-plan-bulk";
    }

    public static final class CommonDownloadAndUpload {

        public static final String PREFIX = API;
        public static final String DOWNLOAD = "/download";
        public static final String UPLOAD_TEMPLATE = "/upload-template";

    }
}
