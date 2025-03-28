package nlj.business.transaction.constant;

/**
 * <PRE>
 * API定数。<BR>
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
    public static final String AUTHORIZATION = "Authorization";
    public static final String API_KEY = "apiKey";

    public static class TransportAbility {

        public static final String PREFIX = API + "/transport_ability";
        public static final String LIST_MATCHING = "/matching";
        public static final String LIST_MATCHING_SUMMARY = "輸送能力の一覧";
        public static final String LIST_MATCHING_DESCRIPTION = "輸送能力の一覧";
        public static final String LIST_MATCHING_STATUS_OK_DESCRIPTION = "輸送能力の一覧";

        public static final String LIST_MATCHING_ID = "/matching/{id}";
        public static final String LIST_MATCHING_ID_SUMMARY = "輸送計画の一覧";
        public static final String LIST_MATCHING_ID_DESCRIPTION = "輸送計画の一覧";
        public static final String LIST_MATCHING_ID_STATUS_OK_DESCRIPTION = "輸送計画の一覧";

        public static final String LIST_PUBLIC = "/public";
        public static final String LIST_PUBLIC_SUMMARY = "輸送計画検索";
        public static final String LIST_PUBLIC_DESCRIPTION = "輸送計画検索";
        public static final String LIST_PUBLIC_STATUS_OK_DESCRIPTION = "輸送計画検索";

        public static final String MATCHING_DETAILS = "/matching/transport_ability";
        public static final String MATCHED_DETAIL_SUMMARY = "輸送能力マッチング実行";
        public static final String MATCHED_DETAIL_DESCRIPTION = "輸送能力マッチング実行";
        public static final String MATCHED_DETAIL_STATUS_OK_DESCRIPTION = "輸送能力マッチング実行";

        public static final String MATCHING_DETAIL_ITEM = "/matching/transport_ability_item";
        public static final String MATCHED_DETAIL_ITEM_SUMMARY = "輸送アイテムの能力マッチングを実行します";
        public static final String MATCHED_DETAIL_ITEM_DESCRIPTION = "輸送アイテムの能力マッチングを実行します";
        public static final String MATCHED_DETAIL_ITEM_STATUS_OK_DESCRIPTION = "輸送アイテムの能力マッチングを実行します";

        public static final String ON_SALE = "/onSale";
        public static final String ON_SALE_SUMMARY = "発売中の交通プラン一覧";
        public static final String ON_SALE_DESCRIPTION = "発売中の交通プラン一覧";
        public static final String ON_SALE_STATUS_OK_DESCRIPTION = "発売中の交通プラン一覧";
        public static final String ON_SALE_UPDATE_STATUS_OK_DESCRIPTION = "販売中の輸送計画アイテムのステータスが正常に更新されました";

        public static final String TRANSPORT_ABILITY_PROPOSAL = "/proposal";
        public static final String TRANSPORT_ABILITY_PROPOSAL_SUMMARY = "運送能力の提案";
        public static final String TRANSPORT_ABILITY_PROPOSAL_DESCRIPTION = "運送能力の提案";
        public static final String TRANSPORT_ABILITY_PROPOSAL_STATUS_OK_DESCRIPTION = "運送能力の提案";

        public static final String CARRIER_ORDER_ID_SALE = "/carrier/{order_id}/sale";
        public static final String CARRIER_ORDER_ID_CANCEL = "/carrier/{order_id}/cancel";
        public static final String CARRIER_ORDER_ID_SALE_SUMMARY = "";
        public static final String CARRIER_ORDER_ID_SALE_DESCRIPTION = "";
        public static final String CARRIER_ORDER_ID_SALE_STATUS_OK_DESCRIPTION = "";

        public static final String CARRIER_ORDER_ID_MATCHING = "/carrier/{order_id}/matching";
        public static final String CARRIER_ORDER_ID_MATCHING_SUMMARY = "";
        public static final String CARRIER_ORDER_ID_MATCHING_DESCRIPTION = "";
        public static final String CARRIER_ORDER_ID_MATCHING_STATUS_OK_DESCRIPTION = "";

        public static final String CARRIER_ORDER_ID_LIST_ON_SALE = "/carrier/{order_id}/onSale";
        public static final String CARRIER_ORDER_ID_LIST_ON_SALE_SUMMARY = "";
        public static final String CARRIER_ORDER_ID_LIST_ON_SALE_DESCRIPTION = "";
        public static final String CARRIER_ORDER_ID_LIST_ON_SALE_STATUS_OK_DESCRIPTION = "";

        public static final String LIST_PUBLIC_IX = "/shipper/search";
        public static final String LIST_PUBLIC_IX_SUMMARY = "輸送計画検索";
        public static final String LIST_PUBLIC_IX_DESCRIPTION = "輸送計画検索";
        public static final String LIST_PUBLIC_IX_STATUS_OK_DESCRIPTION = "輸送計画検索";

        public static final String SHIPPER_ORDER_ID_SALE = "/shipper/{order_id}/sale";
        public static final String SHIPPER_ORDER_ID_SALE_SUMMARY = "";
        public static final String SHIPPER_ORDER_ID_SALE_DESCRIPTION = "";
        public static final String SHIPPER_ORDER_ID_SALE_STATUS_OK_DESCRIPTION = "";

        public static final String SALE = "/sale";
    }

    public static class TransportPlan {

        public static final String PREFIX = API + "/transport_plan";
        public static final String LIST_MATCHING = "/matching";
        public static final String LIST_MATCHING_SUMMARY = "輸送計画の一覧";
        public static final String LIST_MATCHING_DESCRIPTION = "輸送計画の一覧";
        public static final String LIST_MATCHING_STATUS_OK_DESCRIPTION = "輸送計画の一覧";

        public static final String LIST_PUBLIC = "/public";
        public static final String LIST_PUBLIC_SUMMARY = "輸送計画検索";
        public static final String LIST_PUBLIC_DESCRIPTION = "輸送計画検索";
        public static final String LIST_PUBLIC_STATUS_OK_DESCRIPTION = "輸送計画検索";

        public static final String LIST_MATCHING_ID = "/matching/{id}";
        public static final String LIST_MATCHING_ID_SUMMARY = "輸送計画の一覧";
        public static final String LIST_MATCHING_ID_DESCRIPTION = "輸送計画の一覧";
        public static final String LIST_MATCHING_ID_STATUS_OK_DESCRIPTION = "輸送計画の一覧";

        public static final String ON_SALE = "/onSale";
        public static final String ON_SALE_SUMMARY = "発売中の交通プラン一覧";
        public static final String ON_SALE_DESCRIPTION = "発売中の交通プラン一覧";
        public static final String ON_SALE_STATUS_OK_DESCRIPTION = "発売中の交通プラン一覧";
        public static final String ON_SALE_UPDATE_STATUS_OK_DESCRIPTION = "販売中の輸送計画アイテムのステータスが正常に更新されました";

        public static final String POST_MATCHED_TRANSPORT_PLAN = "/matching/transport_plan";
        public static final String POST_MATCHED_TRANSPORT_PLAN_SUMMARY = "輸送計画IDによる一致";
        public static final String POST_MATCHED_TRANSPORT_PLAN_DESCRIPTION = "輸送計画IDによる一致";
        public static final String POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION = "輸送計画IDによる一致";

        public static final String POST_MATCHED_TRANSPORT_PLAN_ITEM = "/matching/transport_plan_item";
        public static final String POST_MATCHED_TRANSPORT_PLAN_ITEM_SUMMARY = "輸送計画項目IDによる一致";
        public static final String POST_MATCHED_TRANSPORT_PLAN_ITEM_DESCRIPTION = "輸送計画項目IDによる一致";
        public static final String POST_MATCHED_TRANSPORT_PLAN_ITEM_STATUS_OK_DESCRIPTION = "輸送計画項目IDによる一致";

        public static final String TRANSPORT_PLAN_PROPOSAL = "/proposal";
        public static final String TRANSPORT_PLAN_PROPOSAL_SUMMARY = "輸送計画の提案";
        public static final String TRANSPORT_PLAN_PROPOSAL_DESCRIPTION = "輸送計画の提案";
        public static final String TRANSPORT_PLAN_PROPOSAL_STATUS_OK_DESCRIPTION = "輸送計画の提案";

        public static final String SALE = "/sale";
    }

    public static class TransferStatus {

        public static final String PREFIX = API + "/transfer-status";
    }

    public static class MarketPrice {

        public static final String PREFIX = API + "/market_price";
        public static final String LIST_SHIPPER = "/shipper";
        public static final String LIST_SHIPPER_SUMMARY = "相場荷主一覧";
        public static final String LIST_SHIPPER_DESCRIPTION = "相場荷主一覧";
        public static final String LIST_SHIPPER_STATUS_OK_DESCRIPTION = "相場荷主一覧";
    }

    public static class Transaction {

        public static final String PREFIX = API + "/transaction";
        public static final String TRANSPORT_PROPOSAL = "/carrier";
        public static final String TRANSPORT_PROPOSAL_SUMMARY = "輸送提案登録";
        public static final String TRANSPORT_PROPOSAL_DESCRIPTION = "輸送提案を新規登録します";
        public static final String TRANSPORT_PROPOSAL_STATUS_OK_DESCRIPTION = "提案が正常に登録されました";

        public static final String SHIPPER_REQUEST = "/shipper";
        public static final String SHIPPER_REQUEST_SUMMARY = "輸送リクエスト検索";
        public static final String SHIPPER_REQUEST_DESCRIPTION = "輸送リクエスト検索";
        public static final String SHIPPER_REQUEST_STATUS_OK_DESCRIPTION = "輸送リクエストの検索に成功しました";

        public static final String SHIPPER_REQUEST_POST = "/shipper";
        public static final String SHIPPER_REQUEST_POST_SUMMARY = "輸送リクエストを提出する";
        public static final String SHIPPER_REQUEST_POST_DESCRIPTION = "輸送リクエストを提出する";
        public static final String SHIPPER_REQUEST_POST_STATUS_OK_DESCRIPTION = "輸送リクエストが正常に送信されました";

        public static final String LIST_TRANSACTION = "/carrier";
        public static final String LIST_TRANSACTION_SUMMARY = "取引一覧";
        public static final String LIST_TRANSACTION_DESCRIPTION = "取引一覧";
        public static final String LIST_TRANSACTION_STATUS_OK_DESCRIPTION = "取引一覧が正常に取得された場合のレスポンス状態";

        public static final String CARRIER_REQUEST_CARRIER = "/carrier/{order_id}/carrier";
        public static final String CARRIER_REQUEST_CARRIER_SUMMARY = "";
        public static final String CARRIER_REQUEST_CARRIER_DESCRIPTION = "";
        public static final String CARRIER_REQUEST_CARRIER_STATUS_OK_DESCRIPTION = "";

        public static final String CARRIER_GET_ITEM_CARRIER = "/carrier/{order_id}/carrier/{id}";
        public static final String CARRIER_GET_ITEM_CARRIER_SUMMARY = "";
        public static final String CARRIER_GET_ITEM_CARRIER_DESCRIPTION = "";
        public static final String CARRIER_GET_ITEM_CARRIER_STATUS_OK_DESCRIPTION = "";

        public static final String CARRIER_CARRIER_REQUEST = "/carrier/{order_id}/carrier/{id}";
        public static final String CARRIER_CARRIER_APPROVE = "/carrier2/{id}/approval";
        public static final String CARRIER_CARRIER_PAYMENT = "/carrier2/{id}/payment";
        public static final String CARRIER_CARRIER_CONTRACT = "/carrier2/{id}/contract";
        public static final String CARRIER_CARRIER_TRANSPORT = "/carrier2/{id}/transport";
        public static final String CARRIER_CARRIER_CANCEL = "/carrier2/{id}/cancel";

        public static final String TRANSACTION_DETAIL = "/{id}";

        public static final String TRANSPORT_PROPOSAL_IX = "/carrier_ix";
        public static final String TRANSPORT_PROPOSAL_IX_SUMMARY = "輸送提案登録";
        public static final String TRANSPORT_PROPOSAL_IX_DESCRIPTION = "輸送提案を新規登録します";
        public static final String TRANSPORT_PROPOSAL_IX_STATUS_OK_DESCRIPTION = "提案が正常に登録されました";

        public static final String SHIPPER_APPROVAL_IX = "/shipper/{id}/approval";
        public static final String SHIPPER_APPROVAL_IX_SUMMARY = "輸送提案登録";
        public static final String SHIPPER_APPROVAL_IX_DESCRIPTION = "輸送提案を新規登録します";
        public static final String SHIPPER_APPROVAL_IX_STATUS_OK_DESCRIPTION = "提案が正常に登録されました";

        public static final String SHIPPER_UPDATE_IX = "/shipper/{id}";
        public static final String SHIPPER_UPDATE_IX_SUMMARY = "輸送提案登録";
        public static final String SHIPPER_UPDATE_IX_DESCRIPTION = "輸送提案を新規登録します";
        public static final String SHIPPER_UPDATE_IX_STATUS_OK_DESCRIPTION = "提案が正常に登録されました";

        public static final String TRANSACTION_TIME = "/time/{target}";

        public static final String TRANSACTION_ID = "/id/{target}";
        public static final String TRANSACTION_CONTRACT = "/contract/{id}";
        public static final String TRANSACTION_PAYMENT = "/payment/{id}";
        public static final String TRANSACTION_APPROVAL = "/approval/{id}";

        public static final String TRANSACTION_CANCEL = "/cancel/{id}";
        public static final String TRANSACTION_SHIPPER_CONTRACT = "/shipper/{id}/contract";
        public static final String TRANSACTION_SHIPPER_PAYMENT = "/shipper/{id}/payment";
        public static final String TRANSACTION_SHIPPER_CANCEL = "/shipper/{id}/cancel";

        public static final String CARRIER_REQUEST_POST = "/carrier";
        public static final String CARRIER_REQUEST_POST_SUMMARY = "";
        public static final String CARRIER_REQUEST_POST_DESCRIPTION = "";
        public static final String CARRIER_REQUEST_POST_STATUS_OK_DESCRIPTION = "";

        public static final String CARRIER_FE_POST = "/carrier_fe";
        public static final String CARRIER_2_POST = "/carrier2";
        public static final String CARRIER_TRANSPORT_DECISION = "/transport_decision/{id}";
        public static final String CARRIER2_TRANSPORT_DECISION = "/carrier2/{id}/transport_decision";
        public static final String CARRIER_RE_PROPOSE = "/re_propose/{id}";
        public static final String UPDATE_ORDER_EMERGENCY = "/emergency";
    }

    public static class TransMatching {

        public static final String REQ_CNS_LINE_ITEM_MATCHING = API + "/req_cns_line_item/matching";
        public static final String VEHICLE_AVB_RESOURCE_MATCHING = API + "/vehicle_avb_resource/matching";
        public static final String CNS_LINE_ITEM_MATCHING = API + "/cns_line_item/matching";
        public static final String CARRIER_TRANSACTION_SHIPPER = API + "/carrier_transaction/shipper";
        public static final String CARRIER_TRANSACTION_SHIPPER_ID_APPROVAL =
            API + "/carrier_transaction/shipper/{id}/approval";
        public static final String CARRIER_TRANSACTION_SHIPPER_ID = API + "/carrier_transaction/shipper/{id}";
        public static final String CARRIER_GET_TRAILER_ID_MATCHING = API + "/vehicle_trailer_matching";
        public static final String GET_CUT_OFF_INFO = API + "/cut_off_info/{id}";
    }

    public static class CarrierTransportPlan {

        public static final String PREFIX = API + "/carrier_transport_plan";

        public static final String SEARCH = "/search";
    }

    public static class Market {

        public static final String BATCH_MARKET = API + "/market/update";
    }

    public static class Matching {

        public static final String PREFIX = API + "/matching";
        public static final String MATCHING_INTERVAL = "/{target}/{interval}";
    }

    public static class IXBurningTTMITracking {

        public static final String PREFIX = API + "/trip/trans_order/{id}/tracking";
        public static final String START = "/start";
        public static final String END = "/end";
        public static final String VALIDATE = "/validate";
        public static final String WEATHER = "/weather";
        public static final String UPDATE_TIME = "/update_time";
    }

    public static class IX {

        public static final String OPERATION_PLANS = "/operation_plans/";
        public static final String TRANSPORT_PLANS = "/transport_plans/";
    }

    public static class ZLWeb {

        public static final String PREFIX = API + "/zl_web/{id}";
    }

    public static class TransportOrder {

        public static final String PREFIX = API + "/transport-order";
        public static final String SENDMAIL = "/send-mail";
    }

    public static class CarrierDiagramItem {

        public static final String DIAGRAM_ITEM_TIME = "/api/v1/item/time/";
        public static final String DIAGRAM_ITEM_TIME_BY_DIAGRAM_ITEM_ID = "/api/v1/item/time/diagram_item/";
        public static final String UPDATE_DIAGRAM_ITEM_STATUS = "/api/v1/item/status";
    }
}