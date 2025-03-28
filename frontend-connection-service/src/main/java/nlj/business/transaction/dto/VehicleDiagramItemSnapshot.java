package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * <PRE>
 * 車両図アイテムスナップショット。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemSnapshot {

    private Long id;

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("oprerator_id")
    private String operatorId;

    @JsonProperty("vehicle_diagram_item_id")
    private Long vehicleDiagramItemId;

    @JsonProperty("vehicle_diagram_allocation_id")
    private Long vehicleDiagramAllocationId;

    private LocalDate day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    @JsonProperty("adjustment_price")
    private BigDecimal adjustmentPrice;

    private Integer status;
}
