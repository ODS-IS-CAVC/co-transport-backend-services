package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 市場輸送計画品目 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class MarketTransportPlanItemDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.SHIPPER_CORPERATE_ID)
    private Long shipperCorperateId;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TRANSPORT_PLAN_ID)
    private Long transportPlanId;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TRANSPORT_CODE)
    private String transportCode;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TRANSPORT_NAME)
    private String transportName;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.DEPARTURE_FROM)
    private String departureFrom;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.ARRIVAL_TO)
    private String arrivalTo;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.COLLECTION_DATE)
    private LocalDate collectionDate;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.PRICE_PER_UNIT)
    private Double pricePerUnit;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.COLLECTION_TIME_FROM)
    private String collectionTimeFrom;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.COLLECTION_TIME_TO)
    private String collectionTimeTo;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.VEHICLE_CONDITION)
    private List<Integer> vehicleCondition;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.OUTER_PACKAGE_CODE)
    private String outerPackageCode;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TOTAL_LENGTH)
    private Double totalLength;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TOTAL_WIDTH)
    private Double totalWidth;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.TOTAL_HEIGHT)
    private Double totalHeight;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.WEIGHT)
    private Double weight;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.SPECIAL_INSTRUCTIONS)
    private String specialInstructions;

    @JsonProperty(DataBaseConstant.MarketTransportPlanItem.STATUS)
    private Integer status;
}
