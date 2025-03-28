package nlj.business.shipper.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlan.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransportPlan extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TransportPlan.ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlan.OPERATOR_ID)
    private ShipperOperator shipperOperator;

    @Column(name = DataBaseConstant.TransportPlan.TRANSPORT_CODE, length = 20)
    private String transportCode;

    @Column(name = DataBaseConstant.TransportPlan.TRANSPORT_NAME, length = 24, unique = true)
    private String transportName;

    @Column(name = DataBaseConstant.TransportPlan.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.TransportPlan.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.TransportPlan.COLLECTION_DATE_FROM)
    private LocalDate collectionDateFrom;

    @Column(name = DataBaseConstant.TransportPlan.COLLECTION_DATE_TO)
    private LocalDate collectionDateTo;

    @Column(name = DataBaseConstant.TransportPlan.TRAILER_NUMBER)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.TransportPlan.REPEAT_DAY)
    private Integer repeatDay;

    @Column(name = DataBaseConstant.TransportPlan.DAY_WEEK)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;

    @Column(name = DataBaseConstant.TransportPlan.COLLECTION_TIME_FROM)
    private LocalTime collectionTimeFrom;

    @Column(name = DataBaseConstant.TransportPlan.COLLECTION_TIME_TO)
    private LocalTime collectionTimeTo;

    @Column(name = DataBaseConstant.TransportPlan.PRICE_PER_UNIT, precision = 9, scale = 0)
    private BigDecimal pricePerUnit;

    @Column(name = DataBaseConstant.TransportPlan.VEHICLE_CONDITION)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    @Column(name = DataBaseConstant.TransportPlan.OUTER_PACKAGE_CODE)
    private Long outerPackageCode;

    @Column(name = DataBaseConstant.TransportPlan.TOTAL_LENGTH, precision = 12, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.TransportPlan.TOTAL_WIDTH, precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.TransportPlan.TOTAL_HEIGHT, precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.TransportPlan.TOTAL_WEIGHT, precision = 12, scale = 3)
    private BigDecimal totalWeight;

    @Column(name = DataBaseConstant.TransportPlan.SPECIAL_INSTRUCTIONS, length = 500)
    private String specialInstructions;

    @Column(name = DataBaseConstant.TransportPlan.ORIGIN_TYPE)
    private Integer originType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlan.IMPORT_ID)
    private TransportPlanImport transportPlanImport;

    @Column(name = DataBaseConstant.TransportPlan.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.TransportPlanItem.IS_PRIVATE)
    private Boolean isPrivate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportPlan")
    private List<TransportPlanItem> transportPlanItems = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}