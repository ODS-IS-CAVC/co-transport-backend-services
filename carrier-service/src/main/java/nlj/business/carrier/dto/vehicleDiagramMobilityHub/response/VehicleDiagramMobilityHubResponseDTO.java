package nlj.business.carrier.dto.vehicleDiagramMobilityHub.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両図面モビリティハブレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramMobilityHubResponseDTO {

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.ID)
    private Long id;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ALLOCATION_ID)
    private Long vehicleDiagramAllocationId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.TYPE)
    private Integer type;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_TYPE)
    private Integer vehicleType;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.FREIGHT_ID)
    private String freightId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.MOBILITY_HUB_ID)
    private String mobilityHubId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.RESERVATION_STATUS)
    private Integer reservationStatus;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.TRUCK_ID)
    private String truckId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.SIZE_CLASS)
    private String sizeClass;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VALID_FROM)
    private String validFrom;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.VALID_UNTIL)
    private String validUntil;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.SLOT_ID)
    private String slotId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.MH_RESERVATION_ID)
    private String mhReservationId;

    @JsonProperty(DataBaseConstant.VehicleDiagramMobilityHub.STATUS)
    private Integer status;
}
