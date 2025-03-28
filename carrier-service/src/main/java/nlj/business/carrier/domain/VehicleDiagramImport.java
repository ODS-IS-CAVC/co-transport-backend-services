package nlj.business.carrier.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両図面のインポート。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataBaseConstant.VehicleDiagramImport.TABLE)
@Entity
public class VehicleDiagramImport extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1183120473873840786L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleDiagramImport.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleDiagramImport.FILE_PATH, length = 500)
    private String filePath;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_SUCCESS)
    private Integer numberSuccess;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_FAILURE)
    private Integer numberFailure;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_TRACTOR_SUCCESS)
    private Integer numberTractorSuccess;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_TRACTOR_FAILURE)
    private Integer numberTractorFailure;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_TRAILER_SUCCESS)
    private Integer numberTrailerSuccess;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_TRAILER_FAILURE)
    private Integer numberTrailerFailure;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_ALLOCATION_SUCCESS)
    private Integer numberAllocationSuccess;

    @Column(name = DataBaseConstant.VehicleDiagramImport.NUMBER_ALLOCATION_FAILURE)
    private Integer numberAllocationFailure;

    @Column(name = DataBaseConstant.VehicleDiagramImport.STATUS)
    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
