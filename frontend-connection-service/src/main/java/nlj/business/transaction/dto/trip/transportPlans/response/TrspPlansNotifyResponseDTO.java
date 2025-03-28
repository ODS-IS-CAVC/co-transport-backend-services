package nlj.business.transaction.dto.trip.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.trip.transportPlans.TrspPlansNotifyResDTO;

/**
 * <PRE>
 * 運送業者プラン通知レスポンスDTO。<BR>
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
