package nlj.business.carrier.dto.vehicleDiagramItemTrailer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両図面アイテムトレーラー日付DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DayDTO {

    @JsonProperty("vehicle_diagram_item_trailer_id")
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty("vehicle_diagram_item_id")
    @ValidateField(notNull = true)
    private Long vehicleDiagramItemId;

    @JsonProperty("day")
    @ValidateField(dateCompareField = "day", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String day;

    @JsonProperty("price")
    @ValidateField(precision = 9, scale = 0)
    private BigDecimal price;

    @JsonProperty("cut_off_price")
    private Map<String, BigDecimal> cutOffPrice;

    @JsonProperty("status")
    private int status;
}
