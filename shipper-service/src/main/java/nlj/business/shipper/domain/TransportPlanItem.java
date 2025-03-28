package nlj.business.shipper.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlanItem.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransportPlanItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TransportPlanItem.ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlanItem.OPERATOR_ID)
    private ShipperOperator shipperOperator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlanItem.TRANSPORT_PLAN_ID)
    private TransportPlan transportPlan;

    @Column(name = DataBaseConstant.TransportPlanItem.TRANSPORT_CODE)
    private String transportCode;

    @Column(name = DataBaseConstant.TransportPlanItem.TRANSPORT_NAME)
    private String transportName;

    @Column(name = DataBaseConstant.TransportPlanItem.COLLECTION_DATE)
    private LocalDate collectionDate;

    @Column(name = DataBaseConstant.TransportPlanItem.COLLECTION_TIME_FROM)
    private LocalTime collectionTimeFrom;

    @Column(name = DataBaseConstant.TransportPlanItem.COLLECTION_TIME_TO)
    private LocalTime collectionTimeTo;

    @Column(name = DataBaseConstant.TransportPlanItem.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.TransportPlanItem.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.TransportPlanItem.TRAILER_NUMBER)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.TransportPlanItem.TRAILER_NUMBER_REST)
    private Integer trailerNumberRest;

    @Column(name = DataBaseConstant.TransportPlanItem.PRICE_PER_UNIT, precision = 12, scale = 0)
    private BigDecimal pricePerUnit;

    @Column(name = DataBaseConstant.TransportPlanItem.TEMP_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> tempRange;

    @Column(name = DataBaseConstant.TransportPlanItem.OUTER_PACKAGE_CODE)
    private Integer outerPackageCode;

    @Column(name = DataBaseConstant.TransportPlanItem.TOTAL_LENGTH, precision = 12, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.TransportPlanItem.TOTAL_WIDTH, precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.TransportPlanItem.TOTAL_HEIGHT, precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.TransportPlanItem.WEIGHT, precision = 12, scale = 3)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.TransportPlanItem.SPECIAL_INSTRUCTIONS)
    private String specialInstructions;

    @Column(name = DataBaseConstant.TransportPlanItem.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.TransportPlanItem.CARGO_INFO_ID)
    private Long cargoInfoId;

    @Column(name = DataBaseConstant.TransportPlanItem.IS_PRIVATE)
    private Boolean isPrivate;

    @Override
    public Long getId() {
        return this.id;
    }
}