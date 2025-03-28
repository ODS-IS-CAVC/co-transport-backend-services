package nlj.business.carrier.dto.vehicleDiagramHead.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.dto.vehicleDiagram.request.VehicleDiagramDTO;

/**
 * <PRE>
 * Vehicle Diagram Head DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramHeadDTO {

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("one_way_time")
    private String oneWayTime;

    @JsonProperty("start_date")
    @ValidateField(dateCompareField = "startDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String startDate;

    @JsonProperty("end_date")
    @ValidateField(dateCompareField = "endDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String endDate;

    @JsonProperty("repeat_day")
    @ValidateField(repeatDay = true)
    private Integer repeatDay;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("is_round_trip")
    @ValidateField(roundTrip = true)
    private Boolean isRoundTrip;

    @JsonProperty("origin_type")
    @ValidateField(originType = true)
    private Integer originType;

    @JsonProperty("import_id")
    private Long importId;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("is_validate")
    private Boolean isValidate;

    @JsonProperty("vehicle_diagram")
    private VehicleDiagramDTO vehicleDiagram;
} 