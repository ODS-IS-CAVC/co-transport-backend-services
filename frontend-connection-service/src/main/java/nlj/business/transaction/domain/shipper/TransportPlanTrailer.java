package nlj.business.transaction.domain.shipper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 輸送計画トレーラーエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportPlanTrailer.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlanTrailer extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.TransportPlanTrailer.TRANSPORT_PLAN_ID, nullable = false)
    private TransportPlan transportPlan;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.TransportPlanTrailer.TRANSPORT_PLAN_ITEM_ID)
    private TransportPlanItem transportPlanItem;

    private Integer trailerOrderNumber;

    private LocalDate day;

    private LocalTime collectionTimeFrom;

    private LocalTime collectionTimeTo;

    @Override
    public Long getId() {
        return this.id;
    }
}
