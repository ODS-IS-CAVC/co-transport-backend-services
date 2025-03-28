package nlj.business.transaction.domain.carrier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 車両図アイテムキャリア。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramItemCarrier.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramItemCarrier extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.TRIP_NAME, length = 250)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.PRICE, precision = 9)
    private BigDecimal price;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.STATUS)
    private Integer status;
    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.SIP_TRACK_ID, length = 250)
    private String sipTrackId;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.INCIDENT_TYPE)
    private Integer incidentType;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.INCIDENT_MSG)
    private String incidentMsg;

    @Column(name = DataBaseConstant.VehicleDiagramItemCarrier.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

}
