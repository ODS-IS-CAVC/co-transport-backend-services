package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両可用性リソース品目DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAvbResourceItemDTO {

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

    private String day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time_min")
    private String departureTimeMin;

    @JsonProperty("departure_time_max")
    private String departureTimeMax;

    @JsonProperty("arrival_time")
    private String arrivalTime;

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
    private List<Integer> temperatureRange;

    private BigDecimal price;

    private Integer status;

    private String giai;
}
