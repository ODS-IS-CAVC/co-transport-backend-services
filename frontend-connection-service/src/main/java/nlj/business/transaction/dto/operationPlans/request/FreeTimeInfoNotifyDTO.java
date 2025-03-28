package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 空き時間情報通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class FreeTimeInfoNotifyDTO {

    @JsonProperty("free_time")
    private String freeTime;

    @JsonProperty("free_time_fee")
    private String freeTimeFee;
}
