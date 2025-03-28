package nlj.business.carrier.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両ダイアグラムインポート.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramItem.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagramItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagramItem.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramItem.OPERATOR_ID)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagramItem.VEHICLE_DIAGRAM_ID)
    private VehicleDiagram vehicleDiagram;

    @Column(name = DataBaseConstant.VehicleDiagramItem.DAY)
    private LocalDate day;

    @Column(name = DataBaseConstant.VehicleDiagramItem.TRIP_NAME)
    private String tripName;

    @Column(name = DataBaseConstant.VehicleDiagramItem.DEPARTURE_TIME)
    private LocalTime departureTime;

    @Column(name = DataBaseConstant.VehicleDiagramItem.ARRIVAL_TIME)
    private LocalTime arrivalTime;
    @Column(name = DataBaseConstant.VehicleDiagramItem.ONE_WAY_TIME)
    private LocalTime oneWayTime;

    @Column(name = DataBaseConstant.VehicleDiagramItem.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleDiagramItem.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleDiagramItem.SIP_TRACK_ID, length = 250)
    private String sipTrackId;
    @Column(name = DataBaseConstant.VehicleDiagramItem.IS_PRIVATE)
    private Boolean isPrivate;

    @Column(name = DataBaseConstant.VehicleDiagramItem.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.VehicleDiagramItem.INCIDENT_TYPE)
    private Integer incidentType;

    @Column(name = DataBaseConstant.VehicleDiagramItem.INCIDENT_MSG)
    private String incidentMsg;

    @Column(name = DataBaseConstant.VehicleDiagramItem.IS_EMERGENCY)
    private Integer isEmergency = 0;

    @OneToMany(mappedBy = DataBaseConstant.VehicleDiagramItem.MAPPED_BY, cascade = CascadeType.ALL)
    private List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers;

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
        if (this.status == null) {
            this.status = 0;
        }
    }
}
