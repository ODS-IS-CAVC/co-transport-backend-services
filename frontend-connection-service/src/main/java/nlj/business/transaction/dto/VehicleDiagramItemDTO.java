package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * <PRE>
 * 車両ダイアグラム品目DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemDTO {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("day")
    private LocalDate day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    @JsonProperty("adjustment_price")
    private Integer adjustmentPrice;

}
