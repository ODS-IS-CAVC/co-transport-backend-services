package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 運送業者トランザクション注文検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportOrderSearchRequest {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private List<Long> vehicleDiagramItemTrailerId;
}
