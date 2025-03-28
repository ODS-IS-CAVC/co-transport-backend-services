package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansInsertResponse;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansNotifyResponse;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 稼働通知サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface GateWayOperationPlansService {

    /**
     * 稼働計画を挿入する。
     *
     * @param requestDTO 挿入する稼働計画のリクエストDTO
     * @param paramUrl   パラメータURL
     * @return 挿入結果
     */
    OperationPlansInsertResponse insertPlans(OperationPlansRequestDTO requestDTO, String paramUrl);

    /**
     * 稼働通知を送信する。
     *
     * @param requestDTO  通知する稼働計画のリクエストDTO
     * @param operationId 操作ID
     * @param paramUrl    パラメータURL
     * @return 通知結果
     */
    OperationPlansNotifyResponse pushNotify(OperationPlansNotifyRequestDTO requestDTO, String operationId,
        String paramUrl);
}
