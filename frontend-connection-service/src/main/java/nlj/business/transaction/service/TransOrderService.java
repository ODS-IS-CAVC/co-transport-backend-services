package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.ShipperTransactionDTO;
import nlj.business.transaction.dto.TransOrderDTO;
import nlj.business.transaction.dto.TransactionDetailResponseDTO;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.request.CarrierOperationApprovalRequestDTO;
import nlj.business.transaction.dto.request.CarrierOperatorPlansRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalSearch;
import nlj.business.transaction.dto.request.OrderEmergencyUpdateRequest;
import nlj.business.transaction.dto.request.SendMailRequest;
import nlj.business.transaction.dto.request.TransMatchingRequest;
import nlj.business.transaction.dto.request.TransactionCarrier2Request;
import nlj.business.transaction.dto.request.TransactionCarrierFERequest;
import nlj.business.transaction.dto.request.TransactionCarrierRequest;
import nlj.business.transaction.dto.request.TransactionShipperApprovalRequest;
import nlj.business.transaction.dto.request.TransactionShipperSearch;
import nlj.business.transaction.dto.request.TransactionShipperUpdateRequest;
import nlj.business.transaction.dto.response.CarrierOperatorPlansInsertResponse;
import nlj.business.transaction.dto.response.CarrierProposalItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * トランザクションサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface TransOrderService {

    /**
     * シッパー取引を挿入する。
     *
     * @param transMatchingRequest シッパー取引のリクエスト
     * @param httpServletRequest   HTTPリクエスト
     * @return 挿入された取引のID
     */
    BigInteger insertShipperTransaction(TransMatchingRequest transMatchingRequest,
        HttpServletRequest httpServletRequest);

    /**
     * キャリア取引を挿入する。
     *
     * @param transMatchingRequest キャリア取引のリクエスト
     * @param httpServletRequest   HTTPリクエスト
     * @return 挿入された取引のID
     */
    Long insertCarrierTransaction(TransMatchingRequest transMatchingRequest, HttpServletRequest httpServletRequest);

    /**
     * ページネーションされたシッパー取引を取得する。
     *
     * @param searchRequest 検索リクエスト
     * @return ページネーションされたシッパー取引のレスポンス
     */
    PageResponseDTO<ShipperTransactionDTO> getPagedShipperTransaction(TransactionShipperSearch searchRequest);

    /**
     * キャリア輸送提案を作成する。
     *
     * @param request            キャリア輸送提案のリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 作成された提案のID
     */
    Long createCarrierTransportProposal(CarrierTransportProposalRequest request, HttpServletRequest httpServletRequest);

    /**
     * 取引のステータスを更新する。
     *
     * @param id                 取引のID
     * @param httpServletRequest HTTPリクエスト
     */
    void updateStatusTransaction(Long id, HttpServletRequest httpServletRequest);

    /**
     * キャリア承認の取引ステータスを更新する。
     *
     * @param id                 取引のID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     */
    void updateStatusTransactionCarrierApproval(Long id, boolean approval, HttpServletRequest httpServletRequest);

    /**
     * キャリア支払いの取引ステータスを更新する。
     *
     * @param id                 取引のID
     * @param httpServletRequest HTTPリクエスト
     */
    void updateStatusTransactionCarrierPayment(Long id, HttpServletRequest httpServletRequest);

    /**
     * キャリア契約の取引ステータスを更新する。
     *
     * @param id                 取引のID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     */
    void updateStatusTransactionCarrierContract(Long id, boolean approval, HttpServletRequest httpServletRequest);

    /**
     * キャリア輸送の取引ステータスを更新する。
     *
     * @param id                 取引のID
     * @param status             ステータス
     * @param httpServletRequest HTTPリクエスト
     */
    void updateStatusTransactionCarrierTransport(Long id, String status, HttpServletRequest httpServletRequest);

    /**
     * ページネーションされたキャリア提案を取得する。
     *
     * @param searchRequest 検索リクエスト
     * @return ページネーションされたキャリア提案のレスポンス
     */
    PageResponseDTO<TransOrderDTO> getPagedCarrierProposal(CarrierTransportProposalSearch searchRequest);

    /**
     * キャリア提案アイテムを取得する。
     *
     * @param transOrderId 取引のID
     * @return キャリア提案アイテムのレスポンス
     */
    CarrierProposalItemResponseDTO getCarrierProposalItem(Long transOrderId);

    /**
     * 取引の詳細を取得する。
     *
     * @param id 取引のID
     * @return 取引の詳細レスポンス
     */
    TransactionDetailResponseDTO getDetailTransaction(String id);

    /**
     * シッパーのキャリア取引を作成する。
     *
     * @param isNotIX            IXでないかのフラグ
     * @param request            キャリアオペレータープランのリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return キャリアオペレータープランの挿入レスポンス
     */
    CarrierOperatorPlansInsertResponse carrierTransactionShipper(boolean isNotIX, CarrierOperatorPlansRequest request,
        HttpServletRequest httpServletRequest);

    /**
     * IX用のキャリア輸送提案を作成する。
     *
     * @param requestData        リクエストデータ
     * @param httpServletRequest HTTPリクエスト
     * @return 作成された提案のID
     */
    BigInteger createCarrierTransportProposalIX(TransactionCarrierRequest requestData,
        HttpServletRequest httpServletRequest);

    /**
     * シッパーのキャリア取引を承認する。
     *
     * @param isNotIX            IXでないかのフラグ
     * @param id                 取引のID
     * @param request            承認リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return キャリアオペレータープランの挿入レスポンス
     */
    CarrierOperatorPlansInsertResponse carrierTransactionShipperIdApproval(boolean isNotIX, String id,
        CarrierOperationApprovalRequestDTO request, HttpServletRequest httpServletRequest);

    /**
     * シッパーのキャリア取引を取得する。
     *
     * @param isNotIX            IXでないかのフラグ
     * @param id                 取引のID
     * @param request            キャリアオペレータープランのリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return キャリアオペレータープランのレスポンス
     */
    CarrierOperatorPlansInsertResponse carrierTransactionShipperId(boolean isNotIX, String id,
        CarrierOperatorPlansRequest request, HttpServletRequest httpServletRequest);

    /**
     * IX用のシッパー提案を承認する。
     *
     * @param requestData        リクエストデータ
     * @param httpServletRequest HTTPリクエスト
     * @return 承認された提案のID
     */
    Long approveShipperProposalIX(TransactionShipperApprovalRequest requestData, HttpServletRequest httpServletRequest);

    /**
     * IX用のシッパー提案を更新する。
     *
     * @param requestData        更新リクエストデータ
     * @param paramId            パラメータID
     * @param httpServletRequest HTTPリクエスト
     * @return 更新された提案のID
     */
    Long updateShipperProposalIX(TransactionShipperUpdateRequest requestData, String paramId,
        HttpServletRequest httpServletRequest);

    /**
     * 注文のステータスを更新する。
     *
     * @param id                 注文のID
     * @param status             ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateOrderStatus(Long id, Integer status, HttpServletRequest httpServletRequest);

    /**
     * 注文の承認ステータスを更新する。
     *
     * @param id                 注文のID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateOrderApprovalStatus(Long id, boolean approval, HttpServletRequest httpServletRequest);

    /**
     * 注文の契約ステータスを更新する。
     *
     * @param id                 注文のID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateOrderContractStatus(Long id, boolean approval, HttpServletRequest httpServletRequest);

    /**
     * シッパーの注文ステータスを更新する。
     *
     * @param id                 注文のID
     * @param status             ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateShipperOrderStatus(String id, Integer status, HttpServletRequest httpServletRequest);

    /**
     * シッパーの契約ステータスを更新する。
     *
     * @param id                 注文のID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateShipperContractStatus(String id, boolean approval, HttpServletRequest httpServletRequest);

    /**
     * トレーラーによる取引を取得する。
     *
     * @param transType        取引タイプ
     * @param advanceStatus    進行状況リスト
     * @param status           ステータス
     * @param freeWord         フリーワード
     * @param temperatureRange 温度範囲リスト
     * @param page             ページ番号
     * @param limit            ページサイズ
     * @param isEmergency      緊急フラグ
     * @return トレーラーによる取引のページ
     */
    Page<TransMatchingHeadResponse> getTransactionByTrailer(String transType, List<String> advanceStatus, String status,
        String freeWord, List<Integer> temperatureRange, int page, int limit, Integer isEmergency);

    /**
     * FE用のキャリア輸送提案を作成する。
     *
     * @param requestData        リクエストデータ
     * @param httpServletRequest HTTPリクエスト
     * @return 作成された提案のID
     */
    Long createCarrierTransportProposalFE(TransactionCarrierFERequest requestData,
        HttpServletRequest httpServletRequest);

    /**
     * キャリア2の輸送提案を作成する。
     *
     * @param requestData リクエストデータ
     * @return 作成された提案のID
     */
    Long createCarrier2TransportProposal(TransactionCarrier2Request requestData);

    /**
     * キャリア輸送の決定を更新する。
     *
     * @param orderIdString      注文IDの文字列
     * @param updateOrderStatus  更新するステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateCarrierTransportDecision(String orderIdString, Integer updateOrderStatus,
        HttpServletRequest httpServletRequest);

    /**
     * キャリアの再提案を更新する。
     *
     * @param orderIdString      注文IDの文字列
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    Long updateCarrierRePropose(String orderIdString, HttpServletRequest httpServletRequest);

    /**
     * アイテムトレーラーIDによる全データを取得する。
     *
     * @param ids トレーラーIDのリスト
     * @return 取得したデータ
     */
    String findAllByItemTrailerIds(List<Long> ids);

    /**
     * 注文の緊急更新を行う。
     *
     * @param requestData 更新リクエストデータ
     */
    void updateOrderEmergency(OrderEmergencyUpdateRequest requestData);

    /**
     * IXメールを送信する。
     *
     * @param sendMailRequest メール送信リクエスト
     */
    void sendIXMail(SendMailRequest sendMailRequest);
}
