package nlj.business.shipper.constant;

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

    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String CREATED_DATE = "createdDate";
    public static final String DESC = "desc";
    public static final List<String> CARGO_STATUS = Arrays.asList("0", "1");

    public static class CargoInfo {

        public static final String SORT = "sort";
        public static final String ORDER = "order";
        public static final String LIMIT = "pageSize";
        public static final String LIMIT_DEFAULT = "10";
        public static final String PAGE_NO = "page";
        public static final String DEFAULT_PAGE_NO = "1";
        public static final List<String> HEADER_IMPORT = Arrays.asList(
            "cargo_name",
            "outer_package_code",
            "total_length",
            "total_width",
            "total_height",
            "weight",
            "temp_range",
            "special_instructions",
            "status"
        );
        public static final String BOM = "\uFEFF";
    }

    public static class OriginType {

        public static final String ORIGIN_TYPE_1 = "1";
        public static final String ORIGIN_TYPE_2 = "2";
        public static final String ORIGIN_TYPE_3 = "3";
    }

    public static class TransportPlanStatus {

        public static final String INIT = "0";          // 初期化
        public static final String MARKET = "1";        // マーケット
        public static final String AUTO_MATCHING = "2"; // 自動マッチング
        public static final String PENDING = "3";       // 確認待ち
        public static final String CONTRACT = "4";      // 契約
        public static final String PAYMENT = "5";       // 支払い
        public static final String SHIPPING = "6";      // 配送
        public static final String COMPLETED = "7";     // 完了
    }

    public static final String PAGE_NUMBER = "pn";
    public static final String RECORDS_PER_PAGE = "rpp";
    public static final Integer RECORDS_PER_PAGE_DEFAULT = 10;
    public static final Integer PAGE_NUMBER_DEFAULT = 1;
    public static final String LIMIT = "pageSize";
    public static final String LIMIT_DEFAULT = "10";
    public static final String PAGE_NO = "page";
    public static final String DEFAULT_PAGE_NO = "1";

    public static class TransportPlanBulk {

        public static final String FILE_TYPE = "text/csv";
        public static final int MAX_FILE_SIZE = 10485760; // 10MB in bytes
    }

    public static class TransportPlan {

        public static final String TRANSPORT_NAME = "transportName";
        public static final String STATUSES = "statuses";
        public static final String OUTER_PACKAGE_CODE = "outerPackageCode";
    }
}
