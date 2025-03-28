package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送注文検索リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransOrderSearchRequest {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private List<Long> vehicleDiagramItemTrailerId;
}
