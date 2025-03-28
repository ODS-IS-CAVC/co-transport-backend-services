package nlj.business.carrier.domain;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.converter.AdjustmentPriceConverter;
import nlj.business.carrier.converter.CutOffPriceConverter;
import nlj.business.carrier.converter.DayWeekConverter;
import nlj.business.carrier.domain.opt.DayAdjustment;
import nlj.business.carrier.domain.opt.DayTime;

/**
 * <PRE>
 * 車両ダイアグラム.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagram.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagram extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagram.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagram.DIAGRAM_HEAD_ID)
    private VehicleDiagramHead vehicleDiagramHead;

    @Column(name = DataBaseConstant.VehicleDiagram.ROUND_TRIP_TYPE)
    private Integer roundTripType;

    @Column(name = DataBaseConstant.VehicleDiagram.TRIP_NAME)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagram.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleDiagram.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleDiagram.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagram.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleDiagram.DAY_WEEK, columnDefinition = "TEXT")
    @Convert(converter = DayWeekConverter.class)
    private Map<String, DayTime> dayWeek;

    @Column(name = DataBaseConstant.VehicleDiagram.ADJUSTMENT_PRICE, columnDefinition = "TEXT")
    @Convert(converter = AdjustmentPriceConverter.class)
    private Map<String, DayAdjustment> adjustmentPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.COMMON_PRICE)
    private BigDecimal commonPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.CUT_OFF_PRICE, columnDefinition = "TEXT")
    @Convert(converter = CutOffPriceConverter.class)
    private Map<String, BigDecimal> cutOffPrice;

    @Column(name = DataBaseConstant.VehicleDiagram.STATUS)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleDiagram", fetch = FetchType.EAGER)
    private List<VehicleDiagramItem> vehicleDiagramItems = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}