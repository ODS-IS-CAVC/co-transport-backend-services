package nlj.business.carrier.dto.vehicleDiagramPrice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.dto.vehicleDiagramPrice.VehicleDiagramPriceDTO;

/**
 * <PRE>
 * 車両図面価格レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramPriceResponseDTO {

    @JsonProperty("vehicle_diagram_price")
    private List<VehicleDiagramPriceDTO> vehicleDiagramPrices;
}
