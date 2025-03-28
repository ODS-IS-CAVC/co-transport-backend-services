package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両ダイアグラム品目トレイラーDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramItemTrailerDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.OPERATOR_ID)
    private String operatorId;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ALLOCATION_ID)
    private Long vehicleDiagramAllocationId;

    private LocalDate day;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.TRIP_NAME)
    private String tripName;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.DEPARTURE_TIME)
    private LocalTime departureTime;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @JsonProperty(DataBaseConstant.VehicleDiagramItemTrailer.ADJUSTMENT_PRICE)
    private Integer adjustmentPrice;

    private Integer status;
}
