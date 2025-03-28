package nlj.business.carrier.dto.vehicleDiagramItemTrailer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムトレーラーDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrailerDTO {

    @JsonProperty("vehicle_diagram_allocation_id")
    private Long vehicleDiagramAllocationId;

    @JsonProperty("freight_rate_type")
    private Integer freightRateType;

    @JsonProperty("days")
    private List<DayDTO> days;

}
