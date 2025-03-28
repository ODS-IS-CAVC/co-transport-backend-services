package nlj.business.carrier.dto.vehicleDiagramHead.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;

/**
 * <PRE>
 * Vehicle Diagram Head Response DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramHeadResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("corperate_id")
    private String operatorId;

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

    @JsonProperty("status")
    private Integer status;

    private List<VehicleDiagramResponseDTO> vehicleDiagrams;
} 