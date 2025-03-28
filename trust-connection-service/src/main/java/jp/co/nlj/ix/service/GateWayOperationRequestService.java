package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestInsertReqDTO;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestNotifyRequest;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestReplyRequest;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestInsertResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestNotifyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestReplyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestUpdateResponse;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 稼働通知サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface GateWayOperationRequestService {

    /**
     * プランを挿入する。
     *
     * @param requestDTO 挿入するプランのDTO
     * @param paramUrl   パラメータURL
     * @return 挿入結果
     */
    OperationRequestInsertResponse insertPlans(OperationRequestInsertReqDTO requestDTO, String paramUrl);

    /**
     * プランを更新する。
     *
     * @param requestDTO  更新するプランのDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 更新結果
     */
    OperationRequestUpdateResponse updatePlan(OperationRequestInsertReqDTO requestDTO, String operationId,
        String proposeId, String paramUrl);

    /**
     * プラン通知を行う。
     *
     * @param requestDTO  通知するプランのDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 通知結果
     */
    OperationRequestNotifyResponse planNotify(OperationRequestNotifyRequest requestDTO, String operationId,
        String proposeId, String paramUrl);

    /**
     * プランに対する返信を行う。
     *
     * @param requestDTO  返信するプランのDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 返信結果
     */
    OperationRequestReplyResponse planReply(OperationRequestReplyRequest requestDTO, String operationId,
        String proposeId, String paramUrl);
}
