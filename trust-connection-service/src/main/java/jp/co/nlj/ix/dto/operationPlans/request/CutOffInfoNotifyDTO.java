package jp.co.nlj.ix.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知リクエストDTO。<BR>
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
