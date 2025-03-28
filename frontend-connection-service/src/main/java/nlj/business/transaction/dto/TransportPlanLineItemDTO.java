package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransportPlanLineItemDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.OPERATOR_ID)
    private String operatorId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_PLAN_MSG_INFO_ID)
    private Long trspPlanMsgInfoId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_INSTRUCTION_ID)
    private String trspInstructionId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_INSTRUCTION_DATE_SUBM_DTTM)
    private Date trspInstructionDateSubmDttm;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.INV_NUM_ID)
    private String invNumId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CMN_INV_NUM_ID)
    private String cmnInvNumId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.MIX_LOAD_NUM_ID)
    private String mixLoadNumId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_NO)
    private String serviceNo;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_NAME)
    private String serviceName;
    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_STRT_DATE)
    private Date serviceStrtDate; // 便の運行日

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_STRT_TIME)
    private Time serviceStrtTime; // 便の運行時刻

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_END_DATE)
    private Date serviceEndDate; // 便の運行終了日

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SERVICE_END_TIME)
    private Time serviceEndTime; // 便の運行終了時刻

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FREIGHT_RATE)
    private BigDecimal freightRate; // 希望運賃

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_MEANS_TYP_CD)
    private String trspMeansTypCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_TYP_CD)
    private String trspSrvcTypCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ROAD_CARR_SRVC_TYP_CD)
    private String roadCarrSrvcTypCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_ROOT_PRIO_CD)
    private String trspRootPrioCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_PRIO_CD)
    private String carClsPrioCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CLS_OF_CARG_IN_SRVC_RQRM_CD)
    private String clsOfCargInSrvcRqrmCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CLS_OF_PKG_UP_SRVC_RQRM_CD)
    private String clsOfPkgUpSrvcRqrmCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.PYR_CLS_SRVC_RQRM_CD)
    private String pyrClsSrvcRqrmCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRMS_OF_MIX_LOAD_CND_CD)
    private String trmsOfMixLoadCndCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DSED_CLL_FROM_DATE)
    private Date dsedCllFromDate;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DSED_CLL_TO_DATE)
    private Date dsedCllToDate;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DSED_CLL_FROM_TIME)
    private Time dsedCllFromTime;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DSED_CLL_TO_TIME)
    private Time dsedCllToTime;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DSED_CLL_TIME_TRMS_SRVC_RQRM_CD)
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.APED_ARR_FROM_DATE)
    private Date apedArrFromDate;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.APED_ARR_TO_DATE)
    private Date apedArrToDate;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.APED_ARR_FROM_TIME_PRFM_DTTM)
    private Time apedArrFromTimePrfmDttm;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.APED_ARR_TO_TIME_PRFM_DTTM)
    private Time apedArrToTimePrfmDttm;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.APED_ARR_TIME_TRMS_SRVC_RQRM_CD)
    private String apedArrTimeTrmsSrvcRqrmCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRMS_OF_MIX_LOAD_TXT)
    private String trmsOfMixLoadTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_NOTE_ONE_TXT)
    private String trspSrvcNoteOneTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_SRVC_NOTE_TWO_TXT)
    private String trspSrvcNoteTwoTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_SIZE_CD)
    private String carClsOfSizeCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_SHP_CD)
    private String carClsOfShpCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_TLG_LFTR_EXST_CD)
    private String carClsOfTlgLftrExstCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_WING_BODY_EXST_CD)
    private String carClsOfWingBodyExstCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_RFG_EXST_CD)
    private String carClsOfRfgExstCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRMS_OF_LWR_TMP_MEAS)
    private BigDecimal trmsOfLwrTmpMeas;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRMS_OF_UPP_TMP_MEAS)
    private BigDecimal trmsOfUppTmpMeas;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_CLS_OF_CRN_EXST_CD)
    private String carClsOfCrnExstCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CAR_RMK_ABOUT_EQPM_TXT)
    private String carRmkAboutEqpmTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.DEL_NOTE_ID)
    private String delNoteId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.SHPM_NUM_ID)
    private String shpmNumId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.RCED_ORD_NUM_ID)
    private String rcedOrdNumId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_PCKS_QUAN)
    private BigDecimal istdTotlPcksQuan;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.NUM_UNT_CD)
    private String numUntCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_WEIG_MEAS)
    private BigDecimal istdTotlWeigMeas;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.WEIG_UNT_CD)
    private String weigUntCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_VOL_MEAS)
    private BigDecimal istdTotlVolMeas;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.VOL_UNT_CD)
    private String volUntCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ISTD_TOTL_UNTL_QUAN)
    private BigDecimal istdTotlUntlQuan;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_HEAD_OFF_ID)
    private String cnsgPrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_BRNC_OFF_ID)
    private String cnsgPrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_NAME_TXT)
    private String cnsgPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_SCT_SPED_ORG_ID)
    private String cnsgSctSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_SCT_SPED_ORG_NAME_TXT)
    private String cnsgSctSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_TEL_CMM_CMP_NUM_TXT)
    private String cnsgTelCmmCmpNumTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PSTL_ADRS_LINE_ONE_TXT)
    private String cnsgPstlAdrsLineOneTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PSTC_CD)
    private String cnsgPstcCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_HEAD_OFF_ID)
    private String trspRqrPrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_BRNC_OFF_ID)
    private String trspRqrPrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PRTY_NAME_TXT)
    private String trspRqrPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_ID)
    private String trspRqrSctSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_SPED_ORG_NAME_TXT)
    private String trspRqrSctSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_SCT_TEL_CMM_CMP_NUM_TXT)
    private String trspRqrSctTelCmmCmpNumTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PSTL_ADRS_LINE_ONE_TXT)
    private String trspRqrPstlAdrsLineOneTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_RQR_PSTC_CD)
    private String trspRqrPstcCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_HEAD_OFF_ID)
    private String cneePrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_BRNC_OFF_ID)
    private String cneePrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PRTY_NAME_TXT)
    private String cneePrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_SCT_ID)
    private String cneeSctId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_SCT_NAME_TXT)
    private String cneeSctNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PRIM_CNT_PERS_NAME_TXT)
    private String cneePrimCntPersNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_TEL_CMM_CMP_NUM_TXT)
    private String cneeTelCmmCmpNumTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PSTL_ADRS_LINE_ONE_TXT)
    private String cneePstlAdrsLineOneTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNEE_PSTC_CD)
    private String cneePstcCd;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID)
    private String logsSrvcPrvPrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID)
    private String logsSrvcPrvPrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_PRTY_NAME_TXT)
    private String logsSrvcPrvPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_ID)
    private String logsSrvcPrvSctSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_SPED_ORG_NAME_TXT)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT)
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT)
    private String logsSrvcPrvSctTelCmmCmpNumTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID)
    private String trspCliPrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_BRNC_OFF_ID)
    private String trspCliPrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_CLI_PRTY_NAME_TXT)
    private String trspCliPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_ID)
    private String roadCarrDepaSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT)
    private String roadCarrDepaSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT)
    private String trspCliTelCmmCmpNumTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_ID)
    private String roadCarrArrSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.ROAD_CARR_ARR_SPED_ORG_NAME_TXT)
    private String roadCarrArrSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_HEAD_OFF_ID)
    private String fretClimToPrtyHeadOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_BRNC_OFF_ID)
    private String fretClimToPrtyBrncOffId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_PRTY_NAME_TXT)
    private String fretClimToPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_ID)
    private String fretClimToSctSpedOrgId;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.FRET_CLIM_TO_SCT_SPED_ORG_NAME_TXT)
    private String fretClimToSctSpedOrgNameTxt;

    @JsonProperty("is_matched")
    private Boolean isMatched;

}
