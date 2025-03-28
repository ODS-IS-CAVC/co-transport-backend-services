package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.MillisecondsToLocalDateDeserializer;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;

/**
 * <PRE>
 * 運送業者トランザクション公開レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransportAbilityPublicResponseDTO {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("car_info_id")
    private Long carInfoId;

    @JsonProperty("vehicle_availability_resource_id")
    private Long vehicleAvailabilityResourceId;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    private Date day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time_min")
    private Time departureTimeMin;

    @JsonProperty("departure_time_max")
    private Time departureTimeMax;

    @JsonProperty("arrival_time")
    private Time arrivalTime;

    @JsonProperty("adjustment_price")
    private Integer adjustmentPrice;

    @JsonProperty("vehicle_type")
    private Integer vehicleType;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("assign_type")
    private Integer assignType;

    @JsonProperty("max_payload")
    private BigDecimal maxPayload;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    @JsonProperty("vehicle_size")
    private Integer vehicleSize;

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;

    private BigDecimal price;

    private Integer status;

    private String giai;

    @JsonProperty("cut_off_info_id")
    private Long cutOffInfoId;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("free_time_info_id")
    private Long freeTimeInfoId;

    @JsonProperty("free_time_time")
    private BigDecimal freeTimeTime;

    @JsonProperty("free_time_fee")
    private BigDecimal freeTimeFee;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("tractor_idcr")
    private Character tractorIdcr;

    @JsonProperty("car_license_plt_num_id")
    private String car_license_plt_num_id;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailer_license_plt_num_id;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_strt_date")
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate serviceStrtDate;

    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataBaseConstant.DATE_TIME_FORMAT.YYYYMMDDHHMMSS)
    private Timestamp createdDate;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("road_carr_depa_sped_org_name_txt")
    private String roadCarrDepaSpedOrgNameTxt;

    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    private String trspCliTelCmmCmpNumTxt;

    @JsonProperty("service_strt_time")
    private Time serviceStrtTime;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;
}
