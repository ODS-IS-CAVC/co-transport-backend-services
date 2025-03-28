package nlj.business.transaction.dto.trip.vehicleDiagramItemTrailer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * <PRE>
 * 車両図形項目トレーラーレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrailerResponseDTO {

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("vehicle_diagram_item_id")
    private Long vehicleDiagramItemId;

    @JsonProperty("day")
    private LocalDate day;

    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    @JsonProperty("trip_name")
    private String tripName;


    public static VehicleDiagramItemTrailerResponseDTO fromResult(Object[] result) {
        VehicleDiagramItemTrailerResponseDTO dto = new VehicleDiagramItemTrailerResponseDTO();
        dto.setVehicleDiagramItemId((Long) result[0]);
        dto.setVehicleDiagramId((Long) result[1]);
        if (result[2] instanceof Time) {
            dto.setDepartureTime(((Time) result[2]).toLocalTime());
        }
        if (result[3] instanceof Time) {
            dto.setArrivalTime(((Time) result[3]).toLocalTime());
        }
        if (result[4] instanceof Date) {
            dto.setDay(((Date) result[4]).toLocalDate());
        }
        dto.setTripName((String) result[5]);
        return dto;
    }
}
