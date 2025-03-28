package jp.co.nlj.ix.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <PRE>
 * 船舶の運航停止情報<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ShipCutOffInfo.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShipCutOffInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -5956219004804723865L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ShipCutOffInfo.ID, nullable = false, updatable = false)
    private Long id;

    @Column(name = DataBaseConstant.ShipCutOffInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ShipCutOffInfo.SHIP_FROM_PRTY_ID, nullable = false)
    private Long shipFromPrtyId;

    @Column(name = DataBaseConstant.ShipCutOffInfo.CUT_OFF_TIME, precision = 5, scale = 1)
    private BigDecimal cutOffTime;

    @Column(name = DataBaseConstant.ShipCutOffInfo.CUT_OFF_FEE, precision = 10, scale = 0)
    private BigDecimal cutOffFee;

    @Override
    public Long getId() {
        return this.id;
    }
}
