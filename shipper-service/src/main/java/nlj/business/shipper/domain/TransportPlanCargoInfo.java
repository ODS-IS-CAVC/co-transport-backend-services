package nlj.business.shipper.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlanCargoInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransportPlanCargoInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TransportPlanCargoInfo.ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.TransportPlanCargoInfo.OPERATOR_ID)
    private ShipperOperator shipperOperator;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TRANSPORT_PLAN_ID)
    private Long transportPlanId;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.CARGO_INFO_ID)
    private Long cargoInfoId;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.CARGO_NAME, length = 20)
    private String cargoName;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.PRICE_PER_UNIT, precision = 9, scale = 0)
    private BigDecimal pricePerUnit;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.OUTER_PACKAGE_CODE)
    private Long outerPackageCode;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TOTAL_LENGTH, precision = 12, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TOTAL_WIDTH, precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TOTAL_HEIGHT, precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TOTAL_WEIGHT, precision = 12, scale = 3)
    private BigDecimal totalWeight;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.TEMP_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> tempRange;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.SPECIAL_INSTRUCTIONS, length = 500)
    private String specialInstructions;

    @Column(name = DataBaseConstant.TransportPlanCargoInfo.STATUS)
    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}