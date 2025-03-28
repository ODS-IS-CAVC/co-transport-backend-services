package jp.co.nlj.ix.service.impl;

import com.next.logistic.data.config.CommonDataConfigProperties;
import jp.co.nlj.ix.constant.APIConstant.OperationPlans;
import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansInsertResponse;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansNotifyResponse;
import jp.co.nlj.ix.service.GateWayOperationPlansService;
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
public class GateWayOperationPlansServiceImpl implements GateWayOperationPlansService {

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
    public OperationPlansInsertResponse insertPlans(OperationPlansRequestDTO requestDTO, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + OperationPlans.PREFIX_WEB + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationPlansRequestDTO> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationPlansInsertResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
            OperationPlansInsertResponse.class);
        return response.getBody();
    }

    /**
     * 操作通知を送信する。
     *
     * @param requestDTO  操作通知のリクエストDTO
     * @param operationId 操作ID
     * @param paramUrl    パラメータURL
     * @return 操作通知のレスポンス
     */
    @Override
    public OperationPlansNotifyResponse pushNotify(OperationPlansNotifyRequestDTO requestDTO, String operationId,
        String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + OperationPlans.PREFIX_WEB + operationId
            + OperationPlans.NOTIFY_WEB + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<OperationPlansNotifyRequestDTO> entity = new HttpEntity<>(requestDTO);

        // Send request
        ResponseEntity<OperationPlansNotifyResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
            OperationPlansNotifyResponse.class);
        return response.getBody();
    }
}
