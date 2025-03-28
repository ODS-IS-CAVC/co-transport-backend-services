package nlj.business.carrier.dto.vehicleDiagramItemTrailer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.TrailerDTO;

/**
 * <PRE>
 * Vehicle Diagram Item Trailer DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrailerRequestDTO {

    @JsonProperty("trip_name")
    @ValidateField(maxLength = 255)
    private String tripName;

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;

    @JsonProperty("cut_off_price")
    private Map<String, BigDecimal> cutOffPrice;

    @JsonProperty("common_price")
    @ValidateField(precision = 10, scale = 0)
    private BigDecimal commonPrice;

    private List<TrailerDTO> trailers;
}
