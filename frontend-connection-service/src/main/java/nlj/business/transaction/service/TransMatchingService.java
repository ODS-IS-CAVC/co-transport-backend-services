package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.CutOffInfo;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.VehicleAvbResourceItemDTO;
import nlj.business.transaction.dto.matching.TransMatchingAbilityDetailResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import nlj.business.transaction.dto.matching.request.MatchingIdRequest;
import nlj.business.transaction.dto.request.CarrierVehicleDiagramGetRequest;
import nlj.business.transaction.dto.response.CarrierVehicleDiagramGetResponse;
import org.springframework.data.domain.Page;

/**
 * <PRE>
 * 配送マッチング。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMatchingService {

    /**
     * 配送計画のマッチングを取得する。
     *
     * @param freeWord         フリーワード
     * @param temperatureRange 温度範囲
     * @param offset           オフセット
     * @param limit            リミット
     * @return マッチング結果のページ
     */
    Page<TransMatchingDTOResponse> getTransPlanMatching(String freeWord, List<Integer> temperatureRange, int offset,
        int limit);

    /**
     * IDによる配送計画のマッチングを取得する。
     *
     * @param cnsLineItemByDateId CNSラインアイテムの日付ID
     * @return マッチング結果のリスト
     */
    List<TrspPlanIdDTOResponse> getTransPlanMatchingById(Long cnsLineItemByDateId);

    /**
     * トレーラーIDによる配送マッチングの詳細を取得する。
     *
     * @param vehicleAvbResourceId 車両の利用可能リソースID
     * @param transType            輸送タイプ
     * @return マッチング能力の詳細
     */
    TransMatchingAbilityDetailResponse getTransMatchingByTrailerId(Long vehicleAvbResourceId, String transType);

    /**
     * 輸送計画IDによるマッチングを実行する。
     *
     * @param transportPlanId    輸送計画ID
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> matchByTransportPlanId(Long transportPlanId, HttpServletRequest httpServletRequest);

    /**
     * CNSラインアイテムIDによるマッチングを実行する。
     *
     * @param cnsLineItemId      CNSラインアイテムID
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> matchByCnsLineItemId(Long cnsLineItemId, HttpServletRequest httpServletRequest);

    /**
     * 車両利用可能リソースアイテムIDによるマッチングを実行する。
     *
     * @param vehicleAvbResourceItem 車両利用可能リソースアイテムID
     * @param httpServletRequest     HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> matchByVehicleAvbResourceItemId(Long vehicleAvbResourceItem,
        HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラムIDによるマッチングを実行する。
     *
     * @param vehicleDiagramId   車両ダイアグラムID
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> executeMatching(Long vehicleDiagramId, HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラムアイテムIDによるマッチングを実行する。
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @return マッチング結果のリスト
     */
    List<TransMatching> executeMatchingItem(Long vehicleDiagramItemId);

    /**
     * 複数の配送マッチングを挿入する。
     *
     * @param transMatchingDTOS 配送マッチングのDTOリスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> inserTransMatchings(List<nlj.business.transaction.dto.TransMatchingDTO> transMatchingDTOS);

    /**
     * キャリアの注文IDを販売に挿入する。
     *
     * @param orderId            注文ID
     * @param httpServletRequest HTTPリクエスト
     */
    void insertCarrierOrderIdSale(Long orderId, HttpServletRequest httpServletRequest);

    /**
     * キャリアの注文IDをキャンセルする。
     *
     * @param orderId            注文ID
     * @param httpServletRequest HTTPリクエスト
     */
    void carrierOrderIdCancel(Long orderId, HttpServletRequest httpServletRequest);

    /**
     * キャリアの注文IDをマッチングに挿入する。
     *
     * @param orderId            注文ID
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のIDリスト
     */
    List<Long> insertCarrierOrderIdMatching(Long orderId, HttpServletRequest httpServletRequest);

    /**
     * キャリアとキャリアパッケージをマッチングする。
     *
     * @param cnsLineItemByDate CNSラインアイテムの日付
     * @return マッチング結果のリスト
     */
    List<TransMatching> matchCarrierWithCarrierPackage(CnsLineItemByDate cnsLineItemByDate);

    /**
     * CNSラインアイテムのマッチングを要求する。
     *
     * @param matchingIdRequest  マッチングIDリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> reqCnsLineItemMatching(MatchingIdRequest matchingIdRequest,
        HttpServletRequest httpServletRequest);

    /**
     * 車両利用可能リソースのマッチングを実行する。
     *
     * @param matchingIdRequest  マッチングIDリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> vehicleAvbResourceMatching(MatchingIdRequest matchingIdRequest,
        HttpServletRequest httpServletRequest);

    /**
     * CNSラインアイテムのマッチングを実行する。
     *
     * @param matchingIdRequest  マッチングIDリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> cnsLineItemMatching(MatchingIdRequest matchingIdRequest, HttpServletRequest httpServletRequest);

    /**
     * 出荷者の注文IDを販売に挿入する。
     *
     * @param orderId            注文ID
     * @param httpServletRequest HTTPリクエスト
     * @return 車両利用可能リソースアイテムDTOのリスト
     */
    List<VehicleAvbResourceItemDTO> insertShipperOrderIdSale(Long orderId, HttpServletRequest httpServletRequest);

    /**
     * トレーラーによるマッチングを取得する。
     *
     * @param transType        輸送タイプ
     * @param freeWord         フリーワード
     * @param temperatureRange 温度範囲
     * @param page             ページ番号
     * @param limit            リミット
     * @param isEmergency      緊急フラグ
     * @return マッチング結果のページ
     */
    Page<TransMatchingHeadResponse> getMatchingByTrailer(String transType, String freeWord,
        List<Integer> temperatureRange, int page, int limit, Integer isEmergency);

    /**
     * トレーラーによるキャリアのマッチングを取得する。
     *
     * @param orderId 注文ID
     * @param page    ページ番号
     * @param limit   リミット
     * @return マッチング結果のページ
     */
    Page<TransMatchingHeadResponse> getMatchingCarrierByTrailer(Long orderId, int page, int limit);

    /**
     * 輸送能力の販売を取得する。
     *
     * @param transType        輸送タイプ
     * @param temperatureRange 温度範囲
     * @param page             ページ番号
     * @param limit            リミット
     * @return 輸送能力の販売結果のページ
     */
    Page<TransMatchingAbilitySaleHeadResponse> getTransportAbilitySale(String transType, List<Integer> temperatureRange,
        int page, int limit);

    /**
     * トレーラーによる輸送マッチングを取得する。
     *
     * @param request 車両ダイアグラム取得リクエスト
     * @return 輸送マッチングのレスポンス
     */
    CarrierVehicleDiagramGetResponse getTransportMatchingByTrailer(CarrierVehicleDiagramGetRequest request);

    /**
     * 出荷者とキャリアをマッチングする。
     *
     * @param cnsLineItemByDate CNSラインアイテムの日付
     * @return マッチング結果のリスト
     */
    List<TransMatching> shipperMatchingCarrier(CnsLineItemByDate cnsLineItemByDate);

    /**
     * キャリアと出荷者をマッチングする。
     *
     * @param vehicleAvbResourceItem 車両利用可能リソースアイテム
     * @param cnsLineItemByDateIds   CNSラインアイテムの日付IDのリスト
     * @return マッチング結果のリスト
     */
    List<TransMatching> carrierMatchingShipper(VehicleAvbResourceItem vehicleAvbResourceItem,
        List<Long> cnsLineItemByDateIds);

    /**
     * カットオフ情報を取得する。
     *
     * @param vehicleAvbResoureId 車両利用可能リソースID
     * @return カットオフ情報のリスト
     */
    List<CutOffInfo> getCufOffInfo(Long vehicleAvbResoureId);

    /**
     * フリーワードによる配送マッチングを挿入する。
     *
     * @param cnsLineItemByDate      CNSラインアイテムの日付
     * @param vehicleAvbResourceItem 車両利用可能リソースアイテム
     * @return マッチング結果の文字列
     */
    String insertFreeWordTransMatching(CnsLineItemByDate cnsLineItemByDate,
        VehicleAvbResourceItem vehicleAvbResourceItem);

    /**
     * フリーワードを正規化する。
     *
     * @param freeword フリーワード
     * @return 正規化されたフリーワード
     */
    String normalizeFreeword(String freeword);
}
