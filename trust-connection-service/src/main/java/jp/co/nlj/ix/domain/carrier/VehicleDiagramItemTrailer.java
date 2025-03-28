package jp.co.nlj.ix.domain.carrier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * VehicleDiagramItemTrailerクラスは、車両図アイテムトレーラエンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramItemTrailer.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramItemTrailer extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.OPERATOR_ID)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ID, nullable = false)
    private VehicleDiagram vehicleDiagram;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ITEM_ID, nullable = false)
    private VehicleDiagramItem vehicleDiagramItem;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ALLOCATION_ID, nullable = false)
    private VehicleDiagramAllocation vehicleDiagramAllocation;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.TRIP_NAME)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.ADJUSTMENT_PRICE)
    private Integer adjustmentPrice;
    @Column(name = DataBaseConstant.VehicleDiagramItemTrailer.STATUS)
    private Integer status;
}
