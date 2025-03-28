package jp.co.nlj.ix.domain;

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
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <PRE>
 * 貨物明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.CnsLineItem.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CnsLineItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -52742153703345414L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.CnsLineItem.ID)
    private Long id;
    @Column(name = DataBaseConstant.CnsLineItem.OPERATOR_ID, nullable = false)
    private String operatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.CnsLineItem.TRSP_PLAN_LINE_ITEM_ID)
    private TrspPlanLineItem trspPlanLineItem;

    @Column(name = DataBaseConstant.CnsLineItem.LINE_ITEM_NUM_ID, length = 10)
    private String lineItemNumId;

    @Column(name = DataBaseConstant.CnsLineItem.SEV_ORD_NUM_ID, length = 23)
    private String sevOrdNumId;

    @Column(name = DataBaseConstant.CnsLineItem.CNSG_CRG_ITEM_NUM_ID, length = 15)
    private String cnsgCrgItemNumId;

    @Column(name = DataBaseConstant.CnsLineItem.BUY_ASSI_ITEM_CD, length = 25)
    private String buyAssiItemCd;

    @Column(name = DataBaseConstant.CnsLineItem.SELL_ASSI_ITEM_CD, length = 55)
    private String sellAssiItemCd;

    @Column(name = DataBaseConstant.CnsLineItem.WRHS_ASSI_ITEM_CD, length = 25)
    private String wrhsAssiItemCd;

    @Column(name = DataBaseConstant.CnsLineItem.ITEM_NAME_TXT, length = 200)
    private String itemNameTxt;

    @Column(name = DataBaseConstant.CnsLineItem.GODS_IDCS_IN_OTS_PCKE_NAME_TXT, length = 50)
    private String godsIdcsInOtsPckeNameTxt;
    @Column(name = DataBaseConstant.CnsLineItem.NUM_OF_ISTD_UNTL_QUAN, precision = 9, scale = 0)
    private BigDecimal numOfIstdUntlQuan;
    @Column(name = DataBaseConstant.CnsLineItem.NUM_OF_ISTD_QUAN, precision = 9, scale = 0)
    private BigDecimal numOfIstdQuan;

    @Column(name = DataBaseConstant.CnsLineItem.SEV_NUM_UNT_CD, length = 3)
    private String sevNumUntCd;
    @Column(name = DataBaseConstant.CnsLineItem.ISTD_PCKE_WEIG_MEAS, precision = 12, scale = 3)
    private BigDecimal istdPckeWeigMeas;

    @Column(name = DataBaseConstant.CnsLineItem.SEV_WEIG_UNT_CD, length = 3)
    private String sevWeigUntCd;
    @Column(name = DataBaseConstant.CnsLineItem.ISTD_PCKE_VOL_MEAS, precision = 11, scale = 4)
    private BigDecimal istdPckeVolMeas;

    @Column(name = DataBaseConstant.CnsLineItem.SEV_VOL_UNT_CD, length = 3)
    private String sevVolUntCd;
    @Column(name = DataBaseConstant.CnsLineItem.ISTD_QUAN_MEAS, precision = 15, scale = 4)
    private BigDecimal istdQuanMeas;

    @Column(name = DataBaseConstant.CnsLineItem.CNTE_NUM_UNT_CD, length = 3)
    private String cnteNumUntCd;

    @Column(name = DataBaseConstant.CnsLineItem.DCPV_TRPN_PCKG_TXT, length = 20)
    private String dcpvTrpnPckgTxt;

    @Column(name = DataBaseConstant.CnsLineItem.PCKE_FRM_CD, length = 3)
    private String pckeFrmCd;

    @Column(name = DataBaseConstant.CnsLineItem.PCKE_FRM_NAME_CD, length = 20)
    private String pckeFrmNameCd;

    @Column(name = DataBaseConstant.CnsLineItem.CRG_HND_TRMS_SPCL_ISRS_TXT, length = 30)
    private String crgHndTrmsSpclIsrsTxt;

    @Column(name = DataBaseConstant.CnsLineItem.GLB_RETB_ASSE_ID, length = 14)
    private String glbRetbAsseId;
    @Column(name = DataBaseConstant.CnsLineItem.TOTL_RTI_QUAN_QUAN, precision = 5, scale = 0)
    private BigDecimal totlRtiQuanQuan;
    @Column(name = DataBaseConstant.CnsLineItem.CHRG_OF_PCKE_CTRL_NUM_UNT_AMNT, precision = 8, scale = 0)
    private BigDecimal chrgOfPckeCtrlNumUntAmnt;

    @Override
    public Long getId() {
        return this.id;
    }

    public Long getTrspPlanLineItemId() {
        return this.trspPlanLineItem.getId();
    }
}
