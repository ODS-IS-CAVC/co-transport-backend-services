package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 配送時間更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTimeUpdateRequest {
    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    @JsonProperty("management_number")
    private String managementNumber;
}
