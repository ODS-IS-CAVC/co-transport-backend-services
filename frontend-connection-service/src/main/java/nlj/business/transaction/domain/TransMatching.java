package nlj.business.transaction.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.converter.CnsLineItemByDateSnapshotConverter;
import nlj.business.transaction.converter.VehicleAvbResourceItemSnapshotConverter;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;
import nlj.business.transaction.dto.VehicleAvbResourceItemSnapshot;
import org.hibernate.annotations.ColumnTransformer;

/**
 * <PRE>
 * 配送マッチングエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransMatching.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransMatching extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(DataBaseConstant.TransMatching.TRANS_TYPE)
    @Column(name = DataBaseConstant.TransMatching.TRANS_TYPE, nullable = false)
    private Integer transType;

    @JsonProperty(DataBaseConstant.TransMatching.BATCH_ID)
    @Column(name = DataBaseConstant.TransMatching.BATCH_ID, nullable = false, unique = true)
    private Long batchId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransMatching.CARRIER_OPERATOR_ID, nullable = false)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE, nullable = false, length = 13)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransMatching.SHIPPER_OPERATOR_CODE, length = 13)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransMatching.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_CODE)
    @Column(name = DataBaseConstant.TransMatching.CARRIER2_OPERATOR_CODE, length = 13)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.REQ_CNS_LINE_ITEM_ID)
    @Column(name = DataBaseConstant.TransMatching.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @JsonProperty(DataBaseConstant.TransMatching.CNS_LINE_ITEM_ID)
    @Column(name = DataBaseConstant.TransMatching.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;

    @JsonProperty(DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID)
    @Column(name = DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ID)
    @Column(name = DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceId;

    @JsonProperty(DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ITEM_ID)
    @Column(name = DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ITEM_ID, nullable = false)
    private Long vehicleAvbResourceItemId;

    @JsonProperty(DataBaseConstant.TransMatching.TRAILER_NUMBER)
    @Column(name = DataBaseConstant.TransMatching.TRAILER_NUMBER, precision = 9, scale = 0, nullable = false)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransMatching.DEPARTURE_FROM)
    @Column(name = DataBaseConstant.TransMatching.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransMatching.ARRIVAL_TO)
    @Column(name = DataBaseConstant.TransMatching.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransMatching.TRANSPORT_DATE)
    @Column(name = DataBaseConstant.TransMatching.TRANSPORT_DATE)
    private LocalDate transportDate;

    @JsonProperty(DataBaseConstant.TransMatching.STATUS)
    @Column(name = DataBaseConstant.TransMatching.STATUS)
    private Integer status;

    @JsonProperty(DataBaseConstant.TransMatching.REQUEST_SNAPSHOT)
    @Column(name = DataBaseConstant.TransMatching.REQUEST_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = CnsLineItemByDateSnapshotConverter.class)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransMatching.PROPOSE_SNAPSHOT)
    @Column(name = DataBaseConstant.TransMatching.PROPOSE_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = VehicleAvbResourceItemSnapshotConverter.class)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty(DataBaseConstant.TransMatching.MATCHING_TYPE)
    @Column(name = DataBaseConstant.TransMatching.MATCHING_TYPE)
    private Integer matchingType;

    @Column(name = DataBaseConstant.TransMatching.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @Column(name = DataBaseConstant.TransMatching.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.TransMatching.TRSP_INSTRUCTION_ID)
    private String trspInstructionId;

    @Column(columnDefinition = DataBaseConstant.COL_TYPE_TEXT)
    private String freeWord;

    @Column(name = DataBaseConstant.TransMatching.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @Column(name = DataBaseConstant.TransMatching.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @Column(name = DataBaseConstant.TransMatching.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @Column(name = DataBaseConstant.TransMatching.IS_EMERGENCY)
    private Integer isEmergency;

    @Override
    public Long getId() {
        return this.id;
    }

    @PrePersist
    @PreUpdate
    public void generateDefaultValue() {
        if (batchId == null) {
            batchId = System.currentTimeMillis();
        }
        if (this.trailerNumber == null) {
            this.trailerNumber = BigDecimal.ONE;
        }
    }
}