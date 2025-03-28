package nlj.business.carrier.dto.vehicleDiagram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

/**
 * <PRE>
 * 輸送車両図形取得レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierVehicleDiagramGetResponse {

    @JsonProperty("diagramItemTrailerMatching")
    private Map<Long, Long> diagramItemTrailerMatching = null;
}