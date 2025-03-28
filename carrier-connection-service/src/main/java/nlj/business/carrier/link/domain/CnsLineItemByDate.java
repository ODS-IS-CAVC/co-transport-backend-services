package nlj.business.carrier.link.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
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
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 貨物明細日付エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.CnsLineItemByDate.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CnsLineItemByDate extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.CnsLineItemByDate.REQ_CNS_LINE_ITEM_ID)
    private Long reqCnsLineItemId;

    @Column(name = DataBaseConstant.CnsLineItemByDate.OPERATOR_ID, nullable = true)
    private String operatorId;

    @Column(name = DataBaseConstant.CnsLineItemByDate.OPERATOR_CODE, nullable = true, length = 13)
    private String operatorCode;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANS_PLAN_ID, nullable = true)
    private String transPlanId;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANSPORT_CODE)
    private String transportCode;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANSPORT_NAME)
    private String transportName;

    @Column(name = DataBaseConstant.CnsLineItemByDate.COLLECTION_DATE)
    private LocalDate collectionDate;

    @Column(name = DataBaseConstant.CnsLineItemByDate.COLLECTION_TIME_FROM)
    private LocalTime collectionTimeFrom;

    @Column(name = DataBaseConstant.CnsLineItemByDate.COLLECTION_TIME_TO)
    private LocalTime collectionTimeTo;

    @Column(name = DataBaseConstant.CnsLineItemByDate.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.CnsLineItemByDate.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRAILER_NUMBER)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRAILER_NUMBER_REST)
    private Integer trailerNumberRest;

    @Column(name = DataBaseConstant.CnsLineItemByDate.PRICE_PER_UNIT)
    private BigDecimal pricePerUnit;

    @Column(name = DataBaseConstant.CnsLineItemByDate.VEHICLE_CONDITION)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    @Column(name = DataBaseConstant.CnsLineItemByDate.OUTER_PACKAGE_CODE)
    private Integer outerPackageCode;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TOTAL_LENGTH, precision = 11, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TOTAL_WIDTH, precision = 11, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TOTAL_HEIGHT, precision = 11, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.CnsLineItemByDate.WEIGHT, precision = 11, scale = 3)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.CnsLineItemByDate.SPECIAL_INSTRUCTIONS, length = 500)
    private String specialInstructions;

    @Column(name = DataBaseConstant.CnsLineItemByDate.STATUS, length = 500)
    private Integer status;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANS_TYPE)
    private Integer transType;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANSPORT_DATE)
    private LocalDate transportDate;

    @Column(name = DataBaseConstant.CnsLineItemByDate.PROPOSE_PRICE)
    private BigDecimal proposePrice;

    @Column(name = DataBaseConstant.CnsLineItemByDate.PROPOSE_DEPARTURE_TIME)
    private LocalTime proposeDepartureTime;

    @Column(name = DataBaseConstant.CnsLineItemByDate.PROPOSE_ARRIVAL_TIME)
    private LocalTime proposeArrivalTime;

    @Column(name = DataBaseConstant.CnsLineItemByDate.ISTD_TOLL_VOL_MEAS)
    private BigDecimal istdTollVolMeas;

    @Column(name = DataBaseConstant.CnsLineItemByDate.VOL_UNT_CD)
    private String volUntCd;

    @Column(name = DataBaseConstant.CnsLineItemByDate.NUM_UNT_CD)
    private String numUntCd;

    @Column(name = DataBaseConstant.CnsLineItemByDate.WEIG_UNT_CD)
    private String weigUntCd;

    @Column(name = DataBaseConstant.CnsLineItemByDate.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;
    @Column(name = DataBaseConstant.CnsLineItemByDate.TRANS_ORDER_ID)
    private Long transOrderId;
    @Column(name = DataBaseConstant.CnsLineItemByDate.CNS_LINE_ITEM_ID)
    private Long cnsLineItemId;
    @Column(name = DataBaseConstant.CnsLineItemByDate.TRSP_PLAN_ITEM_ID)
    private Long trspPlanItemId;
    @Column(name = DataBaseConstant.CnsLineItemByDate.OPERATOR_NAME)
    private String operatorName;
    @Column(name = DataBaseConstant.CnsLineItemByDate.IS_EMERGENCY)
    private Integer isEmergency;

    @Override
    public Long getId() {
        return this.id;
    }

    @PrePersist
    @PreUpdate
    public void generateDefaultValue() {
        if (this.isEmergency == null) {
            isEmergency = 0;
        }
        if (this.totalLength == null) {
            this.totalLength = BigDecimal.ZERO;
        }
        if (this.totalHeight == null) {
            this.totalHeight = BigDecimal.ZERO;
        }
        if (this.totalWidth == null) {
            this.totalWidth = BigDecimal.ZERO;
        }
        if (this.weight == null) {
            this.weight = BigDecimal.ZERO;
        }
    }
}
