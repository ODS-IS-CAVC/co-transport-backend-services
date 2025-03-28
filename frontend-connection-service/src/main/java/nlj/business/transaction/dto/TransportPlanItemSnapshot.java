package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画項目スナップショット。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanItemSnapshot {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("transport_plan_id")
    private Long transportPlanId;

    @JsonProperty("transport_code")
    private String transportCode;

    @JsonProperty("transport_name")
    private String transportName;

    @JsonProperty("collection_date")
    private LocalDate collectionDate;

    @JsonProperty("collection_time_from")
    private LocalTime collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private LocalTime collectionTimeTo;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("trailer_number_rest")
    private Integer trailerNumberRest;

    @JsonProperty("price_per_unit")
    private BigDecimal pricePerUnit;

    @JsonProperty("vehicle_condition")
    private List<Integer> vehicleCondition;

    @JsonProperty("outer_package_code")
    private Integer outerPackageCode;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    private BigDecimal weight;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    private Integer status;
}
