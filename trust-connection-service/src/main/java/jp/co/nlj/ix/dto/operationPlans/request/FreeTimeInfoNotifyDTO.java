package jp.co.nlj.ix.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * フリータイム情報通知リクエストDTO。<BR>
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
