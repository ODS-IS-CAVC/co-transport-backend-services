package jp.co.nlj.ix.domain.shipper;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <PRE>
 * 輸送計画項目エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlanItem.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlanItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.TransportPlanItem.TRANSPORT_PLAN_ID, nullable = false)
    private TransportPlan transportPlan;

    @OneToMany(mappedBy = DataBaseConstant.TransportPlanItem.MAPPED_BY, cascade = CascadeType.ALL)
    private List<TransportPlanTrailer> transportPlanTrailers;

    private String operatorId;

    @Column(length = 20)
    private String transportCode;

    @Column(length = 24)
    private String transportName;

    private LocalDate collectionDate;

    private LocalTime collectionTimeFrom;

    private LocalTime collectionTimeTo;

    private Long departureFrom;

    private Long arrivalTo;

    private Integer trailerNumber;

    private Integer trailerNumberRest;

    @Column(precision = 9)
    private BigDecimal pricePerUnit;
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    private Integer outerPackageCode;

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

    private Integer status;

    @Override
    public Long getId() {
        return this.id;
    }
}
