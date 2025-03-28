package nlj.business.carrier.dto.vehicleDiagram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.domain.opt.DayAdjustment;
import nlj.business.carrier.domain.opt.DayTime;

/**
 * <PRE>
 * Vehicle Diagram DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramDTO {

    @JsonProperty("diagram_head_id")
//  @ValidateField(notNull = true)
    private Long diagramHeadId;

    @JsonProperty("round_trip_type")
    @ValidateField(roundTripDirection = true)
    private Integer roundTripType;

    @JsonProperty("trip_name")
    @ValidateField(maxLength = 255)
    private String tripName;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;

    @JsonProperty("day_week")
    private Map<String, DayTime> dayWeek;

    @JsonProperty("adjustment_price")
    private Map<String, DayAdjustment> adjustmentPrice;

    @JsonProperty("common_price")
    @ValidateField(precision = 9, scale = 0)
    private BigDecimal commonPrice;

    @JsonProperty("cut_off_price")
    private Map<String, BigDecimal> cutOffPrice;

    @JsonProperty("status")
    @ValidateField(statusVehicleDiagram = true, positiveText = true)
    private Integer status;

}