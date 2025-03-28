package jp.co.nlj.ix.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送計画明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.TrspPlanLineItem.TABLE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TrspPlanLineItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 215799124506464337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.TrspPlanLineItem.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_PLAN_MSG_INFO_ID)
    private Long trspPlanMsgInfoId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_INSTRUCTION_ID, length = 20, nullable = false)
    private String trspInstructionId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_INSTRUCTION_DATE_SUBM_DTTM)
    private LocalDate trspInstructionDateSubmDttm;

    @Column(name = DataBaseConstant.TrspPlanLineItem.INV_NUM_ID, length = 20)
    private String invNumId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CMN_INV_NUM_ID, length = 20)
    private String cmnInvNumId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.MIX_LOAD_NUM_ID, length = 20)
    private String mixLoadNumId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_NO, length = 20, nullable = false)
    private String serviceNo;

    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_NAME, length = 24, nullable = false)
    private String serviceName;
    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_STRT_DATE, nullable = false)
    private LocalDate serviceStrtDate; // 便の運行日

    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_STRT_TIME, nullable = false)
    private LocalTime serviceStrtTime; // 便の運行時刻

    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_END_DATE)
    private LocalDate serviceEndDate; // 便の運行終了日

    @Column(name = DataBaseConstant.TrspPlanLineItem.SERVICE_END_TIME)
    private LocalTime serviceEndTime; // 便の運行終了時刻

    @Column(name = DataBaseConstant.TrspPlanLineItem.FREIGHT_RATE, precision = 10, nullable = false)
    private BigDecimal freightRate; // 希望運賃

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_MEANS_TYP_CD, length = 2)
    private String trspMeansTypCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_TYP_CD, length = 2)
    private String trspSrvcTypCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ROAD_CARR_SRVC_TYP_CD, length = 9)
    private String roadCarrSrvcTypCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_ROOT_PRIO_CD, length = 50)
    private String trspRootPrioCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_PRIO_CD, length = 3)
    private String carClsPrioCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CLS_OF_CARG_IN_SRVC_RQRM_CD, length = 1)
    private String clsOfCargInSrvcRqrmCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CLS_OF_PKG_UP_SRVC_RQRM_CD, length = 1)
    private String clsOfPkgUpSrvcRqrmCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.PYR_CLS_SRVC_RQRM_CD, length = 2)
    private String pyrClsSrvcRqrmCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRMS_OF_MIX_LOAD_CND_CD, length = 1)
    private String trmsOfMixLoadCndCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DSED_CLL_FROM_DATE)
    private LocalDate dsedCllFromDate;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DSED_CLL_TO_DATE)
    private LocalDate dsedCllToDate;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DSED_CLL_FROM_TIME)
    private LocalTime dsedCllFromTime;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DSED_CLL_TO_TIME)
    private LocalTime dsedCllToTime;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DSED_CLL_TIME_TRMS_SRVC_RQRM_CD, length = 2)
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.APED_ARR_FROM_DATE)
    private LocalDate apedArrFromDate;

    @Column(name = DataBaseConstant.TrspPlanLineItem.APED_ARR_TO_DATE)
    private LocalDate apedArrToDate;

    @Column(name = DataBaseConstant.TrspPlanLineItem.APED_ARR_FROM_TIME_PRFM_DTTM)
    private LocalTime apedArrFromTimePrfmDttm;

    @Column(name = DataBaseConstant.TrspPlanLineItem.APED_ARR_TO_TIME_PRFM_DTTM)
    private LocalTime apedArrToTimePrfmDttm;

    @Column(name = DataBaseConstant.TrspPlanLineItem.APED_ARR_TIME_TRMS_SRVC_RQRM_CD, length = 2)
    private String apedArrTimeTrmsSrvcRqrmCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRMS_OF_MIX_LOAD_TXT, length = 100)
    private String trmsOfMixLoadTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_NOTE_ONE_TXT, length = 500)
    private String trspSrvcNoteOneTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_NOTE_TWO_TXT, length = 500)
    private String trspSrvcNoteTwoTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_SIZE_CD, length = 1)
    private String carClsOfSizeCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_SHP_CD, length = 1)
    private String carClsOfShpCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_TLG_LFTR_EXST_CD, length = 1)
    private String carClsOfTlgLftrExstCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_WING_BODY_EXST_CD, length = 1)
    private String carClsOfWingBodyExstCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_RFG_EXST_CD, length = 1)
    private String carClsOfRfgExstCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRMS_OF_LWR_TMP_MEAS, precision = 5, scale = 2)
    private BigDecimal trmsOfLwrTmpMeas;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRMS_OF_UPP_TMP_MEAS, precision = 5, scale = 2)
    private BigDecimal trmsOfUppTmpMeas;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_CRN_EXST_CD, length = 1)
    private String carClsOfCrnExstCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CAR_RMK_ABOUT_EQPM_TXT, length = 100)
    private String carRmkAboutEqpmTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.DEL_NOTE_ID, length = 23)
    private String delNoteId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.SHPM_NUM_ID, length = 20)
    private String shpmNumId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.RCED_ORD_NUM_ID, length = 23)
    private String rcedOrdNumId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_PCKS_QUAN, precision = 9, scale = 0, nullable = false)
    private BigDecimal istdTotlPcksQuan;

    @Column(name = DataBaseConstant.TrspPlanLineItem.NUM_UNT_CD, length = 3)
    private String numUntCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_WEIG_MEAS, precision = 14, scale = 3)
    private BigDecimal istdTotlWeigMeas;

    @Column(name = DataBaseConstant.TrspPlanLineItem.WEIG_UNT_CD, length = 3)
    private String weigUntCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_VOL_MEAS, precision = 11, scale = 4)
    private BigDecimal istdTotlVolMeas;

    @Column(name = DataBaseConstant.TrspPlanLineItem.VOL_UNT_CD, length = 3)
    private String volUntCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_UNTL_QUAN, precision = 9, scale = 0)
    private BigDecimal istdTotlUntlQuan;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_HEAD_OFF_ID, length = 13, nullable = false)
    private String cnsgPrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_BRNC_OFF_ID, length = 17, nullable = false)
    private String cnsgPrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_NAME_TXT, length = 320)
    private String cnsgPrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_SCT_SPED_ORG_ID, length = 12)
    private String cnsgSctSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String cnsgSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String cnsgTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String cnsgPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNSG_PSTC_CD, length = 7)
    private String cnsgPstcCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_HEAD_OFF_ID, length = 13, nullable = false)
    private String trspRqrPrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_BRNC_OFF_ID, length = 17, nullable = false)
    private String trspRqrPrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_NAME_TXT, length = 320)
    private String trspRqrPrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_ID, length = 12)
    private String trspRqrSctSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String trspRqrSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String trspRqrSctTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String trspRqrPstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PSTC_CD, length = 7)
    private String trspRqrPstcCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_HEAD_OFF_ID, length = 13)
    private String cneePrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_BRNC_OFF_ID, length = 17)
    private String cneePrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_NAME_TXT, length = 320)
    private String cneePrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_SCT_ID, length = 12)
    private String cneeSctId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_SCT_NAME_TXT, length = 100)
    private String cneeSctNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PRIM_CNT_PERS_NAME_TXT, length = 20)
    private String cneePrimCntPersNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String cneeTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PSTL_ADRS_LINE_ONE_TXT, length = 500)
    private String cneePstlAdrsLineOneTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.CNEE_PSTC_CD, length = 7)
    private String cneePstcCd;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID, length = 13)
    private String logsSrvcPrvPrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID, length = 17)
    private String logsSrvcPrvPrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_NAME_TXT, length = 320)
    private String logsSrvcPrvPrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_ID, length = 12)
    private String logsSrvcPrvSctSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT, length = 20)
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String logsSrvcPrvSctTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID, length = 13)
    private String trspCliPrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_BRNC_OFF_ID, length = 17)
    private String trspCliPrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_NAME_TXT, length = 320)
    private String trspCliPrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_ID, length = 12)
    private String roadCarrDepaSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT, length = 320)
    private String roadCarrDepaSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT, length = 20)
    private String trspCliTelCmmCmpNumTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_ID, length = 12)
    private String roadCarrArrSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_NAME_TXT, length = 320)
    private String roadCarrArrSpedOrgNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_HEAD_OFF_ID, length = 13)
    private String fretClimToPrtyHeadOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_BRNC_OFF_ID, length = 17)
    private String fretClimToPrtyBrncOffId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_NAME_TXT, length = 320)
    private String fretClimToPrtyNameTxt;

    @Column(name = DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_ID, length = 12)
    private String fretClimToSctSpedOrgId;

    @Column(name = DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_NAME_TXT, length = 100)
    private String fretClimToSctSpedOrgNameTxt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trspPlanLineItem")
    private List<CnsLineItem> cnsLineItem = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trspPlanLineItem")
    private List<ShipFromPrty> shipFromPrty = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trspPlanLineItem")
    private List<ShipToPrty> shipToPrty = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}
