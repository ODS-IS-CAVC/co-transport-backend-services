package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 高度な検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvancedRequest {

    @JsonProperty("day_week")
    private List<Integer> dayWeek;

    @JsonProperty("freight_rate_max")
    private Double freightRateMax;

    @JsonProperty("freight_rate_min")
    private Double freightRateMin;

    @JsonProperty("cid")
    private String cid;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("temperature_range")
    private List<Integer> temperatureRange;
}
