package nlj.business.transaction.domain.shipper;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 輸送計画エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlan.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlan extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = DataBaseConstant.TransportPlan.MAPPED_BY, cascade = CascadeType.ALL)
    private List<TransportPlanItem> transportPlanItems;

    @OneToMany(mappedBy = DataBaseConstant.TransportPlan.MAPPED_BY, cascade = CascadeType.ALL)
    private List<TransportPlanTrailer> transportPlanTrailers;

    private String operatorId;

    @Column(length = 20, nullable = false)
    private String transportCode;

    @Column(length = 24, nullable = false)
    private String transportName;

    private Long departureFrom;

    private Long arrivalTo;

    private LocalDate collectionDate;

    private Integer trailerNumber;

    private Integer repeatDay;

    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;

    private LocalTime collectionTimeFrom;

    private LocalTime collectionTimeTo;

    @Column(precision = 9, scale = 0)
    private BigDecimal pricePerUnit;

    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    private Long outerPackageCode;

    @Column(precision = 9, scale = 3)
    private BigDecimal totalLength;

    @Column(precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @Column(precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @Column(precision = 9, scale = 3)
    private BigDecimal weight;

    @Column(length = 500)
    private String specialInstructions;

    private Integer originType;

    private Long importId;

    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
