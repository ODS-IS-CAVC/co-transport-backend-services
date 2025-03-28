package nlj.business.carrier.link.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 要求運送計画明細エンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqTrspPlanLineItem.TABLE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReqTrspPlanLineItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2755142423779479927L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_PLAN_MSG_INFO_ID)
    private Long trspPlanMsgInfoId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reqTrspPlanLineItem")
    private ReqShipFromPrty reqShipFromPrty;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reqTrspPlanLineItem")
    private ReqShipToPrty reqShipToPrty;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_INSTRUCTION_ID, length = 20, nullable = false)
    private String trspInstructionId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_INSTRUCTION_DATE_SUBM_DTTM)
    private LocalDate trspInstructionDateSubmDttm;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.INV_NUM_ID, length = 20)
    private String invNumId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CMN_INV_NUM_ID, length = 20)
    private String cmnInvNumId;
    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FREIGHT_RATE, precision = 10)
    private BigDecimal freightRate; // 希望運賃

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_MEANS_TYP_CD, length = 2)
    private String trspMeansTypCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_SRVC_TYP_CD, length = 2)
    private String trspSrvcTypCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ROAD_CARR_SRVC_TYP_CD, length = 9)
    private String roadCarrSrvcTypCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_ROOT_PRIO_CD, length = 50)
    private String trspRootPrioCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_PRIO_CD, length = 3)
    private String carClsPrioCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CLS_OF_CARG_IN_SRVC_RQRM_CD, length = 1)
    private String clsOfCargInSrvcRqrmCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CLS_OF_PKG_UP_SRVC_RQRM_CD, length = 1)
    private String clsOfPkgUpSrvcRqrmCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.PYR_CLS_SRVC_RQRM_CD, length = 2)
    private String pyrClsSrvcRqrmCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRMS_OF_MIX_LOAD_CND_CD, length = 1)
    private String trmsOfMixLoadCndCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DSED_CLL_FROM_DATE)
    private LocalDate dsedCllFromDate;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DSED_CLL_TO_DATE)
    private LocalDate dsedCllToDate;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DSED_CLL_FROM_TIME)
    private LocalTime dsedCllFromTime;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DSED_CLL_TO_TIME)
    private LocalTime dsedCllToTime;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DSED_CLL_TIME_TRMS_SRVC_RQRM_CD, length = 2)
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.APED_ARR_FROM_DATE)
    private LocalDate apedArrFromDate;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.APED_ARR_TO_DATE)
    private LocalDate apedArrToDate;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.APED_ARR_FROM_TIME_PRFM_DTTM)
    private LocalTime apedArrFromTimePrfmDttm;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.APED_ARR_TO_TIME_PRFM_DTTM)
    private LocalTime apedArrToTimePrfmDttm;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.APED_ARR_TIME_TRMS_SRVC_RQRM_CD, length = 2)
    private String apedArrTimeTrmsSrvcRqrmCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRMS_OF_MIX_LOAD_TXT, length = 100)
    private String trmsOfMixLoadTxt;
    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.BRT_RSV_NEC_CD, length = 1)
    private String brtRsvNecCd;
    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.BRT_RSV_STAS_CD, length = 1)
    private String brtRsvStasCd;
    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.BRT_RSV_NUM_ID, length = 20)
    private String brtRsvNumId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_SRVC_NOTE_ONE_TXT, length = 500)
    private String trspSrvcNoteOneTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_SRVC_NOTE_TWO_TXT, length = 500)
    private String trspSrvcNoteTwoTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_SIZE_CD, length = 1)
    private String carClsOfSizeCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_SHP_CD, length = 1)
    private String carClsOfShpCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_TLG_LFTR_EXST_CD, length = 1)
    private String carClsOfTlgLftrExstCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_WING_BODY_EXST_CD, length = 1)
    private String carClsOfWingBodyExstCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_RFG_EXST_CD, length = 1)
    private String carClsOfRfgExstCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRMS_OF_LWR_TMP_MEAS, precision = 5, scale = 2)
    private BigDecimal trmsOfLwrTmpMeas;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRMS_OF_UPP_TMP_MEAS, precision = 5, scale = 2)
    private BigDecimal trmsOfUppTmpMeas;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_CLS_OF_CRN_EXST_CD, length = 1)
    private String carClsOfCrnExstCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CAR_RMK_ABOUT_EQPM_TXT, length = 100)
    private String carRmkAboutEqpmTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DEL_NOTE_ID, length = 23)
    private String delNoteId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.SHPM_NUM_ID, length = 20)
    private String shpmNumId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.DEL_CTRL_NUM_ID, length = 20)
    private String delCtrlNumId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ISTD_TOTL_PCKS_QUAN, precision = 9, scale = 0, nullable = false)
    private BigDecimal istdTotlPcksQuan;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.NUM_UNT_CD, length = 3)
    private String numUntCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ISTD_TOTL_WEIG_MEAS, precision = 14, scale = 3)
    private BigDecimal istdTotlWeigMeas;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.WEIG_UNT_CD, length = 3)
    private String weigUntCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ISTD_TOTL_VOL_MEAS, precision = 11, scale = 4)
    private BigDecimal istdTotlVolMeas;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.VOL_UNT_CD, length = 3)
    private String volUntCd;


    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_PRTY_HEAD_OFF_ID, length = 13, nullable = false)
    private String cnsgPrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_PRTY_BRNC_OFF_ID, length = 17, nullable = false)
    private String cnsgPrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_PRTY_NAME_TXT, length = 320)
    private String cnsgPrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_SCT_SPED_ORG_ID, length = 12)
    private String cnsgSctSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String cnsgSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String cnsgTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String cnsgPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNSG_PSTC_CD, length = 7)
    private String cnsgPstcCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_PRTY_HEAD_OFF_ID, length = 13)
    private String trspRqrPrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_PRTY_BRNC_OFF_ID, length = 17)
    private String trspRqrPrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_PRTY_NAME_TXT, length = 320)
    private String trspRqrPrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_ID, length = 12)
    private String trspRqrSctSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String trspRqrSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_SCT_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String trspRqrSctTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String trspRqrPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_RQR_PSTC_CD, length = 7)
    private String trspRqrPstcCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PRTY_HEAD_OFF_ID, length = 13)
    private String cneePrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PRTY_BRNC_OFF_ID, length = 17)
    private String cneePrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PRTY_NAME_TXT, length = 320)
    private String cneePrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_SCT_ID, length = 12)
    private String cneeSctId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_SCT_NAME_TXT, length = 100)
    private String cneeSctNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PRIM_CNT_PERS_NAME_TXT, length = 20)
    private String cneePrimCntPersNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String cneeTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String cneePstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.CNEE_PSTC_CD, length = 7)
    private String cneePstcCd;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID, length = 13)
    private String logsSrvcPrvPrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID, length = 17)
    private String logsSrvcPrvPrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_PRTY_NAME_TXT, length = 320)
    private String logsSrvcPrvPrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_ID, length = 12)
    private String logsSrvcPrvSctSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT, length = 20)
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String logsSrvcPrvSctTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID, length = 13)
    private String trspCliPrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_CLI_PRTY_BRNC_OFF_ID, length = 17)
    private String trspCliPrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_CLI_PRTY_NAME_TXT, length = 320)
    private String trspCliPrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_ID, length = 12)
    private String roadCarrDepaSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT, length = 320)
    private String roadCarrDepaSpedOrgNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String trspCliTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_ID, length = 12)
    private String roadCarrArrSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_NAME_TXT, length = 320)
    private String roadCarrArrSpedOrgNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FRET_CLIM_TO_PRTY_HEAD_OFF_ID, length = 13)
    private String fretClimToPrtyHeadOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FRET_CLIM_TO_PRTY_BRNC_OFF_ID, length = 17)
    private String fretClimToPrtyBrncOffId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FRET_CLIM_TO_PRTY_NAME_TXT, length = 320)
    private String fretClimToPrtyNameTxt;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_ID, length = 12)
    private String fretClimToSctSpedOrgId;

    @Column(name = DataBaseConstant.ReqTrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String fretClimToSctSpedOrgNameTxt;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reqTrspPlanLineItem")
    private List<ReqCnsLineItem> cnsLineItem = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}