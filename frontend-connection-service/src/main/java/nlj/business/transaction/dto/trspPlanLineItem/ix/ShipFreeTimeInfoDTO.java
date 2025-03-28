package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 船のフリータイム情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFreeTimeInfoDTO {

    @JsonProperty("free_time")
    private BigDecimal freeTime;
    @JsonProperty("free_time_fee")
    private BigDecimal freeTimeFee;
}
