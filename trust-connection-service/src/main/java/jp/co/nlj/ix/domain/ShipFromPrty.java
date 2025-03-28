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
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷場所エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ShipFromPrty.TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShipFromPrty extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -950704507727036115L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ShipFromPrty.ID)
    private Long id;

    @Column(name = DataBaseConstant.ShipFromPrty.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.ShipFromPrty.TRSP_PLAN_LINE_ITEM_ID)
    private TrspPlanLineItem trspPlanLineItem;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PRTY_HEAD_OFF_ID, length = 13)
    private String shipFromPrtyHeadOffId;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PRTY_BRNC_OFF_ID, length = 17)
    private String shipFromPrtyBrncOffId;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PRTY_NAME_TXT, length = 320)
    private String shipFromPrtyNameTxt;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_SCT_ID, length = 12)
    private String shipFromSctId;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_SCT_NAME_TXT, length = 100)
    private String shipFromSctNameTxt;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String shipFromTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PSTL_ADRS_CTY_ID, length = 5)
    private String shipFromPstlAdrsCtyId;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PSTL_ADRS_ID, length = 20)
    private String shipFromPstlAdrsId;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String shipFromPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.ShipFromPrty.SHIP_FROM_PSTC_CD, length = 7)
    private String shipFromPstcCd;

    @Column(name = DataBaseConstant.ShipFromPrty.PLC_CD_PRTY_ID, length = 4)
    private String plcCdPrtyId;

    @Column(name = DataBaseConstant.ShipFromPrty.GLN_PRTY_ID, length = 13)
    private String glnPrtyId;

    @Column(name = DataBaseConstant.ShipFromPrty.JPN_UPLC_CD, length = 16)
    private String jpnUplcCd;

    @Column(name = DataBaseConstant.ShipFromPrty.JPN_VAN_SRVC_CD, length = 2)
    private String jpnVanSrvcCd;

    @Column(name = DataBaseConstant.ShipFromPrty.JPN_VAN_VANS_CD, length = 12)
    private String jpnVanVansCd;

    @Override
    public Long getId() {
        return this.id;
    }

    public Long getTrspPlanLineItemId() {
        return this.trspPlanLineItem.getId();
    }
}
