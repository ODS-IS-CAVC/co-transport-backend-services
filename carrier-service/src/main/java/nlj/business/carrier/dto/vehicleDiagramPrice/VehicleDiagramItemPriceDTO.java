package nlj.business.carrier.dto.vehicleDiagramPrice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテム価格DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemPriceDTO {

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("price")
    private BigDecimal price;
}
