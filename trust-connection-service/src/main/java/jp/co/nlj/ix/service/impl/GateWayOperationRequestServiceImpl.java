package jp.co.nlj.ix.service.impl;

import com.next.logistic.data.config.CommonDataConfigProperties;
import jp.co.nlj.ix.constant.APIConstant.OperationRequest;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestInsertReqDTO;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestNotifyRequest;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestReplyRequest;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestInsertResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestNotifyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestReplyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestUpdateResponse;
import jp.co.nlj.ix.service.GateWayOperationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 操作通知サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class GateWayOperationRequestServiceImpl implements GateWayOperationRequestService {

    @Autowired
    private CommonDataConfigProperties commonDataConfigProperties;


    /**
     * 輸送計画を挿入する。
     *
     * @param requestDTO 輸送計画のリクエストDTO
     * @param paramUrl   パラメータURL
     * @return 輸送計画挿入のレスポンス
     */
    @Override
    public OperationRequestInsertResponse insertPlans(OperationRequestInsertReqDTO requestDTO, String paramUrl) {
        String url =
            commonDataConfigProperties.getDomainIX() + OperationRequest.PREFIX_WEB_API + OperationRequest.INSERT
                + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationRequestInsertReqDTO> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationRequestInsertResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
            OperationRequestInsertResponse.class);
        return response.getBody();
    }

    /**
     * 輸送計画を更新する。
     *
     * @param requestDTO  輸送計画のリクエストDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 輸送計画更新のレスポンス
     */
    @Override
    public OperationRequestUpdateResponse updatePlan(OperationRequestInsertReqDTO requestDTO, String operationId,
        String proposeId, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + OperationRequest.PREFIX_WEB_API + "/" + operationId
            + OperationRequest.INSERT + "/" + proposeId + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationRequestInsertReqDTO> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationRequestUpdateResponse> response = restTemplate.exchange(url, HttpMethod.PUT, entity,
            OperationRequestUpdateResponse.class);
        return response.getBody();
    }

    /**
     * 輸送計画の通知を送信する。
     *
     * @param requestDTO  輸送計画通知のリクエストDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 輸送計画通知のレスポンス
     */
    @Override
    public OperationRequestNotifyResponse planNotify(OperationRequestNotifyRequest requestDTO, String operationId,
        String proposeId, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + OperationRequest.PREFIX_WEB_API + "/" + operationId
            + OperationRequest.INSERT + "/" + proposeId + OperationRequest.NOTIFY_WEB + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationRequestNotifyRequest> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationRequestNotifyResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
            OperationRequestNotifyResponse.class);
        return response.getBody();
    }

    /**
     * 輸送計画に対する返信を送信する。
     *
     * @param requestDTO  輸送計画返信のリクエストDTO
     * @param operationId 操作ID
     * @param proposeId   提案ID
     * @param paramUrl    パラメータURL
     * @return 輸送計画返信のレスポンス
     */
    @Override
    public OperationRequestReplyResponse planReply(OperationRequestReplyRequest requestDTO, String operationId,
        String proposeId, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + OperationRequest.PREFIX_WEB_API + "/" + operationId
            + OperationRequest.INSERT + "/" + proposeId + OperationRequest.REPLY_WEB + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationRequestReplyRequest> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationRequestReplyResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
            OperationRequestReplyResponse.class);
        return response.getBody();
    }
}
