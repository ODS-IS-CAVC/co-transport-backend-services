package nlj.business.carrier.dto.vehicleDiagramItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.DataBaseConstant.DATE_TIME_FORMAT;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;

/**
 * <PRE>
 * 車両図面アイテム検索レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemByAbilityDetailsDTO {

    @JsonProperty("id")
    @ValidateField
    private Long id;

    @JsonProperty("day")
    @ValidateField
    private String day;

    @JsonProperty("trip_name")
    @ValidateField
    private String tripName;

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;

    @JsonProperty("status")
    @ValidateField
    private Integer status;

    @JsonProperty("start_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String startDate;

    @JsonProperty("end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
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

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("cutoff_time")
    private String cutoffTime;
    @JsonProperty("one_way_time")
    private String oneWayTime;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("is_private")
    private Boolean isPrivate;

    @JsonProperty("sip_track_id")
    private String sipTrackId;

    @JsonProperty("vehicle_diagram_allocations")
    private List<VehicleDiagramAllocationResDTO> vehicleDiagramAllocations;

    @JsonProperty("vehicle_diagram_item_trailer")
    private List<VehicleDiagramItemTrailerDTO> vehicleDiagramItemTrailers;
}
