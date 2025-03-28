package nlj.business.shipper.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 荷物情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = DataBaseConstant.CargoInfo.TABLE)
public class CargoInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8336471252532407393L;

    @Id
    @Column(name = DataBaseConstant.CargoInfo.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = DataBaseConstant.CargoInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.CargoInfo.CARGO_NAME, nullable = false, length = 20)
    private String cargoName;

    @Column(name = DataBaseConstant.CargoInfo.OUTER_PACKAGE_CODE, nullable = false)
    private Integer outerPackageCode;

    @Column(name = DataBaseConstant.CargoInfo.TOTAL_LENGTH, precision = 9, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.CargoInfo.TOTAL_WIDTH, precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.CargoInfo.TOTAL_HEIGHT, precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.CargoInfo.WEIGHT, precision = 9, scale = 3)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.CargoInfo.TEMP_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> tempRange;

    @Column(name = DataBaseConstant.CargoInfo.SPECIAL_INSTRUCTIONS, length = 100)
    private String specialInstructions;

    @Column(name = DataBaseConstant.CargoInfo.IMPORT_ID)
    private Long importId;

    @Column(name = DataBaseConstant.CargoInfo.STATUS)
    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
