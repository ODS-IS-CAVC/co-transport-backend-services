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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 要求メッセージ情報エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqTrspPlanMsgInfo.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReqTrspPlanMsgInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2150569900019161380L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.ID)
    private Long id;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.X_TRACKING)
    private UUID xTracking;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.MSG_ID, precision = 5, scale = 0)
    private BigDecimal msgId;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.MSG_INFO_CLS_TYP_CD, length = 4)
    private String msgInfoClsTypCd;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.MSG_DATE_ISS_DTTM)
    private LocalDate msgDateIssDttm;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.MSG_TIME_ISS_DTTM)
    private LocalTime msgTimeIssDttm;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.MSG_FN_STAS_CD, length = 1)
    private String msgFnStasCd;

    @Column(name = DataBaseConstant.ReqTrspPlanMsgInfo.NOTE_DCPT_TXT, length = 500)
    private String noteDcptTxt;


    @Override
    public Long getId() {
        return this.id;
    }
}
