package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送業者交渉DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierNegotiation {

    @JsonProperty("departure_date")
    private LocalDate departureDate;

    @JsonProperty("arrival_date")
    private LocalDate arrivalDate;

    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    private BigDecimal price;

    private String comment;
}
