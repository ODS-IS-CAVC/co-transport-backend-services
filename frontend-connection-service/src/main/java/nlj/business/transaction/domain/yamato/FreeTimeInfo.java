package nlj.business.transaction.domain.yamato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 車両情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.FreeTimeInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreeTimeInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.FreeTimeInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.FreeTimeInfo.VEHICLE_AVB_RESOURCE_ID, nullable = false)
    @JsonIgnore
    private VehicleAvailabilityResource vehicleAvailabilityResource;

    @Column(name = DataBaseConstant.FreeTimeInfo.FREE_TIME, nullable = true, precision = 5, scale = 1)
    private BigDecimal freeTime;

    @Column(name = DataBaseConstant.FreeTimeInfo.FREE_TIME_FEE, nullable = true, precision = 10, scale = 0)
    private BigDecimal freeTimeFee;

    @Override
    public Long getId() {
        return this.id;
    }
}
