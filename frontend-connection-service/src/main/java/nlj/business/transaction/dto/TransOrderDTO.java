package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.CnsLineItemByDateSnapshotDeserializer;
import nlj.business.transaction.converter.MillisecondsToLocalDateDeserializer;
import nlj.business.transaction.converter.NegotiationDataDeserializer;
import nlj.business.transaction.converter.VehicleAvbResourceItemSnapshotDeserializer;

/**
 * <PRE>
 * 運送業者取引DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransOrderDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_TYPE)
    private Integer transType;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_ID)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_CODE)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_CODE)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_CODE)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_BY_DATE_ID)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ITEM_ID)
    private Long vehicleAvbResourceItemId;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_SNAPSHOT)
    @JsonDeserialize(using = CnsLineItemByDateSnapshotDeserializer.class)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_SNAPSHOT)
    @JsonDeserialize(using = VehicleAvbResourceItemSnapshotDeserializer.class)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.TRAILER_NUMBER)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransOrder.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransOrder.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransOrder.TRANSPORT_DATE)
    @JsonDeserialize(using = MillisecondsToLocalDateDeserializer.class)
    private LocalDate transportDate;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_PRICE)
    private BigDecimal requestPrice;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_FROM)
    private LocalTime requestCollectionTimeFrom;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_TO)
    private LocalTime requestCollectionTimeTo;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_PRICE)
    private BigDecimal proposePrice;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_DEPARTURE_TIME)
    private LocalTime proposeDepartureTime;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_ARRIVAL_TIME)
    private LocalTime proposeArrivalTime;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_CONTRACT_FILE)
    private String shipperContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_CONTRACT_FILE)
    private String carrierContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_CONTRACT_FILE)
    private String carrier2ContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CONTRACT_FILE)
    private String contractFile;

    @JsonProperty(DataBaseConstant.TransOrder.STATUS)
    private Integer status;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_MATCHING_ID)
    private Long transMatchingId;

    @JsonProperty(DataBaseConstant.TransOrder.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceId;

    @JsonProperty(DataBaseConstant.TransOrder.PARENT_ORDER_ID)
    private Long parentOrderId;

    @JsonProperty(DataBaseConstant.TransOrder.TRSP_INSTRUCTION_ID)
    private String trspInstructionId;

    @JsonProperty(DataBaseConstant.TransOrder.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.NEGOTIATION_DATA)
    @JsonDeserialize(using = NegotiationDataDeserializer.class)
    private NegotiationData negotiationData;

    @JsonProperty(DataBaseConstant.TransOrder.SERVICE_NAME)
    private String serviceName;

    @JsonProperty(DataBaseConstant.TransOrder.ITEM_NAME_TXT)
    private String itemNameTxt;
}
