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
 * 車両利用可能リソース明細エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.VehicleAvbResourceItem.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VehicleAvbResourceItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.OPERATOR_CODE, nullable = false, length = 13)
    private String operatorCode;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleAvbResourceItem.CAR_INFO_ID, nullable = false)
    private CarInfo carInfo;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_AVB_RESOURCE_ID, nullable = false)
    private VehicleAvailabilityResource vehicleAvailabilityResource;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TRIP_NAME)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_TIME_MIN)
    private LocalTime departureTimeMin;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_TIME_MAX)
    private LocalTime departureTimeMax;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.ADJUSTMENT_PRICE)
    private Integer adjustmentPrice;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DISPLAY_ORDER)
    private Integer displayOrder;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.ASSIGN_TYPE)
    private Integer assignType;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_SIZE)
    private Integer vehicleSize;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.PRICE, precision = 9)
    private BigDecimal price;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.GIAI, nullable = false, length = 50)
    private String giai;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.CUT_OFF_INFO_ID, nullable = true)
    private Long cutOffInfoId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.CUT_OFF_TIME, nullable = true, precision = 5, scale = 1)
    private BigDecimal cutOffTime;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.CUT_OFF_FEE, nullable = true, precision = 10, scale = 0)
    private BigDecimal cutOffFee;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.FREE_TIME_INFO_ID, nullable = true)
    private Long freeTimeInfoId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.FREE_TIME_TIME, nullable = true, precision = 5, scale = 1)
    private BigDecimal freeTimeTime;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.FREE_TIME_FEE, nullable = true, precision = 10, scale = 0)
    private BigDecimal freeTimeFee;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TRANS_TYPE)
    private Integer transType;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.TRANS_ORDER_ID)
    private Long transOrderId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.OPERATOR_NAME)
    private String operatorName;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_NAME)
    private String vehicleName;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @Column(name = DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Override
    public Long getId() {
        return this.id;
    }

    @PrePersist
    @PreUpdate
    public void generateDefaultValue() {
        if (this.totalLength == null) {
            this.totalLength = BigDecimal.ZERO;
        }
        if (this.totalHeight == null) {
            this.totalHeight = BigDecimal.ZERO;
        }
        if (this.totalWidth == null) {
            this.totalWidth = BigDecimal.ZERO;
        }
        if (this.maxPayload == null) {
            this.maxPayload = BigDecimal.ZERO;
        }
    }
}
