package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷停止情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipCutOffInfoDTO {

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;
    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;
}
