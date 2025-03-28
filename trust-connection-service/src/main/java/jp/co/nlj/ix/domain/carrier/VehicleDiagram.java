package jp.co.nlj.ix.domain.carrier;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * VehicleDiagramクラスは、車両図エンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagram.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagram extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagram.DIAGRAM_HEAD_ID, nullable = false)
    private VehicleDiagramHead vehicleDiagramHead;

    @OneToMany(mappedBy = DataBaseConstant.VehicleDiagram.MAPPED_BY, cascade = CascadeType.ALL)
    private List<VehicleDiagramItem> vehicleDiagramItems;

    @Column(name = DataBaseConstant.VehicleDiagram.OPERATOR_ID)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleDiagram.ROUND_TRIP_TYPE)
    private Integer roundTripType;

    @Column(name = DataBaseConstant.VehicleDiagram.TRIP_NAME, length = 250)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagram.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleDiagram.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleDiagram.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagram.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleDiagram.DAY_WEEK)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;

    @Column(name = DataBaseConstant.VehicleDiagram.ADJUSTMENT_PRICE, columnDefinition = "TEXT")
    private String adjustmentPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.COMMON_PRICE, precision = 9, scale = 0)
    private BigDecimal commonPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.CUT_OFF_PRICE, columnDefinition = "TEXT")
    private String cutOffPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.STATUS)
    private Integer status;

}
