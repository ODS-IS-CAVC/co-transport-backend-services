package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant.ShipCutOffInfo;

/**
 * <PRE>
 * 出荷停止情報 DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipCutOffInfoDTO {

    @JsonProperty(ShipCutOffInfo.CUT_OFF_TIME)
    @ValidateField(precision = 5, scale = 1, textHalfWidth = true)
    private BigDecimal cutOffTime;
    @JsonProperty(ShipCutOffInfo.CUT_OFF_FEE)
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private BigDecimal cutOffFee;
}
