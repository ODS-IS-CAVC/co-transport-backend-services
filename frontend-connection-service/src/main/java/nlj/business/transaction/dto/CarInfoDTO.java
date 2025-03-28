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
 * 車両情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarInfoDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("service_strt_date")
    private LocalDate serviceStrtDate;

    @JsonProperty("service_strt_time")
    private LocalTime serviceStrtTime;

    @JsonProperty("service_end_date")
    private LocalDate serviceEndDate;

    @JsonProperty("service_end_time")
    private LocalTime serviceEndTime;

    @JsonProperty("freight_rate")
    private BigDecimal freightRate;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("tractor_idcr")
    private String tractorIdcr;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("order_status")
    private Integer orderStatus;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;
}
