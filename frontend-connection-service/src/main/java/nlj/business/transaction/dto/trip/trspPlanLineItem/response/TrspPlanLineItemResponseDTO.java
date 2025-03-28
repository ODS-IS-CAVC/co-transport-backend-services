package nlj.business.transaction.dto.trip.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.Data;

/**
 * <PRE>
 * 運送業者プラン行項目レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemResponseDTO {

    @JsonProperty("trsp_instruction_date_subm_dttm")
    private Date trspInstructionDateSubmDttm;

    @JsonProperty("inv_num_id")
    private String invNumId;

    @JsonProperty("cmn_inv_num_id")
    private String cmnInvNumId;

    @JsonProperty("trsp_means_typ_cd")
    private String trspMeansTypCd;

    @JsonProperty("trsp_srvc_typ_cd")
    private String trspSrvcTypCd;

    @JsonProperty("road_carr_srvc_typ_cd")
    private String roadCarrSrvcTypCd;

    @JsonProperty("trsp_root_prio_cd")
    private String trspRootPrioCd;

    @JsonProperty("car_cls_prio_cd")
    private String carClsPrioCd;

    @JsonProperty("cls_of_carg_in_srvc_rqrm_cd")
    private String clsOfCargInSrvcRqrmCd;

    @JsonProperty("cls_of_pkg_up_srvc_rqrm_cd")
    private String clsOfPkgUpSrvcRqrmCd;

    @JsonProperty("pyr_cls_srvc_rqrm_cd")
    private String pyrClsSrvcRqrmCd;

    @JsonProperty("trms_of_mix_load_cnd_cd")
    private String trmsOfMixLoadCndCd;

    @JsonProperty("dsed_cll_from_date")
    private Date dsedCllFromDate;

    @JsonProperty("dsed_cll_to_date")
    private Date dsedCllToDate;

    @JsonProperty("dsed_cll_from_time")
    private Time dsedCllFromTime;

    @JsonProperty("dsed_cll_to_time")
    private Time dsedCllToTime;

    @JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @JsonProperty("aped_arr_from_date")
    private Date apedArrFromDate;

    @JsonProperty("aped_arr_to_date")
    private Date apedArrToDate;

    @JsonProperty("aped_arr_from_time_prfm_dttm")
    private Time apedArrFromTimePrfmDttm;

    @JsonProperty("aped_arr_time_trms_srvc_rqrm_cd")
    private String apedArrTimeTrmsSrvcRqrmCd;

    @JsonProperty("aped_arr_to_time_prfm_dttm")
    private Time apedArrToTimePrfmDttm;

    @JsonProperty("trms_of_mix_load_txt")
    private String trmsOfMixLoadTxt;

    @JsonProperty("trsp_srvc_note_one_txt")
    private String trspSrvcNoteOneTxt;

    @JsonProperty("trsp_srvc_note_two_txt")
    private String trspSrvcNoteTwoTxt;

    @JsonProperty("freight_rate")
    private BigDecimal freightRate;

    @JsonProperty("car_cls_of_size_cd")
    private String carClsOfSizeCd;

    @JsonProperty("car_cls_of_shp_cd")
    private String carClsOfShpCd;

    @JsonProperty("car_cls_of_tlg_lftr_exst_cd")
    private String carClsOfTlgLftrExstCd;

    @JsonProperty("car_cls_of_wing_body_exst_cd")
    private String carClsOfWingBodyExstCd;

    @JsonProperty("car_cls_of_rfg_exst_cd")
    private String carClsOfRfgExstCd;

    @JsonProperty("trms_of_lwr_tmp_meas")
    private BigDecimal trmsOfLwrTmpMeas;

    @JsonProperty("trms_of_upp_tmp_meas")
    private BigDecimal trmsOfUppTmpMeas;

    @JsonProperty("car_cls_of_crn_exst_cd")
    private String carClsOfCrnExstCd;

    @JsonProperty("car_rmk_about_eqpm_txt")
    private String carRmkAboutEqpmTxt;

    @JsonProperty("del_note_id")
    private String delNoteId;

    @JsonProperty("shpm_num_id")
    private String shpmNumId;

    @JsonProperty("rced_ord_num_id")
    private String rcedOrdNumId;

    @JsonProperty("istd_totl_pcks_quan")
    private BigDecimal istdTotlPcksQuan;

    @JsonProperty("num_unt_cd")
    private String numUntCd;

    @JsonProperty("istd_totl_weig_meas")
    private BigDecimal istdTotlWeigMeas;

    @JsonProperty("weig_unt_cd")
    private String weigUntCd;

    @JsonProperty("istd_totl_vol_meas")
    private BigDecimal istdTotlVolMeas;

    @JsonProperty("vol_unt_cd")
    private String volUntCd;

    @JsonProperty("istd_totl_untl_quan")
    private BigDecimal istdTotlUntlQuan;

    @JsonProperty("cnsg_prty_head_off_id")
    private String cnsgPrtyHeadOffId;

    @JsonProperty("cnsg_prty_brnc_off_id")
    private String cnsgPrtyBrncOffId;

    @JsonProperty("cnsg_prty_name_txt")
    private String cnsgPrtyNameTxt;

    @JsonProperty("cnsg_sct_sped_org_id")
    private String cnsgSctSpedOrgId;

    @JsonProperty("cnsg_sct_sped_org_name_txt")
    private String cnsgSctSpedOrgNameTxt;

    @JsonProperty("cnsg_tel_cmm_cmp_num_txt")
    private String cnsgTelCmmCmpNumTxt;

    @JsonProperty("cnsg_pstl_adrs_line_one_txt")
    private String cnsgPstlAdrsLineOneTxt;

    @JsonProperty("cnsg_pstc_cd")
    private String cnsgPstcCd;

    @JsonProperty("trsp_rqr_prty_head_off_id")
    private String trspRqrPrtyHeadOffId;

    @JsonProperty("trsp_rqr_prty_brnc_off_id")
    private String trspRqrPrtyBrncOffId;

    @JsonProperty("trsp_rqr_prty_name_txt")
    private String trspRqrPrtyNameTxt;

    @JsonProperty("trsp_rqr_sct_sped_org_id")
    private String trspRqrSctSpedOrgId;

    @JsonProperty("trsp_rqr_sct_sped_org_name_txt")
    private String trspRqrSctSpedOrgNameTxt;

    @JsonProperty("trsp_rqr_sct_tel_cmm_cmp_num_txt")
    private String trspRqrSctTelCmmCmpNumTxt;

    @JsonProperty("trsp_rqr_pstl_adrs_line_one_txt")
    private String trspRqrPstlAdrsLineOneTxt;

    @JsonProperty("trsp_rqr_pstc_cd")
    private String trspRqrPstcCd;

    @JsonProperty("cnee_prty_head_off_id")
    private String cneePrtyHeadOffId;

    @JsonProperty("cnee_prty_brnc_off_id")
    private String cneePrtyBrncOffId;

    @JsonProperty("cnee_prty_name_txt")
    private String cneePrtyNameTxt;

    @JsonProperty("cnee_sct_id")
    private String cneeSctId;

    @JsonProperty("cnee_sct_name_txt")
    private String cneeSctNameTxt;

    @JsonProperty("cnee_prim_cnt_pers_name_txt")
    private String cneePrimCntPersNameTxt;

    @JsonProperty("cnee_tel_cmm_cmp_num_txt")
    private String cneeTelCmmCmpNumTxt;

    @JsonProperty("cnee_pstl_adrs_line_one_txt")
    private String cneePstlAdrsLineOneTxt;

    @JsonProperty("cnee_pstc_cd")
    private String cneePstcCd;

    @JsonProperty("logs_srvc_prv_prty_head_off_id")
    private String logsSrvcPrvPrtyHeadOffId;

    @JsonProperty("logs_srvc_prv_prty_brnc_off_id")
    private String logsSrvcPrvPrtyBrncOffId;

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    private String logsSrvcPrvPrtyNameTxt;

    @JsonProperty("logs_srvc_prv_sct_sped_org_id")
    private String logsSrvcPrvSctSpedOrgId;

    @JsonProperty("logs_srvc_prv_sct_sped_org_name_txt")
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @JsonProperty("logs_srvc_prv_sct_prim_cnt_pers_name_txt")
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @JsonProperty("logs_srvc_prv_sct_tel_cmm_cmp_num_txt")
    private String logsSrvcPrvSctTelCmmCmpNumTxt;

    @JsonProperty("trsp_cli_prty_head_off_id")
    private String trspCliPrtyHeadOffId;

    @JsonProperty("trsp_cli_prty_brnc_off_id")
    private String trspCliPrtyBrncOffId;

    @JsonProperty("trsp_cli_prty_name_txt")
    private String trspCliPrtyNameTxt;

    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    private String trspCliTelCmmCmpNumTxt;

    @JsonProperty("fret_clim_to_prty_head_off_id")
    private String fretClimToPrtyHeadOffId;

    @JsonProperty("fret_clim_to_prty_brnc_off_id")
    private String fretClimToPrtyBrncOffId;

    @JsonProperty("fret_clim_to_prty_name_txt")
    private String fretClimToPrtyNameTxt;

    @JsonProperty("fret_clim_to_sct_sped_org_id")
    private String fretClimToSctSpedOrgId;

    @JsonProperty("fret_clim_to_sct_sped_org_name_txt")
    private String fretClimToSctSpedOrgNameTxt;

    public static TrspPlanLineItemResponseDTO fromResult(Object[] result) {
        TrspPlanLineItemResponseDTO dto = new TrspPlanLineItemResponseDTO();
        dto.setTrspInstructionDateSubmDttm((Date) result[0]);
        dto.setInvNumId((String) result[1]);
        dto.setCmnInvNumId((String) result[2]);
        dto.setTrspMeansTypCd((String) result[3]);
        dto.setTrspSrvcTypCd((String) result[4]);
        dto.setRoadCarrSrvcTypCd((String) result[5]);
        dto.setTrspRootPrioCd((String) result[6]);
        dto.setCarClsPrioCd((String) result[7]);
        dto.setClsOfCargInSrvcRqrmCd((String) result[8]);
        dto.setClsOfPkgUpSrvcRqrmCd((String) result[9]);
        dto.setPyrClsSrvcRqrmCd((String) result[10]);
        dto.setTrmsOfMixLoadCndCd((String) result[11]);
        dto.setDsedCllFromDate((Date) result[12]);
        dto.setDsedCllToDate((Date) result[13]);
        dto.setDsedCllFromTime((Time) result[14]);
        dto.setDsedCllToTime((Time) result[15]);
        dto.setDsedCllTimeTrmsSrvcRqrmCd((String) result[16]);
        dto.setApedArrFromDate((Date) result[17]);
        dto.setApedArrToDate((Date) result[18]);
        dto.setApedArrFromTimePrfmDttm((Time) result[19]);
        dto.setApedArrTimeTrmsSrvcRqrmCd((String) result[20]);
        dto.setApedArrToTimePrfmDttm((Time) result[21]);
        dto.setTrmsOfMixLoadTxt((String) result[22]);
        dto.setTrspSrvcNoteOneTxt((String) result[23]);
        dto.setTrspSrvcNoteTwoTxt((String) result[24]);
        dto.setFreightRate((BigDecimal) result[25]);
        dto.setCarClsOfSizeCd((String) result[26]);
        dto.setCarClsOfShpCd((String) result[27]);
        dto.setCarClsOfTlgLftrExstCd((String) result[28]);
        dto.setCarClsOfWingBodyExstCd((String) result[29]);
        dto.setCarClsOfRfgExstCd((String) result[30]);
        dto.setTrmsOfLwrTmpMeas((BigDecimal) result[31]);
        dto.setTrmsOfUppTmpMeas((BigDecimal) result[32]);
        dto.setCarClsOfCrnExstCd((String) result[33]);
        dto.setCarRmkAboutEqpmTxt((String) result[34]);
        dto.setDelNoteId((String) result[35]);
        dto.setShpmNumId((String) result[36]);
        dto.setRcedOrdNumId((String) result[37]);
        dto.setIstdTotlPcksQuan(result[38] != null ? ((BigDecimal) result[38]) : null);
        dto.setNumUntCd((String) result[39]);
        dto.setIstdTotlWeigMeas((BigDecimal) result[40]);
        dto.setWeigUntCd((String) result[41]);
        dto.setIstdTotlVolMeas((BigDecimal) result[42]);
        dto.setVolUntCd((String) result[43]);
        dto.setIstdTotlUntlQuan(result[44] != null ? ((BigDecimal) result[44]) : null);
        dto.setCnsgPrtyHeadOffId((String) result[45]);
        dto.setCnsgPrtyBrncOffId((String) result[46]);
        dto.setCnsgPrtyNameTxt((String) result[47]);
        dto.setCnsgSctSpedOrgId((String) result[48]);
        dto.setCnsgSctSpedOrgNameTxt((String) result[49]);
        dto.setCnsgTelCmmCmpNumTxt((String) result[50]);
        dto.setCnsgPstlAdrsLineOneTxt((String) result[51]);
        dto.setCnsgPstcCd((String) result[52]);
        dto.setTrspRqrPrtyHeadOffId((String) result[53]);
        dto.setTrspRqrPrtyBrncOffId((String) result[54]);
        dto.setTrspRqrPrtyNameTxt((String) result[55]);
        dto.setTrspRqrSctSpedOrgId((String) result[56]);
        dto.setTrspRqrSctSpedOrgNameTxt((String) result[57]);
        dto.setTrspRqrSctTelCmmCmpNumTxt((String) result[58]);
        dto.setTrspRqrPstlAdrsLineOneTxt((String) result[59]);
        dto.setTrspRqrPstcCd((String) result[60]);
        dto.setCneePrtyHeadOffId((String) result[61]);
        dto.setCneePrtyBrncOffId((String) result[62]);
        dto.setCneePrtyNameTxt((String) result[63]);
        dto.setCneeSctId((String) result[64]);
        dto.setCneeSctNameTxt((String) result[65]);
        dto.setCneePrimCntPersNameTxt((String) result[66]);
        dto.setCneeTelCmmCmpNumTxt((String) result[67]);
        dto.setCneePstlAdrsLineOneTxt((String) result[68]);
        dto.setCneePstcCd((String) result[69]);
        dto.setLogsSrvcPrvPrtyHeadOffId((String) result[70]);
        dto.setLogsSrvcPrvPrtyBrncOffId((String) result[71]);
        dto.setLogsSrvcPrvPrtyNameTxt((String) result[72]);
        dto.setLogsSrvcPrvSctSpedOrgId((String) result[73]);
        dto.setLogsSrvcPrvSctSpedOrgNameTxt((String) result[74]);
        dto.setLogsSrvcPrvSctPrimCntPersNameTxt((String) result[75]);
        dto.setLogsSrvcPrvSctTelCmmCmpNumTxt((String) result[76]);
        dto.setTrspCliPrtyHeadOffId((String) result[77]);
        dto.setTrspCliPrtyBrncOffId((String) result[78]);
        dto.setTrspCliPrtyNameTxt((String) result[79]);
        dto.setTrspCliTelCmmCmpNumTxt((String) result[80]);
        dto.setFretClimToPrtyHeadOffId((String) result[81]);
        dto.setFretClimToPrtyBrncOffId((String) result[82]);
        dto.setFretClimToPrtyNameTxt((String) result[83]);
        dto.setFretClimToSctSpedOrgId((String) result[84]);
        dto.setFretClimToSctSpedOrgNameTxt((String) result[85]);
        return dto;
    }
}
