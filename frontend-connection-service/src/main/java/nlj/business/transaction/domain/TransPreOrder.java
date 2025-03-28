package nlj.business.transaction.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
import nlj.business.transaction.converter.TransportPlanItemSnapshotConverter;
import nlj.business.transaction.converter.VehicleDiagramItemTrailerSnapshotConverter;
import nlj.business.transaction.dto.TransportPlanItemSnapshot;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerSnapshot;
import org.hibernate.annotations.ColumnTransformer;

/**
 * <PRE>
 * 取引事前注文エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransPreOrder.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransPreOrder extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(DataBaseConstant.TransPreOrder.CARRIER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransPreOrder.CARRIER_OPERATOR_ID, nullable = false)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransPreOrder.SHIPPER_OPERATOR_ID)
    @Column(name = DataBaseConstant.TransPreOrder.SHIPPER_OPERATOR_ID, nullable = false)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANS_MATCHING_ID)
    @OneToOne
    @JoinColumn(name = DataBaseConstant.TransPreOrder.TRANS_MATCHING_ID, nullable = false)
    private TransMatching transMatching;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ID)
    @Column(name = DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ID, nullable = false)
    private Long transportPlanId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_ID)
    @Column(name = DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_ID, nullable = false)
    private Long transportPlanItemId;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_ID)
    @Column(name = DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_ID, nullable = false)
    private Long vehicleDiagramItemId;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    @Column(name = DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_ID, nullable = false)
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_SNAPSHOT)
    @Column(name = DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = TransportPlanItemSnapshotConverter.class)
    private TransportPlanItemSnapshot transportPlanItemSnapshot;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_SNAPSHOT)
    @Column(name = DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_SNAPSHOT, columnDefinition = DataBaseConstant.COl_TYPE_JSONB, nullable = false)
    @ColumnTransformer(write = DataBaseConstant.JSONB_TRANSFORMER)
    @Convert(converter = VehicleDiagramItemTrailerSnapshotConverter.class)
    private VehicleDiagramItemTrailerSnapshot vehicleDiagramItemTrailerSnapshot;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRAILER_NUMBER)
    @Column(name = DataBaseConstant.TransPreOrder.TRAILER_NUMBER, precision = 9, scale = 0)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransPreOrder.DEPARTURE_FROM)
    @Column(name = DataBaseConstant.TransPreOrder.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransPreOrder.ARRIVAL_TO)
    @Column(name = DataBaseConstant.TransPreOrder.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_DATE)
    @Column(name = DataBaseConstant.TransPreOrder.TRANSPORT_DATE)
    private LocalDate transportDate;

    @JsonProperty(DataBaseConstant.TransPreOrder.PRICE)
    @Column(name = DataBaseConstant.TransPreOrder.PRICE, precision = 9, scale = 0)
    private BigDecimal price;

    @JsonProperty(DataBaseConstant.TransPreOrder.COLLECTION_TIME_FROM)
    @Column(name = DataBaseConstant.TransPreOrder.COLLECTION_TIME_FROM)
    private LocalTime collectionTimeFrom;

    @JsonProperty(DataBaseConstant.TransPreOrder.COLLECTION_TIME_TO)
    @Column(name = DataBaseConstant.TransPreOrder.COLLECTION_TIME_TO)
    private LocalTime collectionTimeTo;

    @JsonProperty(DataBaseConstant.TransPreOrder.STATUS)
    @Column(name = DataBaseConstant.TransPreOrder.STATUS)
    private Integer status;

//    @OneToOne(mappedBy = DataBaseConstant.TransPreOrder.MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private TransOrder transOrder;

    @Override
    public Long getId() {
        return this.id;
    }
}