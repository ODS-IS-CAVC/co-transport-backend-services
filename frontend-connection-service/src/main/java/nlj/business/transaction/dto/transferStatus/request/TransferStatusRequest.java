package nlj.business.transaction.dto.transferStatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 転送ステータスリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferStatusRequest {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty("vehicle_diagram_item_trailer_status")
    private Integer vehicleDiagramItemTrailerStatus;
    @JsonProperty("transport_plan_item_id")
    private Long transportPlanItemId;

    @JsonProperty("transport_plan_item_status")
    private Integer transportPlanItemStatus;
}
