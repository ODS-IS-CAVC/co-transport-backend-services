package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者トランザクションSIPトラックID更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateSipTrackIdRequestDTO {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty("sip_track_id")
    private String sipTrackId;


}
