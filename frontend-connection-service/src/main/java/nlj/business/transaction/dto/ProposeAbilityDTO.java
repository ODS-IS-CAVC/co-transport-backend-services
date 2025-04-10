package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.converter.CarInfoDeserializer;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;
import nlj.business.transaction.converter.TimestampToLocalDateTimeDeserializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposeAbilityDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("day")
    private String day;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("assign_type")
    private Integer assignType;

    @JsonProperty("car_info_id")
    private Long carInfoId;

    @JsonProperty("max_payload")
    private BigDecimal maxPayload;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("vehicle_size")
    private Integer vehicleSize;

    @JsonProperty("vehicle_type")
    private Integer vehicleType;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("adjustment_price")
    private Integer adjustmentPrice;

    @JsonProperty("car_info")
    @JsonDeserialize(using = CarInfoDeserializer.class)
    private List<TransportPlanCarInfoDTO> carInfo;

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;

    @JsonProperty("departure_time_max")
    private String departureTimeMax;

    @JsonProperty("departure_time_min")
    private String departureTimeMin;

    @JsonProperty("vehicle_avb_resource_id")
    private Long vehicleAvbResourceId;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("created_date")
    @JsonDeserialize(using = TimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;
}
