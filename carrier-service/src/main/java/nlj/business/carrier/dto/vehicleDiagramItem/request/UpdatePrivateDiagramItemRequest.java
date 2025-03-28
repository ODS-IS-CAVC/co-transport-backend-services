package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両図面アイテムプライベート更新リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdatePrivateDiagramItemRequest {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private List<Long> vehicleDiagramItemTrailerId;
    @JsonProperty("is_private")
    @ValidateField(inputIsArrOrBoolean = false, notNull = true)
    private Boolean isPrivate;
}
