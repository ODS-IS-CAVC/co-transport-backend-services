package jp.co.nlj.ix.constant;

/**
 * <PRE>
 * APIConstantクラスは、APIの定数を定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class APIConstant {

    public static final String API = "/api/v1";

    public static final String TRSP_PLAN_COMPANY_ID_DEFAULT_FOR_IX = "793647dd-b372-49d4-a48d-8d62ada95428";
    public static final String API_IX_NEW = "/webapi/v1";
    public static final String STATUS_BAD_REQUEST_CODE = "400";
    public static final String STATUS_BAD_REQUEST_DESCRIPTION = "リクエスト自体に問題がある場合";
    public static final String STATUS_SERVER_ERROR_CODE = "500";
    public static final String STATUS_SERVER_ERROR_DESCRIPTION = "システムの内部にてエラーが発生している場合";
    public static final String STATUS_OK_CODE = "200";
    public static final String API_KEY = "apiKey";
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_TRACKING = "X-Tracking";
    public static final String X_FIELDS = "X-Fields";

    public static final class ShipperTransportCapacity {

        public static final String PREFIX = API + "/shipper_operation_plans";
        public static final String SEARCH_SUMMARY = "指定した条件の運送能力情報を取得";
        public static final String SEARCH_DESCRIPTION = "指定した条件に基づいて、運送能力情報を取得します。";
        public static final String SEARCH_STATUS_OK_DESCRIPTION = "指定した条件に基づいて、運送能力情報を正常に取得しました。";
    }

    public static final class TrspPlanLineItem {

        public static final String PREFIX = API + "/carrier_operation_plans";
        public static final String PROPOSE = "/propose";
        public static final String PROPOSE_OPERATION = "/{operation_id}/propose/{propose_id}";
        public static final String APPROVAL_PREFIX = "/{operation_id}/propose/{propose_id}/reply";
        public static final String STATUS_OK_MSG = "指定した条件に基づいて、運送能力情報（明細型）を正常に取得しました。";
    }

    public static final class ReqTrspPlanLineItem {

        public static final String PREFIX = API + "/transport_plans";
    }

    public static final class OperationNotify {

        public static final String PREFIX = API + "/operation";

        public static final String NOTIFY = "/{operation_id}/notify";
        public static final String HANDOVER_INFO = "/{operation_id}/propose/{propose_id}/handover_info";
    }

    public static final class Transports {

        public static final String PREFIX = API + "/transports";
        public static final String NOTIFY = "/{trsp_instruction_id}/notify";
    }

    public static final class ShipperOperationPlans {

        public static final String PREFIX = API + "/shipper_operation_plans";
        public static final String SUMMARY = "荷主向け運行申し込み諾否回答通知";
        public static final String DESCRIPTION = "荷主向け運行申し込み諾否回答通知をします。";
        public static final String UPDATE_SUMMARY = "荷主向け運行申し込み諾否回答更新";
        public static final String UPDATE_DESCRIPTION = "荷主向け運行申し込み諾否回答更新をします。";
        public static final String RESERVE_SUMMARY = "荷主向け運行申し込み予約";
        public static final String RESERVE_DESCRIPTION = "荷主向け運行申し込み予約をします。";
        public static final String APPROVAL_PREFIX = "/{operation_id}/propose/{propose_id}/reply";
        public static final String UPDATE_PREFIX = "/{operation_id}/propose/{propose_id}";
        public static final String PROPOSE_PREFIX = "/propose";
    }

    public static final class OperationPlans {

        public static final String PREFIX_WEB = API_IX_NEW + "/operation_plans/";
        public static final String PREFIX = API + "/operation_plans";
        public static final String UPDATE = "/{operation_id}";
        public static final String NOTIFY = "/{operation_id}/notify";
        public static final String NOTIFY_WEB = "/notify";
    }

    public static final class OperationRequest {

        public static final String PREFIX = API + "/operation_request";
        public static final String PREFIX_WEB_API = API_IX_NEW + "/operation_request";
        public static final String INSERT = "/propose";
        public static final String UPDATE = "/{operation_id}/propose/{propose_id}";
        public static final String HANDOVER_INFO = "/{operation_id}/propose/{propose_id}/handover_info";
        public static final String NOTIFY = "/{operation_id}/propose/{propose_id}/notify";
        public static final String NOTIFY_WEB = "/notify";
        public static final String REPLY = "/{operation_id}/propose/{propose_id}/reply";
        public static final String REPLY_WEB = "/reply";
    }

    public static final class Reserve {

        public static final String PREFIX = API_IX_NEW + "/reserve";
        public static final String PROPOSE = "/propose/";
        public static final String NOTIFY = "/notify";
        public static final String REPLY = "/reply";
    }

    public static final class ReserveRequest {

        public static final String PREFIX = API + "/reserve_request";
        public static final String UPDATE = "/{operation_id}/propose/{propose_id}";
        public static final String NOTIFY = "/{operation_id}/propose/{propose_id}/notify";
        public static final String REPLY = "/{operation_id}/propose/{propose_id}/reply";
    }

    public static final class TrspPlans {

        public static final String PREFIX = API_IX_NEW + "/transport_plans/";
        public static final String NOTIFY = "/notify";
    }

    public static final class TrspPlansRequest {

        public static final String PREFIX = API + "/transport_plans_request";
        public static final String TRSP_PLANS = "/{trsp_instruction_id}";
        public static final String NOTIFY = "/{trsp_instruction_id}/notify";
    }

}
