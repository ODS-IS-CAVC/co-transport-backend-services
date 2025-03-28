package nlj.business.carrier.dto.vehicleDiagramItemTrailer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムトラクターレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTractorResponseDTO {

    @JsonProperty("departure_time")
    private Time departureTime;

    @JsonProperty("arrival_time")
    private Time arrivalTime;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    @JsonProperty("max_payload")
    private BigDecimal maxPayload;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("end_date")
    private Date endDate;

    @JsonProperty("registration_group_number")
    private String registrationGroupNumber;

    @JsonProperty("registration_character")
    private Character registrationCharacter;

    @JsonProperty("registration_number_1")
    private String registrationNumber1;

    @JsonProperty("registration_number_2")
    private String registrationNumber2;

    @JsonProperty("registration_area_code")
    private String registrationAreaCode;

    public static VehicleDiagramItemTractorResponseDTO fromResult(Object[] result) {
        VehicleDiagramItemTractorResponseDTO dto = new VehicleDiagramItemTractorResponseDTO();
        dto.setDepartureTime((Time) result[0]);
        dto.setArrivalTime((Time) result[1]);
        dto.setTripName((String) result[2]);
        dto.setTotalLength((BigDecimal) result[3]);
        dto.setTotalWidth((BigDecimal) result[4]);
        dto.setTotalHeight((BigDecimal) result[5]);
        dto.setMaxPayload((BigDecimal) result[6]);
        dto.setGiai((String) result[7]);
        dto.setStartDate((Date) result[8]);
        dto.setEndDate((Date) result[9]);
        dto.setRegistrationGroupNumber((String) result[10]);
        dto.setRegistrationCharacter((Character) result[11]);
        dto.setRegistrationNumber1((String) result[12]);
        dto.setRegistrationNumber2((String) result[13]);
        dto.setRegistrationAreaCode((String) result[14]);
        return dto;
    }
}
