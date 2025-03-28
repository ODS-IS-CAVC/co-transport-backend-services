package nlj.business.shipper.domain;

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
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画インポートエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlanImport.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransportPlanImport extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TransportPlanImport.ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlanImport.OPERATE_ID)
    private ShipperOperator shipperOperator;

    @Column(name = DataBaseConstant.TransportPlanImport.FILE_PATH, length = 500)
    private String filePath;

    @Column(name = DataBaseConstant.TransportPlanImport.NUMBER_SUCCESS)
    private Integer numberSuccess;

    @Column(name = DataBaseConstant.TransportPlanImport.NUMBER_FAILURE)
    private Integer numberFailure;

    @Column(name = DataBaseConstant.TransportPlanImport.STATUS)
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportPlanImport")
    private List<TransportPlan> transportPlans = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}