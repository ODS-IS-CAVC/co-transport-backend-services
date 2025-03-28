package nlj.business.transaction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.CutOffInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CutOffInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.CutOffInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.CutOffInfo.VEHICLE_AVB_RESOURCE_ID, nullable = false)
    private Long vehicleAvbResourceId;

    @Column(name = DataBaseConstant.CutOffInfo.CUT_OFF_TIME, nullable = true, precision = 10, scale = 1)
    private BigDecimal cutOffTime;

    @Column(name = DataBaseConstant.CutOffInfo.CUT_OFF_FEE, nullable = true, precision = 10, scale = 0)
    private BigDecimal cutOffFee;

    @Override
    public Long getId() {
        return this.id;
    }
}
