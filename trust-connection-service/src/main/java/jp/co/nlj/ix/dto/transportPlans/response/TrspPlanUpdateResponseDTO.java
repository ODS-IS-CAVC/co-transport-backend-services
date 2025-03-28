package jp.co.nlj.ix.dto.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.transportPlans.TrspPlansDTO;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画更新応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanUpdateResponseDTO {

    private TrspPlansDTO transportPlans;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
