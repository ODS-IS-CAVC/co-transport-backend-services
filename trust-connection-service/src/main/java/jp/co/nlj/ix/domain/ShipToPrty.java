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
 * 荷届先エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ShipToPrty.TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShipToPrty extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1407541010953245322L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ShipToPrty.ID)
    private Long id;

    @Column(name = DataBaseConstant.ShipToPrty.OPERATOR_ID, nullable = false)
    private String operatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.ShipToPrty.TRSP_PLAN_LINE_ITEM_ID)
    private TrspPlanLineItem trspPlanLineItem;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PRTY_HEAD_OFF_ID, length = 13)
    private String shipToPrtyHeadOffId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PRTY_BRNC_OFF_ID, length = 17)
    private String shipToPrtyBrncOffId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PRTY_NAME_TXT, length = 320)
    private String shipToPrtyNameTxt;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_SCT_ID, length = 12)
    private String shipToSctId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_SCT_NAME_TXT, length = 100)
    private String shipToSctNameTxt;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PRIM_CNT_ID, length = 12)
    private String shipToPrimCntId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PRIM_CNT_PERS_NAME_TXT, length = 20)
    private String shipToPrimCntPersNameTxt;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String shipToTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PSTL_ADRS_CTY_ID, length = 5)
    private String shipToPstlAdrsCtyId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PSTL_ADRS_ID, length = 20)
    private String shipToPstlAdrsId;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String shipToPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.ShipToPrty.SHIP_TO_PSTC_CD, length = 7)
    private String shipToPstcCd;

    @Column(name = DataBaseConstant.ShipToPrty.PLC_CD_PRTY_ID, length = 4)
    private String plcCdPrtyId;

    @Column(name = DataBaseConstant.ShipToPrty.GLN_PRTY_ID, length = 13)
    private String glnPrtyId;

    @Column(name = DataBaseConstant.ShipToPrty.JPN_UPLC_CD, length = 16)
    private String jpnUplcCd;

    @Column(name = DataBaseConstant.ShipToPrty.JPN_VAN_SRVC_CD, length = 2)
    private String jpnVanSrvcCd;

    @Column(name = DataBaseConstant.ShipToPrty.JPN_VAN_VANS_CD, length = 12)
    private String jpnVanVansCd;

    @Override
    public Long getId() {
        return this.id;
    }

    public Long getTrspPlanLineItemId() {
        return this.trspPlanLineItem.getId();
    }
}
