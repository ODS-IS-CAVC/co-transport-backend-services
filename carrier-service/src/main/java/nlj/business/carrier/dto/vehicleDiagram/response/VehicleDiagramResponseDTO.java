package nlj.business.carrier.dto.vehicleDiagram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.domain.opt.DayAdjustment;
import nlj.business.carrier.domain.opt.DayTime;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramITemTrailerResDTO;

/**
 * <PRE>
 * 輸送車両図形レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagramResponseDTO {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("diagram_head_id")
    private Long diagramHeadId;

    @JsonProperty("round_trip_type")
    private Integer roundTripType;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("day_week")
    private Map<String, DayTime> dayWeek;

    @JsonProperty("adjustment_price")
    private Map<String, DayAdjustment> adjustmentPrice;

    @JsonProperty("common_price")
    private BigDecimal commonPrice;

    @JsonProperty("cut_off_price")
    private Map<String, BigDecimal> cutOffPrice;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("vehicle_diagram_items")
    private List<VehicleDiagramItemResponseDTO> vehicleDiagramItems;

    @JsonProperty("vehicle_diagram_allocations")
    private List<VehicleDiagramAllocationResDTO> vehicleDiagramAllocations;

    @JsonProperty("vehicle_diagram_item_trailers")
    private List<VehicleDiagramITemTrailerResDTO> vehicleDiagramItemTrailerResDTOS;

    // Vehicle diagram head

    @JsonProperty("start_date")
    @ValidateField(dateCompareField = "startDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String startDate;

    @JsonProperty("end_date")
    @ValidateField(dateCompareField = "endDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String endDate;

    @JsonProperty("repeat_day")
    @ValidateField(textHalfWidth = true, textContainSpecialChar = true)
    private Integer repeatDay;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("is_round_trip")
    private Boolean isRoundTrip;

    @JsonProperty("origin_type")
    private Integer originType;

    @JsonProperty("import_id")
    private Long importId;

    @JsonProperty("freight_rate_type")
    private Integer freightRateType;

    @JsonProperty("one_way_time")
    private String oneWayTime;

    @JsonProperty("created_date")
    private String createdDate;
}
