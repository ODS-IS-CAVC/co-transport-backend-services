package nlj.business.carrier.dto.vehicleDiagram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 輸送サービスマッチングレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierServiceTransMatchingResponse {

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("matching_status")
    private Integer matchingStatus;

    @JsonProperty("order_status")
    private Integer orderStatus;

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private Long vehicleDiagramItemTrailerId;
}
