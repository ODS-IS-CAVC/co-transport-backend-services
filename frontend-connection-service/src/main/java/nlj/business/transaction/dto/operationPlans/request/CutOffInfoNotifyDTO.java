package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 切り捨て情報通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CutOffInfoNotifyDTO {

    @JsonProperty("cut_off_time")
    private String cutOffTime;

    @JsonProperty("cut_off_fee")
    private String cutOffFee;
}
