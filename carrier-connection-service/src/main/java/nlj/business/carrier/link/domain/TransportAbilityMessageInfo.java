package nlj.business.carrier.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * トランスポート能力メッセージ情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportAbilityMessageInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityMessageInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @OneToMany(mappedBy = DataBaseConstant.TransportAbilityMessageInfo.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    Set<TransportAbilityLineItem> transportAbilityLineItems;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.OPERATOR_ID, nullable = false)
    private String operatorId;
    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.XTRACKING)
    private UUID xTracking;
    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.MSG_ID, precision = 5)
    private BigDecimal msgId;

    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.MSG_INFO_CLS_TYP_CD, length = 4)
    private String msgInfoClsTypCd;

    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.MSG_DATE_ISS_DTTM)
    private LocalDate msgDateIssDttm;

    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.MSG_TIME_ISS_DTTM)
    private LocalTime msgTimeIssDttm;

    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.MSG_FN_STAS_CD, length = 1)
    private String msgFnStasCd;

    @Column(name = DataBaseConstant.TransportAbilityMessageInfo.NOTE_DCPT_TXT, length = 500)
    private String noteDcptTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}