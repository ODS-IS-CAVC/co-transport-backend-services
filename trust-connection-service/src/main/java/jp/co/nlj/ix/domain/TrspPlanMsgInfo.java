package jp.co.nlj.ix.domain;

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
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * メッセージ情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.TrspPlanMsgInfo.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrspPlanMsgInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2731021845362534266L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TrspPlanMsgInfo.ID)
    private Long id;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.X_TRACKING)
    private UUID xTracking;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.MSG_ID, precision = 5, scale = 0, nullable = false)
    private BigDecimal msgId;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.MSG_INFO_CLS_TYP_CD, length = 4, nullable = false)
    private String msgInfoClsTypCd;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.MSG_DATE_ISS_DTTM)
    private LocalDate msgDateIssDttm;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.MSG_TIME_ISS_DTTM)
    private LocalTime msgTimeIssDttm;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.MSG_FN_STAS_CD, length = 1, nullable = false)
    private String msgFnStasCd;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.NOTE_DCPT_TXT, length = 500)
    private String noteDcptTxt;

    @Column(name = DataBaseConstant.TrspPlanMsgInfo.TRSP_PLAN_STAS_CD, length = 2)
    private String trspPlanStasCd;

    @Override
    public Long getId() {
        return this.id;
    }
}
