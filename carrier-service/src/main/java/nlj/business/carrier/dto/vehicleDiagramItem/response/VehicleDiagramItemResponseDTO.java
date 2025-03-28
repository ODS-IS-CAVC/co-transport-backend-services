package nlj.business.carrier.dto.vehicleDiagramItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("day")
    private String day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;
} 