package nlj.business.transaction.domain.trans;

import com.next.logistic.convert.IntegerArrayToStringConverter;
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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 市場車両図アイテムトレーラエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketVehicleDiagramItemTrailer extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.OPERATOR_ID)
    private String operatorId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ALLOCATION_ID)
    private Integer vehicleDiagramAllocationId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_INFO_ID)
    private Integer vehicleInfoId;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TRIP_NAME)
    private String tripName;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.PRICE)
    private Integer price;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_CODE)
    private String vehicleCode;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_NAME)
    private String vehicleName;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_SIZE)
    private Integer vehicleSize;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.GROUND_CLEARANCE)
    private BigDecimal groundClearance;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.DOOR_HEIGHT)
    private BigDecimal doorHeight;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_SPECIFICATION)
    private String bodySpecification;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_SHAPE)
    private String bodyShape;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_CONSTRUCTION)
    private String bodyConstruction;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.IMAGES)
    private String[] images;

    @Column(name = DataBaseConstant.MarketVehicleDiagramItemTrailer.STATUS)
    private Integer status;
}
