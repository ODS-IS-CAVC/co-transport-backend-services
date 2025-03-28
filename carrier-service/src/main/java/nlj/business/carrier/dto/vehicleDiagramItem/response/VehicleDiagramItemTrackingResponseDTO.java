package nlj.business.carrier.dto.vehicleDiagramItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import nlj.business.carrier.dto.vehicleDiagramMobilityHub.response.VehicleDiagramMobilityHubResponseDTO;

/**
 * <PRE>
 * 車両図面アイテムトラッキングレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrackingResponseDTO {

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

    @JsonProperty("sip_track_id")
    private String sipTrackId;

    @JsonProperty("vehicle_diagram_item_tracking")
    private List<VehicleDiagramItemTrackingDetailsResponseDTO> itemTrackingDetails;
    @JsonProperty("vehicle_diagram_allocations")
    private List<VehicleDiagramAllocationResDTO> vehicleDiagramAllocations;

    @JsonProperty("vehicle_diagram_item_trailer")
    private List<VehicleDiagramItemTrailerDTO> vehicleDiagramItemTrailers;
    @JsonProperty("vehicle_diagram_mobility_hub")
    private List<VehicleDiagramMobilityHubResponseDTO> vehicleDiagramMobilityHub;
}
