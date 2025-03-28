package nlj.business.carrier.dto.vehicleDiagramPrice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Data;
import nlj.business.carrier.domain.opt.DayAdjustment;

/**
 * <PRE>
 * 車両図面価格DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramPriceDTO {

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("round_trip_type")
    private Integer roundTripType;

    @JsonProperty("adjustment_price")
    private Map<String, DayAdjustment> adjustmentPrice;

    @JsonProperty("common_price")
    private BigDecimal commonPrice;

    @JsonProperty("cut_off_price")
    private Map<String, BigDecimal> cutOffPrice;

    @JsonProperty("item_price")
    private List<VehicleDiagramItemPriceDTO> itemPrice;
}
