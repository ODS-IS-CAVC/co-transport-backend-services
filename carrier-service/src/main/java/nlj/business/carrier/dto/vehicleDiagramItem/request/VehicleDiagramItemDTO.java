package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * Vehicle Diagram Item DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemDTO {

    @JsonProperty("operator_id")
    @ValidateField(notNull = true)
    private String operatorId;

    @JsonProperty("vehicle_diagram_id")
    @ValidateField(notNull = true)
    private Long vehicleDiagramId;

    @JsonProperty("day")
    @ValidateField(dateCompareField = "day", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String day;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("trip_name")
    @ValidateField(maxLength = 255)
    private String tripName;

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;

    @JsonProperty("price")
    @ValidateField(precision = 9, scale = 0)
    private BigDecimal price;

    @JsonProperty("is_emergency")
    private Integer isEmergency = 0;
}
