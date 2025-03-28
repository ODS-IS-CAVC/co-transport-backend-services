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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 要求貨物明細エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqCnsLineItem.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReqCnsLineItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -52742153703345414L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ReqCnsLineItem.ID)
    private Long id;
    @Column(name = DataBaseConstant.ReqCnsLineItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.ReqCnsLineItem.REQ_TRSP_PLAN_LINE_ITEM_ID)
    private ReqTrspPlanLineItem reqTrspPlanLineItem;

    @Column(name = DataBaseConstant.ReqCnsLineItem.LINE_ITEM_NUM_ID, length = 10)
    private String lineItemNumId;

    @Column(name = DataBaseConstant.ReqCnsLineItem.SEV_ORD_NUM_ID, length = 23)
    private String sevOrdNumId;

    @Column(name = DataBaseConstant.ReqCnsLineItem.CNSG_CRG_ITEM_NUM_ID, length = 15)
    private String cnsgCrgItemNumId;

    @Column(name = DataBaseConstant.ReqCnsLineItem.BUY_ASSI_ITEM_CD, length = 25)
    private String buyAssiItemCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.SELL_ASSI_ITEM_CD, length = 55)
    private String sellAssiItemCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.WRHS_ASSI_ITEM_CD, length = 25)
    private String wrhsAssiItemCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.ITEM_NAME_TXT, length = 200)
    private String itemNameTxt;

    @Column(name = DataBaseConstant.ReqCnsLineItem.GODS_IDCS_IN_OTS_PCKE_NAME_TXT, length = 50)
    private String godsIdcsInOtsPckeNameTxt;
    @Column(name = DataBaseConstant.ReqCnsLineItem.CLS_OF_KPNG_TMP_CD, length = 2)
    private String clsOfKpngTmpCd;
    @Column(name = DataBaseConstant.ReqCnsLineItem.SELL_CTRL_NUM_ID, length = 23)
    private String sellCtrlNumId;
    @Column(name = DataBaseConstant.ReqCnsLineItem.NUM_OF_ISTD_QUAN, precision = 9, scale = 0)
    private BigDecimal numOfIstdQuan;

    @Column(name = DataBaseConstant.ReqCnsLineItem.SEV_NUM_UNT_CD, length = 3)
    private String sevNumUntCd;
    @Column(name = DataBaseConstant.ReqCnsLineItem.ISTD_PCKE_WEIG_MEAS, precision = 12, scale = 3)
    private BigDecimal istdPckeWeigMeas;

    @Column(name = DataBaseConstant.ReqCnsLineItem.SEV_WEIG_UNT_CD, length = 3)
    private String sevWeigUntCd;
    @Column(name = DataBaseConstant.ReqCnsLineItem.ISTD_PCKE_VOL_MEAS, precision = 11, scale = 4)
    private BigDecimal istdPckeVolMeas;

    @Column(name = DataBaseConstant.ReqCnsLineItem.SEV_VOL_UNT_CD, length = 3)
    private String sevVolUntCd;
    @Column(name = DataBaseConstant.ReqCnsLineItem.ISTD_QUAN_MEAS, precision = 15, scale = 4)
    private BigDecimal istdQuanMeas;

    @Column(name = DataBaseConstant.ReqCnsLineItem.CNTE_NUM_UNT_CD, length = 3)
    private String cnteNumUntCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.DCPV_TRPN_PCKG_TXT, length = 20)
    private String dcpvTrpnPckgTxt;

    @Column(name = DataBaseConstant.ReqCnsLineItem.PCKE_FRM_CD, length = 3)
    private String pckeFrmCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.PCKE_FRM_NAME_CD, length = 20)
    private String pckeFrmNameCd;

    @Column(name = DataBaseConstant.ReqCnsLineItem.CRG_HND_TRMS_SPCL_ISRS_TXT, length = 30)
    private String crgHndTrmsSpclIsrsTxt;

    @Column(name = DataBaseConstant.ReqCnsLineItem.GLB_RETB_ASSE_ID, length = 14)
    private String glbRetbAsseId;
    @Column(name = DataBaseConstant.ReqCnsLineItem.TOTL_RTI_QUAN_QUAN, precision = 5, scale = 0)
    private BigDecimal totlRtiQuanQuan;

    @Override
    public Long getId() {
        return this.id;
    }

    public Long getreqTrspPlanLineItemId() {
        return this.reqTrspPlanLineItem.getId();
    }
}
