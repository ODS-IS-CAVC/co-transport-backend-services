package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * 配送マッチングトレーラIDレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransMatchingTrailerIdResponse {

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_strt_date")
    private Date serviceStrtDate;

    @JsonProperty("service_strt_time")
    private Time serviceStrtTime;

    @JsonProperty("service_end_date")
    private Date serviceEndDate;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("departure_from")
    private String departureFrom;

    @JsonProperty("arrival_to")
    private String arrivalTo;

    @JsonProperty("carrier_operator_name")
    private String carrierOperatorName;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("temperature_range")
    private Integer[] temperatureRange;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;
}
