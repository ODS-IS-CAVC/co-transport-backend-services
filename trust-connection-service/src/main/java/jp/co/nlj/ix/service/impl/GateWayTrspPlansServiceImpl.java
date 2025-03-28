package jp.co.nlj.ix.service.impl;

import com.next.logistic.data.config.CommonDataConfigProperties;
import jp.co.nlj.ix.constant.APIConstant.TrspPlans;
import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlanUpdateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansCreateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansNotifyResponseDTO;
import jp.co.nlj.ix.service.GateWayTrspPlansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 輸送計画サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GateWayTrspPlansServiceImpl implements GateWayTrspPlansService {

    @Autowired
    private CommonDataConfigProperties commonDataConfigProperties;

    /**
     * 輸送計画を更新する。
     *
     * @param trspInstructionId   輸送指示ID
     * @param trspPlansRequestDTO 輸送計画リクエストDTO
     * @param paramUrl            パラメータURL
     * @return 更新結果
     */
    @Override
    public TrspPlanUpdateResponseDTO updateTrspPlans(String trspInstructionId,
        TrspPlansRequestDTO trspPlansRequestDTO, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + TrspPlans.PREFIX + trspInstructionId + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TrspPlansRequestDTO> entity = new HttpEntity<>(trspPlansRequestDTO);
        try {
            log.info("Start call update trsp plans IX External: " + trspPlansRequestDTO);
            ResponseEntity<TrspPlanUpdateResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, TrspPlanUpdateResponseDTO.class);
            log.info("End call success update trsp plans IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call update trsp plans IX External ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * 輸送計画を挿入する。
     *
     * @param trspInstructionId   輸送指示ID
     * @param trspPlansRequestDTO 輸送計画リクエストDTO
     * @param paramUrl            パラメータURL
     * @return 挿入結果
     */
    @Override
    public TrspPlansCreateResponseDTO insertTrspPlans(String trspInstructionId,
        TrspPlansRequestDTO trspPlansRequestDTO, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + TrspPlans.PREFIX + trspInstructionId + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TrspPlansRequestDTO> entity = new HttpEntity<>(trspPlansRequestDTO);
        try {
            log.info("Start call insert trsp plans IX External: " + trspPlansRequestDTO);
            ResponseEntity<TrspPlansCreateResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, TrspPlansCreateResponseDTO.class);
            log.info("End call success insert trsp plans IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call insert trsp plans IX External ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * 輸送計画通知を挿入する。
     *
     * @param trspInstructionId         輸送指示ID
     * @param trspPlansNotifyRequestDTO 輸送計画通知リクエストDTO
     * @param paramUrl                  パラメータURL
     * @return 通知結果
     */
    @Override
    public TrspPlansNotifyResponseDTO insertTrspPlansNotify(String trspInstructionId,
        TrspPlansNotifyRequestDTO trspPlansNotifyRequestDTO, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + TrspPlans.PREFIX + trspInstructionId + TrspPlans.NOTIFY
            + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TrspPlansNotifyRequestDTO> entity = new HttpEntity<>(trspPlansNotifyRequestDTO);
        try {
            log.info("Start call insert trsp plans notify IX External: " + trspPlansNotifyRequestDTO);
            ResponseEntity<TrspPlansNotifyResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, TrspPlansNotifyResponseDTO.class);
            log.info("End call success insert trsp plans notify IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call insert trsp plans notify IX External ERROR: " + e.getMessage());
        }
        return null;
    }
}
