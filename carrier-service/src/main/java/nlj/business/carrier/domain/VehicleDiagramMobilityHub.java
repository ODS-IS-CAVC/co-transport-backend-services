package nlj.business.carrier.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両図モビリティ ハブのエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.VehicleDiagramMobilityHub.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VehicleDiagramMobilityHub extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.ID, nullable = false)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ITEM_ID, nullable = false)
    private Long vehicleDiagramItemId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ALLOCATION_ID)
    private Long vehicleDiagramAllocationId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.TYPE)
    private Integer type = 0;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.FREIGHT_ID, length = 20)
    private String freightId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.MOBILITY_HUB_ID, length = 50)
    private String mobilityHubId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.RESERVATION_STATUS)
    private Integer reservationStatus = 0;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.TRUCK_ID, length = 50)
    private String truckId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.SIZE_CLASS, length = 100)
    private String sizeClass;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VALID_FROM)
    private LocalDateTime validFrom;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.VALID_UNTIL)
    private LocalDateTime validUntil;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.SLOT_ID, length = 50)
    private String slotId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.MH_RESERVATION_ID, length = 20)
    private String mhReservationId;

    @Column(name = DataBaseConstant.VehicleDiagramMobilityHub.STATUS)
    private Integer status;

    @Override
    public Long getId() {
        return id;
    }
}
