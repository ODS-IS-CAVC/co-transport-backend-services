package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

/**
 * <PRE>
 * 運送業者車両図表取得レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierVehicleDiagramGetResponse {

    @JsonProperty("diagramItemTrailerMatching")
    private Map<Long, Long> diagramItemTrailerMatching = null;
}
