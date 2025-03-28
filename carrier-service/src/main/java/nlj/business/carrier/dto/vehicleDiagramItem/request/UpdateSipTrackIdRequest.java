package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムSIPトラックID更新リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateSipTrackIdRequest {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty("sip_track_id")
    private String sipTrackId;


}
