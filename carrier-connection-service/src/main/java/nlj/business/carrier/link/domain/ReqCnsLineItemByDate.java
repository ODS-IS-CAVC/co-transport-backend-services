package nlj.business.carrier.link.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 要求貨物明細日付エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqCnsLineItemByDate.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReqCnsLineItemByDate extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.ReqCnsLineItemByDate.CNS_LINE_ITEM_ID, nullable = false)
    private ReqCnsLineItem reqCnsLineItem;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TRANS_PLAN_ID, nullable = false)
    private String transPlanId;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.OPERATOR_CODE, nullable = true, length = 13)
    private String operatorCode;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TRANSPORT_CODE, nullable = false)
    private String transportCode;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TRANSPORT_NAME, nullable = false)
    private String transportName;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.COLLECTION_DATE, nullable = false)
    private LocalDate collectionDate;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.COLLECTION_TIME_FROM, nullable = false)
    private LocalTime collectionTimeFrom;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.COLLECTION_TIME_TO, nullable = false)
    private LocalTime collectionTimeTo;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.DEPARTURE_FROM, nullable = false)
    private Long departureFrom;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.ARRIVAL_TO, nullable = false)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TRAILER_NUMBER, nullable = false)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TRAILER_NUMBER_REST, nullable = false)
    private Integer trailerNumberRest;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.PRICE_PER_UNIT, nullable = false)
    private BigDecimal pricePerUnit;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.VEHICLE_CONDITION, nullable = false)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.OUTER_PACKAGE_CODE, nullable = false)
    private Integer outerPackageCode;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TOTAL_LENGTH, precision = 9, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TOTAL_WIDTH, precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.TOTAL_HEIGHT, precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.WEIGHT, precision = 9, scale = 3)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.SPECIAL_INSTRUCTIONS, length = 500)
    private String specialInstructions;

    @Column(name = DataBaseConstant.ReqCnsLineItemByDate.STATUS, length = 500)
    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
