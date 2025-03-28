package nlj.business.carrier.dto.vehicleDiagram.request;

import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送車両図形取得リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierVehicleDiagramGetRequest {

    private List<Long> trailerIds;
}
