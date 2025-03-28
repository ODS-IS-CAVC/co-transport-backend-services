package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.converter.MillisecondsToLocalDateDeserializer;
import nlj.business.transaction.converter.ProposeTrspAbilityDeserializer;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;

/**
 * <PRE>
 * 輸送計画明細応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnsLineItemByDateResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("cns_line_item_id")
    private Long cnsLineItemId;

    @JsonProperty("req_cns_line_item_id")
    private Long reqCnsLineItemId;

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

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;

    @JsonProperty("transport_code")
    private String transportCode;

    @JsonProperty("transport_name")
    private String transportName;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("vehicle_condition")
    private String vehicleCondition;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("cnsg_prty_name_txt")
    private String CnsgPrtyNameTxt;

    @JsonProperty("carrier_price")
    private BigDecimal carrierPrice;

    @JsonProperty("propose_trsp_ability")
    @JsonDeserialize(using = ProposeTrspAbilityDeserializer.class)
    private List<ProposeTrspAbilityDTO> proposeTrspAbility;


}
