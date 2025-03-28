package nlj.business.carrier.dto.vehicleDiagramImport;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 車両図面インポートリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramImportRequest {

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("one_way_time")
    private String oneWayTime;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("repeat_day")
    private Integer repeatDay;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("is_round_trip")
    private Boolean isRoundTrip;

    @JsonProperty("origin_type")
    private Integer originType;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("round_trip_type")
    private Integer roundTripType;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("diagram_departure_from")
    private Long diagramDepartureFrom;

    @JsonProperty("diagram_arrival_to")
    private Long diagramArrivalTo;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("day_week")
    private String dayWeek;

    @JsonProperty("adjustment_price")
    private String adjustmentPrice;

    @JsonProperty("common_price")
    private BigDecimal commonPrice;

    @JsonProperty("cut_off_price")
    private String cutOffPrice;

    @JsonProperty("diagram_status")
    private Integer diagramStatus;
}
