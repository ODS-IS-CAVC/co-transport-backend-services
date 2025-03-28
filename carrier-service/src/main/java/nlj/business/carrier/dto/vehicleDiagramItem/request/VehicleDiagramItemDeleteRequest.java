package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテム削除リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemDeleteRequest {

    @JsonProperty("vehicle_item_trailer_id")
    private List<Long> vehicleItemTrailerId;
    @JsonProperty("vehicle_diagram_allocation_id")
    private List<Long> vehicleDiagramAllocationId;

}
