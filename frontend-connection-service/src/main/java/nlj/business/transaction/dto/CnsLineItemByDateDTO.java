package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.CnsLineItemByDateSnapshotDeserializer;
import nlj.business.transaction.converter.MillisecondsToLocalDateDeserializer;
import nlj.business.transaction.converter.ProposeTrspAbilityDeserializer;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;
import nlj.business.transaction.converter.TimestampToLocalDateTimeDeserializer;
import nlj.business.transaction.converter.VehicleAvbResourceItemSnapshotDeserializer;

/**
 * <PRE>
 * 輸送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnsLineItemByDateDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("cns_line_item_id")
    private Long cnsLineItemId;

    @JsonProperty("trans_plan_id")
    private String transPlanId;

    @JsonProperty("collection_date")
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate collectionDate;

    @JsonProperty("transport_date")
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate transportDate;

    @JsonProperty("collection_time_from")
    private LocalTime collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private LocalTime collectionTimeTo;

    @JsonProperty("propose_depature_time")
    private LocalTime proposeDepartureTime;

    @JsonProperty("propose_arrival_time")
    private LocalTime proposeArrivalTime;

    @JsonProperty("propose_price")
    private BigDecimal proposePrice;

    @JsonProperty("outer_package_code")
    private Integer outerPackageCode;

    @JsonProperty("price_per_unit")
    private BigDecimal pricePerUnit;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("total_height")
    private BigDecimal totalHeight;

    @JsonProperty("total_length")
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    private BigDecimal totalWidth;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("trailer_number_rest")
    private Integer trailerNumberRest;

    @JsonProperty("weight")
    private BigDecimal weight;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("transport_code")
    private String transportCode;

    @JsonProperty("transport_name")
    private String transportName;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("vehicle_condition")
    private String vehicleCondition;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailer_license_plt_num_id;

    @JsonProperty("parent_order_created_date")
    @JsonDeserialize(using = TimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime orderCreatedDate;

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_SNAPSHOT)
    @JsonDeserialize(using = CnsLineItemByDateSnapshotDeserializer.class)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty("parent_order_propose_snapshot")
    @JsonDeserialize(using = VehicleAvbResourceItemSnapshotDeserializer.class)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty("propose_trsp_ability")
    @JsonDeserialize(using = ProposeTrspAbilityDeserializer.class)
    private List<ProposeTrspAbilityDTO> proposeTrspAbility;


}
