package jp.co.nlj.ix.domain.carrier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * VehicleDiagramItemクラスは、車両図アイテムエンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramItem.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleDiagramItem.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.VehicleDiagramItem.TRIP_NAME, length = 250)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagramItem.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagramItem.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleDiagramItem.PRICE, precision = 9)
    private BigDecimal price;

    @Column(name = DataBaseConstant.VehicleDiagramItem.STATUS)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.VehicleDiagramItem.VEHICLE_DIAGRAM_ID, nullable = false)
    private VehicleDiagram vehicleDiagram;

    @OneToMany(mappedBy = DataBaseConstant.VehicleDiagramItem.MAPPED_BY, cascade = CascadeType.ALL)
    private List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers;

}
