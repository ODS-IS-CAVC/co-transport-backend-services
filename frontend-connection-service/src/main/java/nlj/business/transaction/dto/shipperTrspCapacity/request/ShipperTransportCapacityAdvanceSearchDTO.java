package nlj.business.transaction.dto.shipperTrspCapacity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送業者運送能力事前検索DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipperTransportCapacityAdvanceSearchDTO {

    @JsonProperty("day_week")
    private List<Integer> dayWeek;

    @JsonProperty("freight_rate_max")
    private BigDecimal freightRateMax;

    @JsonProperty("freight_rate_min")
    private BigDecimal freightRateMin;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("temperature_range")
    private List<Integer> temperatureRange;
}
