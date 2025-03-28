package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画明細売上DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnsLineItemByDateSaleDTO {

    private Long id;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.OPERATOR_ID)
    private String operatorId;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.OPERATOR_CODE)
    private String operatorCode;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANS_PLAN_ID)
    private String transPlanId;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANSPORT_CODE)
    private String transportCode;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANSPORT_NAME)
    private String transportName;

    @JsonProperty("collection_date")
    private String collectionDate;

    @JsonProperty("collection_time_from")
    private Time collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private Time collectionTimeTo;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRAILER_NUMBER)
    private Integer trailerNumber;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRAILER_NUMBER_REST)
    private Integer trailerNumberRest;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.PRICE_PER_UNIT)
    private BigDecimal pricePerUnit;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.VEHICLE_CONDITION)
    private String vehicleCondition;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.OUTER_PACKAGE_CODE)
    private Integer outerPackageCode;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.WEIGHT)
    private BigDecimal weight;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.SPECIAL_INSTRUCTIONS)
    private String specialInstructions;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.STATUS)
    private Integer status;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANS_TYPE)
    private Integer transType;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANSPORT_DATE)
    private LocalDate transportDate;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.PROPOSE_PRICE)
    private BigDecimal proposePrice;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.PROPOSE_DEPARTURE_TIME)
    private LocalTime proposeDepartureTime;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.PROPOSE_ARRIVAL_TIME)
    private LocalTime proposeArrivalTime;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.ISTD_TOLL_VOL_MEAS)
    private BigDecimal istdTollVolMeas;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.VOL_UNT_CD)
    private String volUntCd;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.NUM_UNT_CD)
    private String numUntCd;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.WEIG_UNT_CD)
    private String weigUntCd;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.TRANS_ORDER_ID)
    private Long transOrderId;

    @JsonProperty(value = DataBaseConstant.CnsLineItemByDate.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(value = "item_name_txt")
    private String itemNameTxt;

    @JsonProperty(value = "cnsg_prty_name_txt")
    private String csngPrtyNameTxt;

}
