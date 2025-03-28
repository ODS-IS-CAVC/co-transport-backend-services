package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.MillisecondsToLocalDateDeserializer;

/**
 * <PRE>
 * 提案運送業者プラン行項目DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposeTrspPlanLineItemDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_strt_date")
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate serviceStrtDate;

    @JsonProperty("service_strt_time")
    private LocalTime serviceStrtTime;

    @JsonProperty("service_end_date")
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate serviceEndDate;

    @JsonProperty("service_end_time")
    private LocalTime serviceEndTime;

    @JsonProperty("freight_rate")
    private BigDecimal freightRate;

    @JsonProperty("is_matched")
    private Boolean isMatched;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataBaseConstant.DATE_TIME_FORMAT.YYYYMMDDHHMMSS)
    private Timestamp createdDate;

    @JsonProperty("car_info")
    private Object carInfo;
}
