package nlj.business.carrier.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 船のフリータイム情報エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ShipFreeTimeInfo.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShipFreeTimeInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1362653625327904155L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ShipFreeTimeInfo.ID, nullable = false, updatable = false)
    private Long id;

    @Column(name = DataBaseConstant.ShipFreeTimeInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ShipFreeTimeInfo.SHIP_TO_PRTY_ID, nullable = false)
    private Long shipToPrtyId;

    @Column(name = DataBaseConstant.ShipFreeTimeInfo.FREE_TIME, precision = 5, scale = 1)
    private BigDecimal freeTime;

    @Column(name = DataBaseConstant.ShipFreeTimeInfo.FREE_TIME_FEE, precision = 10, scale = 0)
    private BigDecimal freeTimeFee;

    @Override
    public Long getId() {
        return this.id;
    }
}
