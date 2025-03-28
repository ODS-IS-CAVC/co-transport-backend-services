package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlanUpdateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansCreateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansNotifyResponseDTO;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 輸送計画サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface GateWayTrspPlansService {

    /**
     * 輸送計画を更新する。
     *
     * @param trspInstructionId   輸送指示ID
     * @param trspPlansRequestDTO 輸送計画リクエストDTO
     * @param paramUrl            パラメータURL
     * @return 更新結果DTO
     */
    TrspPlanUpdateResponseDTO updateTrspPlans(String trspInstructionId, TrspPlansRequestDTO trspPlansRequestDTO,
        String paramUrl);

    /**
     * 輸送計画を挿入する。
     *
     * @param trspInstructionId   輸送指示ID
     * @param trspPlansRequestDTO 輸送計画リクエストDTO
     * @param paramUrl            パラメータURL
     * @return 作成結果DTO
     */
    TrspPlansCreateResponseDTO insertTrspPlans(String trspInstructionId, TrspPlansRequestDTO trspPlansRequestDTO,
        String paramUrl);

    /**
     * 輸送計画通知を挿入する。
     *
     * @param trspInstructionId         輸送指示ID
     * @param trspPlansNotifyRequestDTO 輸送計画通知リクエストDTO
     * @param paramUrl                  パラメータURL
     * @return 通知結果DTO
     */
    TrspPlansNotifyResponseDTO insertTrspPlansNotify(String trspInstructionId,
        TrspPlansNotifyRequestDTO trspPlansNotifyRequestDTO, String paramUrl);
}
