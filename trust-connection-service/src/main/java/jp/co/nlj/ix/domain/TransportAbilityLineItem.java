package jp.co.nlj.ix.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 輸送能力明細項目エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransportAbilityLineItem.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityLineItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.TransportAbilityLineItem.TRSP_ABILITY_MSG_INFO_ID, nullable = true)
    @JsonBackReference
    private TransportAbilityMessageInfo transportAbilityMessageInfo;

    @OneToMany(mappedBy = DataBaseConstant.TransportAbilityLineItem.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CarInfo> carInfos;

    @OneToMany(mappedBy = DataBaseConstant.TransportAbilityLineItem.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DriverInformation> driverInformations;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID, nullable = false, length = 13)
    private String trspCliPrtyHeadOffId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_BRNC_OFF_ID, nullable = false, length = 17)
    private String trspCliPrtyBrncOffId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_NAME_TXT, nullable = true, length = 320)
    private String trspCliPrtyNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_DEPA_SPEO_ORG_ID, nullable = true, length = 12)
    private String roadCarrDepaSpedOrgId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_DEPA_SPEO_ORG_NAME_TXT, nullable = true, length = 320)
    private String roadCarrDepaSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT, nullable = true, length = 20)
    private String trspCliTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_ARR_SPEO_ORG_ID, nullable = true, length = 12)
    private String roadCarrArrSpedOrgId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_ARR_SPEO_ORG_NAME_TXT, nullable = true, length = 320)
    private String roadCarrArrSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID, nullable = true, length = 13)
    private String logsSrvcPrvPrtyHeadOffId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID, nullable = true, length = 17)
    private String logsSrvcPrvPrtyBrncOffId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_PRTY_NAME_TXT, nullable = true, length = 320)
    private String logsSrvcPrvPrtyNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_SCT_SPEO_ORG_ID, nullable = true, length = 12)
    private String logsSrvcPrvSctSpedOrgId;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_SCT_SPEO_ORG_NAME_TXT, nullable = true, length = 100)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT, nullable = true, length = 20)
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @Column(name = DataBaseConstant.TransportAbilityLineItem.LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT, nullable = true, length = 20)
    private String logsSrvcPrvSctTelCmmCmpNumTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
