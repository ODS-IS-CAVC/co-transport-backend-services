package nlj.business.shipper.domain;

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
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 荷物情報インポートエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = DataBaseConstant.CargoInfoImport.TABLE)
public class CargoInfoImport extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8336471252532407393L;

    @Id
    @Column(name = DataBaseConstant.CargoInfoImport.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = DataBaseConstant.CargoInfoImport.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.CargoInfoImport.FILE_PATH, length = 500)
    private String filePath;

    @Column(name = DataBaseConstant.CargoInfoImport.NUMBER_SUCCESS)
    private Integer numberSuccess;

    @Column(name = DataBaseConstant.CargoInfoImport.NUMBER_FAILURE)
    private Integer numberFailure;

    @Column(name = DataBaseConstant.CargoInfoImport.STATUS)
    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
