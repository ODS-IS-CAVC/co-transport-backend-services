package nlj.business.carrier.dto.vehicleDiagramItemTrailer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.DayDTO;

/**
 * <PRE>
 * 車両図面アイテムトレーラー検索レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramITemTrailerResDTO {

    @JsonProperty("vehicle_diagram_allocation_id")
    private Long vehicleDiagramAllocationId;

    @JsonProperty("freight_rate_type")
    private Integer freightRateType;

    @JsonProperty("days")
    private List<DayDTO> days;
}
