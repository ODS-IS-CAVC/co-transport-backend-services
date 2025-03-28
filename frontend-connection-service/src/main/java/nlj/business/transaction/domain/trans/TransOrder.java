package nlj.business.transaction.domain.trans;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.CnsLineItemByDateSnapshotConverter;
import nlj.business.transaction.converter.NegotiationDataConverter;
import nlj.business.transaction.converter.VehicleAvbResourceItemSnapshotConverter;
import nlj.business.transaction.domain.AbstractAuditingEntity;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;
import nlj.business.transaction.dto.NegotiationData;
import nlj.business.transaction.dto.VehicleAvbResourceItemSnapshot;
import org.hibernate.annotations.ColumnTransformer;

/**
 * <PRE>
 * 取引注文エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransOrder.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransOrder extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_TYPE)
    @Column(name = DataBaseConstant.TransOrder.TRANS_TYPE, nullable = false)
    private Integer transType;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransOrder.CARRIER_OPERATOR_ID, nullable = false)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransOrder.CARRIER_OPERATOR_CODE, nullable = false, length = 13)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransOrder.SHIPPER_OPERATOR_ID, nullable = false)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransOrder.SHIPPER_OPERATOR_CODE, length = 13)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransOrder.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransOrder.CARRIER2_OPERATOR_CODE, length = 13)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransOrder.REQ_CNS_LINE_ITEM_ID)
    @Column(name = DataBaseConstant.TransMatching.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_ID)
    @Column(name = DataBaseConstant.TransMatching.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(DataBaseConstant.TransOrder.CNS_LINE_ITEM_BY_DATE_ID)
    @Column(name = DataBaseConstant.TransOrder.CNS_LINE_ITEM_BY_DATE_ID, nullable = false)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ID)
    @Column(name = DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceId;

    @JsonProperty(DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ITEM_ID)
    @Column(name = DataBaseConstant.TransOrder.VEHICLE_AVB_RESOURCE_ITEM_ID, nullable = false)
    private Long vehicleAvbResourceItemId;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_SNAPSHOT)
    @Column(name = DataBaseConstant.TransOrder.REQUEST_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = CnsLineItemByDateSnapshotConverter.class)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_SNAPSHOT)
    @Column(name = DataBaseConstant.TransOrder.PROPOSE_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = VehicleAvbResourceItemSnapshotConverter.class)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty(DataBaseConstant.TransOrder.TRAILER_NUMBER)
    @Column(name = DataBaseConstant.TransOrder.TRAILER_NUMBER, precision = 9, scale = 0, nullable = false)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransOrder.DEPARTURE_FROM)
    @Column(name = DataBaseConstant.TransOrder.DEPARTURE_FROM, nullable = false)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransOrder.ARRIVAL_TO)
    @Column(name = DataBaseConstant.TransOrder.ARRIVAL_TO, nullable = false)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransOrder.TRANSPORT_DATE)
    @Column(name = DataBaseConstant.TransOrder.TRANSPORT_DATE, nullable = false)
    private LocalDate transportDate;

    @JsonProperty(DataBaseConstant.TransOrder.PRICE)
    @Column(name = DataBaseConstant.TransOrder.PRICE, precision = 10, scale = 0)
    private BigDecimal price;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_PRICE)
    @Column(name = DataBaseConstant.TransOrder.REQUEST_PRICE, precision = 10, scale = 0)
    private BigDecimal requestPrice;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_FROM)
    @Column(name = DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_FROM)
    private LocalTime requestCollectionTimeFrom;

    @JsonProperty(DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_TO)
    @Column(name = DataBaseConstant.TransOrder.REQUEST_COLLECTION_TIME_TO)
    private LocalTime requestCollectionTimeTo;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_PRICE)
    @Column(name = DataBaseConstant.TransOrder.PROPOSE_PRICE)
    private BigDecimal proposePrice;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_DEPARTURE_TIME)
    @Column(name = DataBaseConstant.TransOrder.PROPOSE_DEPARTURE_TIME)
    private LocalTime proposeDepartureTime;

    @JsonProperty(DataBaseConstant.TransOrder.PROPOSE_ARRIVAL_TIME)
    @Column(name = DataBaseConstant.TransOrder.PROPOSE_ARRIVAL_TIME)
    private LocalTime proposeArrivalTime;

    @JsonProperty(DataBaseConstant.TransOrder.SHIPPER_CONTRACT_FILE)
    @Column(name = DataBaseConstant.TransOrder.SHIPPER_CONTRACT_FILE, length = 500)
    private String shipperContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER_CONTRACT_FILE)
    @Column(name = DataBaseConstant.TransOrder.CARRIER_CONTRACT_FILE, length = 500)
    private String carrierContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CARRIER2_CONTRACT_FILE)
    @Column(name = DataBaseConstant.TransOrder.CARRIER2_CONTRACT_FILE, length = 500)
    private String carrier2ContractFile;

    @JsonProperty(DataBaseConstant.TransOrder.CONTRACT_FILE)
    @Column(name = DataBaseConstant.TransOrder.CONTRACT_FILE, length = 500)
    private String contractFile;

    @JsonProperty(DataBaseConstant.TransOrder.STATUS)
    @Column(name = DataBaseConstant.TransOrder.STATUS)
    private Integer status;

    @Column(columnDefinition = DataBaseConstant.COL_TYPE_TEXT)
    private String freeWord;

    @JsonProperty(DataBaseConstant.TransOrder.PARENT_ORDER_ID)
    @Column(name = DataBaseConstant.TransOrder.PARENT_ORDER_ID)
    private Long parentOrderId;

    @JsonProperty(DataBaseConstant.TransOrder.TRANS_MATCHING_ID)
    @Column(name = DataBaseConstant.TransOrder.TRANS_MATCHING_ID)
    private Long transMatchingId;

    @JsonProperty(DataBaseConstant.TransOrder.TRSP_INSTRUCTION_ID)
    @Column(name = DataBaseConstant.TransOrder.TRSP_INSTRUCTION_ID)
    private String trspInstructionId;

    @Column(name = DataBaseConstant.TransOrder.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @Column(name = DataBaseConstant.TransOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.TransOrder.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @Column(name = DataBaseConstant.TransOrder.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @Column(name = DataBaseConstant.TransOrder.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @JsonProperty(DataBaseConstant.TransOrder.NEGOTIATION_DATA)
    @Column(name = DataBaseConstant.TransOrder.NEGOTIATION_DATA, columnDefinition = DataBaseConstant.COl_TYPE_JSONB)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = NegotiationDataConverter.class)
    private NegotiationData negotiationData;

    @Column(name = DataBaseConstant.TransOrder.SERVICE_NAME, length = 255)
    @JsonProperty(DataBaseConstant.TransOrder.SERVICE_NAME)
    private String serviceName;

    @Column(name = DataBaseConstant.TransOrder.ITEM_NAME_TXT, length = 255)
    @JsonProperty(DataBaseConstant.TransOrder.ITEM_NAME_TXT)
    private String itemNameTxt;

    @Column(name = DataBaseConstant.TransOrder.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.TransOrder.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.TransOrder.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @Column(name = DataBaseConstant.TransOrder.IS_EMERGENCY)
    private Integer isEmergency;

    @Override
    public Long getId() {
        return this.id;
    }
}