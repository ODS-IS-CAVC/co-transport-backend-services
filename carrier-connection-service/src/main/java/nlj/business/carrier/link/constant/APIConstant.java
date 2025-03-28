package nlj.business.carrier.link.constant;

/**
 * <PRE>
 * API定数.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";
    public static final String STATUS_BAD_REQUEST_CODE = "400";
    public static final String STATUS_BAD_REQUEST_DESCRIPTION = "リクエスト自体に問題がある場合";
    public static final String STATUS_SERVER_ERROR_CODE = "500";
    public static final String STATUS_SERVER_ERROR_DESCRIPTION = "システムの内部にてエラーが発生している場合";
    public static final String STATUS_OK_CODE = "200";
    public static final String API_KEY = "apiKey";
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_TRACKING = "X-Tracking";

    public static class Demo {

        public static final String PREFIX = API + "/demo";
        public static final String REGISTER = "/register";
    }

    public static final class VehicleInfo {

        public static final String PREFIX = API + "/vehicle";
        public static final String DATA_CHANNEL_PREFIX = "/vehicle";
        public static final String DATA_CHANNEL_DELETE = "/vehicle_del";
    }

    public static final class VehicleSearchParam {

        public static final String VEHICLE_TYPE = "vehicle_type";
    }

    public static final class TrspPlanLineItem {

        public static final String PREFIX = API + "/carrier_trans_request";
        public static final String DATA_CHANNEL_PREFIX = "/carrier_trans_request";
        public static final String DATA_CHANNEL_DELETE = "/carrier_trans_request_del";
    }

    public static final class ReqTrspPlanLineItem {

        public static final String PREFIX = API + "/transport_plans";

        public static final String DATA_CHANNEL_PREFIX = "/transport_plans";
    }

    public static final class CarrierTransportCapacity {

        public static final String PREFIX = API + "/carrier_trans_capacity";

        public static final String DATA_CHANNEL_PREFIX = "/carrier_trans_capacity";
        public static final String DATA_CHANNEL_DELETE = "/carrier_trans_capacity_del";
    }

    public static final class ShipperTransportCapacity {

        public static final String PREFIX = API + "/shipper_trans_capacity";

        public static final String DATA_CHANNEL_PREFIX = "/shipper_trans_capacity";
        public static final String DATA_CHANNEL_DELETE = "/shipper_trans_capacity_del";

        public static final String REGISTER_SUMMARY = "運送能力情報を登録";
        public static final String REGISTER_DESCRIPTION = "運送能力情報を登録します。";
        public static final String REGISTER_STATUS_OK_DESCRIPTION = "運送能力情報を正常に登録しました。";

        public static final String UPDATE_SUMMARY = "運送能力情報を更新";
        public static final String UPDATE_DESCRIPTION = "運送能力情報を登更新します";
        public static final String UPDATE_STATUS_OK_DESCRIPTION = "運送能力情報を正常に更新しました。";

        public static final String DELETE_SUMMARY = "運送能力情報を削除";
        public static final String DELETE_DESCRIPTION = "運送能力情報を削除します。";
        public static final String DELETE_STATUS_OK_DESCRIPTION = "運送能力情報を正常に削除しました。";

        public static final String SEARCH_SUMMARY = "指定した条件の運送能力情報を取得";
        public static final String SEARCH_DESCRIPTION = "指定した条件に基づいて、運送能力情報を取得します。";
        public static final String SEARCH_STATUS_OK_DESCRIPTION = "指定した条件に基づいて、運送能力情報を正常に取得しました。";
    }

    public static final class Incident {

        public static final String PREFIX = API + "/vehicle_incident_info";
        public static final String DATA_CHANNEL_PREFIX = "/vehicleIncidentInfo";
    }

    public static final class TransMatching {

        public static final String TRSP_MATCHING_PLAN = API + "/transport-plan/matching/transport-plan";
        public static final String TRSP_MATCHING_PLAN_ITEM = API + "/transport-plan/matching/transport-plan-item";
        public static final String TRSP_MATCHING_ABILITY = API + "/transport-ability/matching/transport-ability";
        public static final String TRSP_MATCHING_ABILITY_ITEM =
            API + "/transport-ability/matching/transport-ability-item";
        public static final String CARRIER_ORDER_ID_SALE =
            API + "/transport-ability/carrier/insert/cns-line-item-by-date";
        public static final String CARRIER_ORDER_ID_CANCEL =
            API + "/transport-ability/carrier/cancel/cns-line-item-by-date";
        public static final String CARRIER_ORDER_ID_MATCHING = API + "/transport-ability/carrier/insert/trans-matching";

        public static final String REQ_CNS_LINE_ITEM_MATCHING = API + "/req-cns-line-item/matching";
        public static final String VEHICLE_AVB_RESOURCE_MATCHING = API + "/vehicle-avb-resource/matching";
        public static final String CNS_LINE_ITEM_MATCHING = API + "/cns-line-item/matching";

        public static final String SHIPPER_ORDER_ID_SALE =
            API + "/transport-ability/shipper/insert/vehicle-avb-resource-item";
    }

    public static final class TrspAbilityLineItem {

        //public static final String PREFIX = API + "/trsp_ability_line_item";
        public static final String PREFIX = "/trsp_ability_line_item";
    }

    public static final class Transaction {

        public static final String TRANSACTION_CARRIER_CARRIER = API + "/transaction/carrier/carrier";
    }
}
