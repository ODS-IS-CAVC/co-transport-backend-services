package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransMatchingDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.TransMatching.TRANS_TYPE)
    private Integer transType;

    @JsonProperty(DataBaseConstant.TransMatching.BATCH_ID)
    private Long batchId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_ID)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_CODE)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_CODE)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @JsonProperty(DataBaseConstant.TransMatching.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(DataBaseConstant.TransMatching.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceId;

    @JsonProperty(DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ITEM_ID)
    private Long vehicleAvbResourceItemId;

    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransMatching.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransMatching.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransMatching.TRANSPORT_DATE)
    private LocalDate transportDate;

    private Integer status;

    @JsonProperty(DataBaseConstant.TransMatching.REQUEST_SNAPSHOT)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransMatching.PROPOSE_SNAPSHOT)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @Column(name = DataBaseConstant.TransMatching.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @Column(name = DataBaseConstant.TransMatching.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.TransMatching.IS_EMERGENCY)
    private Integer isEmergency;
}
