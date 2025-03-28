package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 運送業者車両図形取得リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierVehicleDiagramGetRequest {

    @JsonProperty("trailerIds")
    private List<Long> trailerIds;
}
