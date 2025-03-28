package nlj.business.carrier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送会社交渉DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAvbResourceItemSnapshot {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("vehicle_avb_resource_id")
    private Long vehicleAvbResourceId;

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    private LocalDate day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time_min")
    private LocalTime departureTimeMin;

    @JsonProperty("departure_time_max")
    private LocalTime departureTimeMax;

    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;

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

    @JsonProperty("car_max_load_capacity1_meas")
    private Integer carMaxLoadCapacity1Meas;

    @JsonProperty("vehicle_size")
    private Integer vehicleSize;

    @JsonProperty("temperature_range")
    private List<Integer> temperatureRange;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    private BigDecimal price;

    private Integer status;

    private String giai;

    @JsonProperty("cut_off_info_id")
    private Long cutOffInfoId;

    @JsonProperty("free_time_info_id")
    private Long freeTimeInfoId;

    @JsonProperty("free_time_time")
    private BigDecimal freeTimeTime;

    @JsonProperty("free_time_free")
    private BigDecimal freeTimeFee;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("trans_order_id")
    private Long transOrderId;

    @JsonProperty("vehicle_name")
    private String vehicleName;
}
