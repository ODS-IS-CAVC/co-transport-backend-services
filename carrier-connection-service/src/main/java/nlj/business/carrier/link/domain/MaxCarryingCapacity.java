package nlj.business.carrier.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 最大積載量エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.MaxCarryingCapacity.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaxCarryingCapacity extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.MaxCarryingCapacity.ID)
    private Long id;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.OPERATOR_ID)
    private String operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.MaxCarryingCapacity.VEHICLE_INFO_ID)
    private VehicleInfo vehicleInfo;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.PACKAGE_CODE, length = 3)
    private String packageCode;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.PACKAGE_NAME_KANJI, length = 20)
    private String packageNameKanji;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.WIDTH, precision = 15, scale = 6)
    private BigDecimal width;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.HEIGHT, precision = 15, scale = 6)
    private BigDecimal height;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.DEPTH, precision = 15, scale = 6)
    private BigDecimal depth;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.DIMENSION_UNIT_CODE, length = 3)
    private String dimensionUnitCode;

    @Column(name = DataBaseConstant.MaxCarryingCapacity.MAX_LOAD_QUANTITY)
    private Integer maxLoadQuantity;

    @Override
    public Long getId() {
        return this.id;
    }
}
