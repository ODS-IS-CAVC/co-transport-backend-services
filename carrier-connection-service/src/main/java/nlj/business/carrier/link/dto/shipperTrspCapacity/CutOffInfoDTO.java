package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 切断情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CutOffInfoDTO {

    @JsonProperty("cut_off_time")
    @ValidateField(precision = 10, scale = 1, textHalfWidth = true)
    private String cutOffTime;

    @JsonProperty("cut_off_fee")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String cutOffFee;
}
