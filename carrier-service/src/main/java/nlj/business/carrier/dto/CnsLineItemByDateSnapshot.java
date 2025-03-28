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
public class CnsLineItemByDateSnapshot {

    private Long id;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("req_cns_line_item_id")
    private Long reqCnsLineItemId;

    @JsonProperty("trans_plan_id")
    private String transPlanId;

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

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("trans_order_id")
    private Long transOrderId;

    @JsonProperty("cns_line_item_id")
    private Long cnsLineItemId;

    @JsonProperty("matching_id")
    private Long matchingId;

    @JsonProperty("shipper_operator_name")
    private String shipperOperatorName;

    @JsonProperty("temperature_range")
    private List<Integer> temperatureRange;
}
