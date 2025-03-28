package nlj.business.transaction.domain.carrier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 車両図割当。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramAllocation.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramAllocation extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.OPERATOR_ID)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_DIAGRAM_ID)
    private Long vehicleDiagramId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_INFO_ID, nullable = false)
    private VehicleInfo vehicleInfo;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.DISPLAY_ORDER)
    private Integer displayOrder;

    @Column(name = DataBaseConstant.VehicleDiagramAllocation.ASSIGN_TYPE)
    private Integer assignType;
}
