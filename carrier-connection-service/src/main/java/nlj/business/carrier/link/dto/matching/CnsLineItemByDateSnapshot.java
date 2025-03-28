package nlj.business.carrier.link.dto.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Convert;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnsLineItemByDateSnapshot {

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
    @Convert(converter = IntegerArrayToStringConverter.class)
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
    private Integer transOrderId;
}
