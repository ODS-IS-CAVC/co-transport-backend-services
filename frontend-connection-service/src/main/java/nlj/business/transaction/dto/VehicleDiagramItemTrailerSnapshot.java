package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 車両図アイテムスナップショット。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrailerSnapshot {

    private Long id;

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("vehicle_diagram_item_id")
    private Long vehicleDiagramItemId;

    @JsonProperty("vehicle_diagram_allocation_id")
    private Long vehicleDiagramAllocationId;

    @JsonProperty("vehicle_info_id")
    private Long vehicleInfoId;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    private LocalDate day;

    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    private Integer price;

    @JsonProperty("vehicle_code")
    private String vehicleCode;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("vehicle_type")
    private Integer vehicleType;

    @JsonProperty("vehicle_size")
    private Integer vehicleSize;

    @JsonProperty("temperature_range")
    private List<Integer> temperatureRange;

    @JsonProperty("max_payload")
    private BigDecimal maxPayload;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    @JsonProperty("ground_clearance")
    private BigDecimal groundClearance;

    @JsonProperty("door_height")
    private BigDecimal doorHeight;

    @JsonProperty("body_specification")
    private String bodySpecification;

    @JsonProperty("body_shape")
    private String bodyShape;

    @JsonProperty("body_construction")
    private String bodyConstruction;

    private String[] images;

    private Integer status;
}
