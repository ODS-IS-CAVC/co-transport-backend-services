package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanDTO {

    private Long id;

    @JsonProperty("transport_plan_item")
    @JsonManagedReference
    private List<TransportPlanItemDTO> transportPlanItemDTOS;

    @JsonProperty("operator_id")
    private String corporateId;

    @JsonProperty("transport_code")
    private String transportCode;

    @JsonProperty("transport_name")
    private String transportName;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("collection_date")
    private LocalDate collectionDate;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("repeat_day")
    private Integer repeatDay;

    @JsonProperty("day_week")
    private List<Integer> dayWeek;

    @JsonProperty("collection_time_from")
    private LocalTime collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private LocalTime collectionTimeTo;

    @JsonProperty("price_per_unit")
    private BigDecimal pricePerUnit;

    @JsonProperty("vehicle_condition")
    private List<Integer> vehicleCondition;

    @JsonProperty("outer_package_code")
    private Long outerPackageCode;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    private BigDecimal weight;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("origin_type")
    private Integer originType;

    @JsonProperty("import_id")
    private Long importId;

    private Integer status;
}
