package jp.co.nlj.ix.dto.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.transportPlans.TrspPlansNotifyResDTO;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画通知応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansNotifyResponseDTO {

    @JsonProperty("transport_plans")
    private TrspPlansNotifyResDTO transportPlans;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
