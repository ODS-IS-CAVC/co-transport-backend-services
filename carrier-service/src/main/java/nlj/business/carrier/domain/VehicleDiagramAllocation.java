package nlj.business.carrier.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
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
 * 車両ダイアグラム割り当てエンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramAllocation.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagramAllocation extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagramAllocation.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_INFO_ID)
    private Long vehicleInfoId;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.DISPLAY_ORDER)
    private Integer displayOrder;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.ASSIGN_TYPE)
    private Integer assignType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleDiagramAllocation", fetch = FetchType.EAGER)
    private List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}
