package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者取引応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailResponseDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_TYPE)
    private Integer transType;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_MATCHING_ID)
    private Long transMatchingId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_ID)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_CODE)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_CODE)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_CODE)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_BY_DATE_ID)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ITEM_ID)
    private Long vehicleAvbResourceItemId;

    @JsonProperty("vehicle_diagram_item_id")
    private Long vehicleDiagramItemId;

    @JsonProperty(DataBaseConstant.TransOrder.TRAILER_NUMBER)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransOrder.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransOrder.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransOrder.TRANSPORT_DATE)
    private String transportDate;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_PRICE)
    private BigDecimal requestPrice;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_FROM)
    private Time requestCollectionTimeFrom;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_TO)
    private Time requestCollectionTimeTo;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_PRICE)
    private BigDecimal proposePrice;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_DEPARTURE_TIME)
    private Time proposeDepartureTime;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_ARRIVAL_TIME)
    private Time proposeArrivalTime;

    @JsonProperty(DataBaseConstant.TransOrder.DEPARTURE_TIME)
    private Time departureTime;

    @JsonProperty(DataBaseConstant.TransOrder.ARRIVAL_TIME)
    private Time arrivalTime;

    @JsonProperty("road_carr_depa_sped_org_name_txt")
    private String roadCarrDepaSpedOrgNameTxt;

    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    private String trspCliTelCmmCmpNumTxt;

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

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_SNAPSHOT)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_SNAPSHOT)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.NEGOTIATION_DATA)
    private NegotiationData negotiationData;

    @JsonIgnore
    private String serviceNo;

    @JsonIgnore
    private String serviceStrtDate;

    @JsonIgnore
    private Long carInfoId;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("car_info")
    List<Map<String, Object>> carInfo;

    @JsonProperty("parent_order_id")
    private Long parentOrderId;

    @JsonProperty("parent_order")
    private TransactionCarrierDTO parentOrder;

    @JsonProperty("sibling_order_status")
    private Integer siblingOrderStatus;
}
