package nlj.business.transaction.dto.trip.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.trip.transportPlans.TrspPlansDTO;

/**
 * <PRE>
 * 運送業者プラン更新レスポンスDTO。<BR>
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
