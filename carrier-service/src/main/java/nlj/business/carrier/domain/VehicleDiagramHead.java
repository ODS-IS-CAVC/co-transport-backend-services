package nlj.business.carrier.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両ダイアグラムヘッダー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramHead.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagramHead extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagramHead.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramHead.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ONE_WAY_TIME)
    private String oneWayTime;

    @Column(name = DataBaseConstant.VehicleDiagramHead.START_DATE)
    private LocalDate startDate;

    @Column(name = DataBaseConstant.VehicleDiagramHead.END_DATE)
    private LocalDate endDate;

    @Column(name = DataBaseConstant.VehicleDiagramHead.REPEAT_DAY)
    private Integer repeatDay;

    @Column(name = DataBaseConstant.VehicleDiagramHead.TRAILER_NUMBER)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.VehicleDiagramHead.IS_ROUND_TRIP)
    private Boolean isRoundTrip;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ORIGIN_TYPE)
    private Integer originType;

    @Column(name = DataBaseConstant.VehicleDiagramHead.IMPORT_ID)
    private Long importId;

    @Column(name = DataBaseConstant.VehicleDiagramHead.STATUS)
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleDiagramHead")
    private List<VehicleDiagram> vehicleDiagrams = new ArrayList<>();


    @Override
    public Long getId() {
        return this.id;
    }
} 