package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationReserveRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationUpdateRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationReserveResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationUpdateResponseDTO;

/**
 * <PRE>
 * 荷主向け運行申し込み諾否回答通知サービスインターフェース
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipperOperationPlansService {

    /**
     * 荷主の運行申し込みに対する承認を行う。
     *
     * @param shipperOperationApprovalRequestDTO 荷主運行申し込み承認リクエストDTO
     * @param operationIdParam                   運行IDパラメータ
     * @param proposeId                          提案ID
     * @return 荷主運行申し込み承認レスポンスDTO
     */
    ShipperOperationApprovalResponseDTO shipperOperationApproval(
        ShipperOperationApprovalRequestDTO shipperOperationApprovalRequestDTO,
        String operationIdParam, String proposeId);

    /**
     * 荷主の運行申し込みを更新する。
     *
     * @param shipperOperationUpdateRequestDTO 荷主運行申し込み更新リクエストDTO
     * @param operationIdParam                 運行IDパラメータ
     * @param proposeId                        提案ID
     * @return 荷主運行申し込み更新レスポンスDTO
     */
    ShipperOperationUpdateResponseDTO shipperOperationUpdate(
        ShipperOperationUpdateRequestDTO shipperOperationUpdateRequestDTO,
        String operationIdParam, String proposeId);

    /**
     * 荷主の運行申し込みを予約する。
     *
     * @param shipperOperationApplyRequestDTO 荷主運行申し込み予約リクエストDTO
     * @return 荷主運行申し込み予約レスポンスDTO
     */
    ShipperOperationReserveResponseDTO shipperOperationReserve(
        ShipperOperationReserveRequestDTO shipperOperationApplyRequestDTO);
}
