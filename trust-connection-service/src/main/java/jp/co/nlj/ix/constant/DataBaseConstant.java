package jp.co.nlj.ix.constant;

/**
 * <PRE>
 * データベース定数クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class DataBaseConstant {

    public static final String COl_TYPE_JSONB = "jsonb";
    public static final String JSONB_TRANSFORMER = "?::jsonb";
    public static final String COL_TYPE_TEXT = "text";
    public static final String DATE_TIME_FORMAT_HHMMSS = "yyyyMMddHHmmss";

    public static final class TransportAbilityMessageInfo {

        public static final String TABLE = "trsp_ability_msg_info";
        public static final String MAPPED_BY = "transportAbilityMessageInfo";
        public static final String OPERATOR_ID = "operator_id";
        public static final String XTRACKING = "x_tracking";
        public static final String MSG_ID = "msg_id";
        public static final String MSG_INFO_CLS_TYP_CD = "msg_info_cls_typ_cd";
        public static final String MSG_DATE_ISS_DTTM = "msg_date_iss_dttm";
        public static final String MSG_TIME_ISS_DTTM = "msg_time_iss_dttm";
        public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
        public static final String NOTE_DCPT_TXT = "note_dcpt_txt";
    }

    public static final class TransportAbilityLineItem {

        public static final String TABLE = "trsp_ability_line_item";
        public static final String MAPPED_BY = "transportAbilityLineItem";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_ABILITY_MSG_INFO_ID = "trsp_ability_msg_info_id";
        public static final String TRSP_CLI_PRTY_HEAD_OFF_ID = "trsp_cli_prty_head_off_id";
        public static final String TRSP_CLI_PRTY_BRNC_OFF_ID = "trsp_cli_prty_brnc_off_id";
        public static final String TRSP_CLI_PRTY_NAME_TXT = "trsp_cli_prty_name_txt";
        public static final String ROAD_CARR_DEPA_SPEO_ORG_ID = "road_carr_depa_sped_org_id";
        public static final String ROAD_CARR_DEPA_SPEO_ORG_NAME_TXT = "road_carr_depa_sped_org_name_txt";
        public static final String TRSP_CLI_TEL_CMM_CMP_NUM_TXT = "trsp_cli_tel_cmm_cmp_num_txt";
        public static final String ROAD_CARR_ARR_SPEO_ORG_ID = "road_carr_arr_sped_org_id";
        public static final String ROAD_CARR_ARR_SPEO_ORG_NAME_TXT = "road_carr_arr_sped_org_name_txt";
        public static final String LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID = "logs_srvc_prv_prty_head_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID = "logs_srvc_prv_prty_brnc_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_NAME_TXT = "logs_srvc_prv_prty_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_SPEO_ORG_ID = "logs_srvc_prv_sct_sped_org_id";
        public static final String LOGS_SRVC_PRV_SCT_SPEO_ORG_NAME_TXT = "logs_srvc_prv_sct_sped_org_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT = "logs_srvc_prv_sct_prim_cnt_pers_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT = "logs_srvc_prv_sct_tel_cmm_cmp_num_txt";
    }

    public static final class CarInfo {

        public static final String TABLE = "car_info";
        public static final String MAPPED_BY = "carInfo";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_ABILLITY_LINE_ITEM_ID = "trsp_ability_line_item_id";
        public static final String SERVICE_STRT_DATE = "service_strt_date";
        public static final String SERVICE_STRT_TIME = "service_strt_time";
        public static final String SERVICE_END_DATE = "service_end_date";
        public static final String SERVICE_END_TIME = "service_end_time";
        public static final String FREIGHT_RATE = "freight_rate";
        public static final String GIAI = "giai";
        public static final String SERVICE_NO = "service_no";
        public static final String SERVICE_NAME = "service_name";
        public static final String CAR_CTRL_NUM_ID = "car_ctrl_num_id";
        public static final String CAR_LICENSE_PLT_NUM_ID = "car_license_plt_num_id";
        public static final String CAR_BODY_NUM_CD = "car_body_num_cd";
        public static final String CAR_CLS_OF_SIZE_CD = "car_cls_of_size_cd";
        public static final String TRACTOR_IDCR = "tractor_idcr";
        public static final String TRAILER_LICENSE_PLT_NUM_ID = "trailer_license_plt_num_id";
        public static final String CAR_WEIG_MEAS = "car_weig_meas";
        public static final String CAR_LNGH_MEAS = "car_lngh_meas";
        public static final String CAR_WID_MEAS = "car_wid_meas";
        public static final String CAR_HGHT_MEAS = "car_hght_meas";
        public static final String CAR_MAX_LOAD_CAPACITY1_MEAS = "car_max_load_capacity1_meas";
        public static final String CAR_MAX_LOAD_CAPACITY2_MEAS = "car_max_load_capacity2_meas";
        public static final String CAR_VOL_OF_HZD_ITEM_MEAS = "car_vol_of_hzd_item_meas";
        public static final String CAR_SPC_GRV_OF_HZD_ITEM_MEAS = "car_spc_grv_of_hzd_item_meas";
        public static final String CAR_TRK_BED_HGHT_MEAS = "car_trk_bed_hght_meas";
        public static final String CAR_TRK_BED_WID_MEAS = "car_trk_bed_wid_meas";
        public static final String CAR_TRK_BED_LNGH_MEAS = "car_trk_bed_lngh_meas";
        public static final String CAR_TRK_BED_GRND_HGHT_MEAS = "car_trk_bed_grnd_hght_meas";
        public static final String CAR_MAX_LOAD_VOL_MEAS = "car_max_load_vol_meas";
        public static final String PCKE_FRM_CD = "pcke_frm_cd";
        public static final String PCKE_FRM_NAME_CD = "pcke_frm_name_cd";
        public static final String CAR_MAX_UNTL_CP_QUAN = "car_max_untl_cp_quan";
        public static final String CAR_CLS_OF_SHP_CD = "car_cls_of_shp_cd";
        public static final String CAR_CLS_OF_TLG_LFTR_EXST_CD = "car_cls_of_tlg_lftr_exst_cd";
        public static final String CAR_CLS_OF_WING_BODY_EXST_CD = "car_cls_of_wing_body_exst_cd";
        public static final String CAR_CLS_OF_RFG_EXST_CD = "car_cls_of_rfg_exst_cd";
        public static final String TRMS_OF_LWR_TMP_MEAS = "trms_of_lwr_tmp_meas";
        public static final String TRMS_OF_UPP_TMP_MEAS = "trms_of_upp_tmp_meas";
        public static final String CAR_CLS_OF_CRN_EXST_CD = "car_cls_of_crn_exst_cd";
        public static final String CAR_RMK_ABOUT_EQPM_TXT = "car_rmk_about_eqpm_txt";
        public static final String CAR_CMPN_NAME_OF_GTP_CRTF_EXST_TXT = "car_cmpn_name_of_gtp_crtf_exst_txt";
    }

    public static final class VehicleAvailabilityResource {

        public static final String TABLE = "vehicle_avb_resource";
        public static final String MAPPED_BY = "vehicleAvailabilityResource";
        public static final String OPERATOR_ID = "operator_id";
        public static final String CAR_INFO_ID = "car_info_id";
        public static final String TRSP_OP_STRT_AREA_LINE_ONE_TXT = "trsp_op_strt_area_line_one_txt";
        public static final String TRSP_OP_STRT_AREA_CTY_JIS_CD = "trsp_op_strt_area_cty_jis_cd";
        public static final String TRSP_OP_DATE_TRM_STRT_DATE = "trsp_op_date_trm_strt_date";
        public static final String TRSP_OP_PLAN_DATE_TRM_STRT_TIME = "trsp_op_plan_date_trm_strt_time";
        public static final String TRSP_OP_END_AREA_LINE_ONE_TXT = "trsp_op_end_area_line_one_txt";
        public static final String TRSP_OP_END_AREA_CTY_JIS_CD = "trsp_op_end_area_cty_jis_cd";
        public static final String TRSP_OP_DATE_TRM_END_DATE = "trsp_op_date_trm_end_date";
        public static final String TRSP_OP_PLAN_DATE_TRM_END_TIME = "trsp_op_plan_date_trm_end_time";
        public static final String CLB_AREA_TXT = "clb_area_txt";
        public static final String TRMS_OF_CLB_AREA_CD = "trms_of_clb_area_cd";
        public static final String AVB_DATE_CLL_DATE = "avb_date_cll_date";
        public static final String AVB_FROM_TIME_OF_CLL_TIME = "avb_from_time_of_cll_time";
        public static final String AVB_TO_TIME_OF_CLL_TIME = "avb_to_time_of_cll_time";
        public static final String DELB_AREA_TXT = "delb_area_txt";
        public static final String TRMS_OF_DELB_AREA_CD = "trms_of_delb_area_cd";
        public static final String ESTI_DEL_DATE_PRFM_DTTM = "esti_del_date_prfm_dttm";
        public static final String AVB_FROM_TIME_OF_DEL_TIME = "avb_from_time_of_del_time";
        public static final String AVB_TO_TIME_OF_DEL_TIME = "avb_to_time_of_del_time";
        public static final String AVB_LOAD_CP_OF_CAR_MEAS = "avb_load_cp_of_car_meas";
        public static final String AVB_LOAD_VOL_OF_CAR_MEAS = "avb_load_vol_of_car_meas";
        public static final String PCKE_FRM_CD = "pcke_frm_cd";
        public static final String AVB_NUM_OF_RETB_CNTN_OF_CAR_QUAN = "avb_num_of_retb_cntn_of_car_quan";
        public static final String TRK_BED_STAS_TXT = "trk_bed_stas_txt";
    }

    public static final class DriverInformation {

        public static final String TABLE = "drv_info";
        public static final String MAPPED_BY = "driverInformation";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_ABILLITY_LINE_ITEM_ID = "trsp_ability_line_item_id";
        public static final String DRV_CTRL_NUM_ID = "drv_ctrl_num_id";
        public static final String DRV_CLS_OF_DRVG_LICENSE_CD = "drv_cls_of_drvg_license_cd";
        public static final String DRV_CLS_OF_FKL_LICENSE_EXST_CD = "drv_cls_of_fkl_license_exst_cd";
        public static final String DRV_RMK_ABOUT_DRV_TXT = "drv_rmk_about_drv_txt";
        public static final String DRV_CMPN_NAME_OF_GTP_CRTF_EXST_TXT = "drv_cmpn_name_of_gtp_crtf_exst_txt";
    }

    public static final class DriverAvailabilityTime {

        public static final String TABLE = "drv_avb_time";
        public static final String OPERATOR_ID = "operator_id";
        public static final String DRV_INFO_ID = "drv_info_id";
        public static final String DRV_AVB_FROM_DATE = "drv_avb_from_date";
        public static final String DRV_AVB_FROM_TIME_OF_WRKG_TIME = "drv_avb_from_time_of_wrkg_time";
        public static final String DRV_AVB_TO_DATE = "drv_avb_to_date";
        public static final String DRV_AVB_TO_TIME_OF_WRKG_TIME = "drv_avb_to_time_of_wrkg_time";
        public static final String DRV_WRKG_TRMS_TXT = "drv_wrkg_trms_txt";
        public static final String DRV_FRMR_OPTG_DATE = "drv_frmr_optg_date";
        public static final String DRV_FRMR_OP_END_TIME = "drv_frmr_op_end_time";
    }

    public static final class DATE_TIME_FORMAT {

        public static final String DATE_FORMAT = "yyyyMMdd";
        public static final String TIME_FORMAT_HHMMSS = "HHmmss";
        public static final String TIME_FORMAT_HHMM = "HHmm";
    }

    public static final class CutOffInfo {

        public static final String TABLE = "cut_off_info";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String CUT_OFF_TIME = "cut_off_time";
        public static final String CUT_OFF_FEE = "cut_off_fee";
    }

    public static final class FreeTimeInfo {

        public static final String TABLE = "free_time_info";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String FREE_TIME = "free_time";
        public static final String FREE_TIME_FEE = "free_time_fee";
    }

    public static final class TrspPlanLineItem {

        public static final String TABLE = "trsp_plan_line_item";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_MSG_INFO_ID = "trsp_plan_msg_info_id";
        public static final String TRSP_INSTRUCTION_ID = "trsp_instruction_id";
        public static final String TRSP_INSTRUCTION_DATE_SUBM_DTTM = "trsp_instruction_date_subm_dttm";
        public static final String INV_NUM_ID = "inv_num_id";
        public static final String CMN_INV_NUM_ID = "cmn_inv_num_id";
        public static final String MIX_LOAD_NUM_ID = "mix_load_num_id";
        public static final String SERVICE_NO = "service_no";
        public static final String SERVICE_NAME = "service_name";
        public static final String SERVICE_STRT_DATE = "service_strt_date";
        public static final String SERVICE_STRT_TIME = "service_strt_time";
        public static final String SERVICE_END_DATE = "service_end_date";
        public static final String SERVICE_END_TIME = "service_end_time";
        public static final String FREIGHT_RATE = "freight_rate";
        public static final String TRSP_MEANS_TYP_CD = "trsp_means_typ_cd";
        public static final String TRSP_SRVC_TYP_CD = "trsp_srvc_typ_cd";
        public static final String ROAD_CARR_SRVC_TYP_CD = "road_carr_srvc_typ_cd";
        public static final String TRSP_ROOT_PRIO_CD = "trsp_root_prio_cd";
        public static final String CAR_CLS_PRIO_CD = "car_cls_prio_cd";
        public static final String CLS_OF_CARG_IN_SRVC_RQRM_CD = "cls_of_carg_in_srvc_rqrm_cd";
        public static final String CLS_OF_PKG_UP_SRVC_RQRM_CD = "cls_of_pkg_up_srvc_rqrm_cd";
        public static final String PYR_CLS_SRVC_RQRM_CD = "pyr_cls_srvc_rqrm_cd";
        public static final String TRMS_OF_MIX_LOAD_CND_CD = "trms_of_mix_load_cnd_cd";
        public static final String DSED_CLL_FROM_DATE = "dsed_cll_from_date";
        public static final String DSED_CLL_TO_DATE = "dsed_cll_to_date";
        public static final String DSED_CLL_FROM_TIME = "dsed_cll_from_time";
        public static final String DSED_CLL_TO_TIME = "dsed_cll_to_time";
        public static final String DSED_CLL_TIME_TRMS_SRVC_RQRM_CD = "dsed_cll_time_trms_srvc_rqrm_cd";
        public static final String APED_ARR_FROM_DATE = "aped_arr_from_date";
        public static final String APED_ARR_TO_DATE = "aped_arr_to_date";
        public static final String APED_ARR_FROM_TIME_PRFM_DTTM = "aped_arr_from_time_prfm_dttm";
        public static final String APED_ARR_TO_TIME_PRFM_DTTM = "aped_arr_to_time_prfm_dttm";
        public static final String APED_ARR_TIME_TRMS_SRVC_RQRM_CD = "aped_arr_time_trms_srvc_rqrm_cd";
        public static final String TRMS_OF_MIX_LOAD_TXT = "trms_of_mix_load_txt";
        public static final String TRSP_SRVC_NOTE_ONE_TXT = "trsp_srvc_note_one_txt";
        public static final String TRSP_SRVC_NOTE_TWO_TXT = "trsp_srvc_note_two_txt";
        public static final String CAR_CLS_OF_SIZE_CD = "car_cls_of_size_cd";
        public static final String CAR_CLS_OF_SHP_CD = "car_cls_of_shp_cd";
        public static final String CAR_CLS_OF_TLG_LFTR_EXST_CD = "car_cls_of_tlg_lftr_exst_cd";
        public static final String CAR_CLS_OF_WING_BODY_EXST_CD = "car_cls_of_wing_body_exst_cd";
        public static final String CAR_CLS_OF_RFG_EXST_CD = "car_cls_of_rfg_exst_cd";
        public static final String TRMS_OF_LWR_TMP_MEAS = "trms_of_lwr_tmp_meas";
        public static final String TRMS_OF_UPP_TMP_MEAS = "trms_of_upp_tmp_meas";
        public static final String CAR_CLS_OF_CRN_EXST_CD = "car_cls_of_crn_exst_cd";
        public static final String CAR_RMK_ABOUT_EQPM_TXT = "car_rmk_about_eqpm_txt";
        public static final String DEL_NOTE_ID = "del_note_id";
        public static final String SHPM_NUM_ID = "shpm_num_id";
        public static final String RCED_ORD_NUM_ID = "rced_ord_num_id";
        public static final String ISTD_TOTL_PCKS_QUAN = "istd_totl_pcks_quan";
        public static final String NUM_UNT_CD = "num_unt_cd";
        public static final String ISTD_TOTL_WEIG_MEAS = "istd_totl_weig_meas";
        public static final String WEIG_UNT_CD = "weig_unt_cd";
        public static final String ISTD_TOTL_VOL_MEAS = "istd_totl_vol_meas";
        public static final String VOL_UNT_CD = "vol_unt_cd";
        public static final String ISTD_TOTL_UNTL_QUAN = "istd_totl_untl_quan";
        public static final String CNSG_PRTY_HEAD_OFF_ID = "cnsg_prty_head_off_id";
        public static final String CNSG_PRTY_BRNC_OFF_ID = "cnsg_prty_brnc_off_id";
        public static final String CNSG_PRTY_NAME_TXT = "cnsg_prty_name_txt";
        public static final String CNSG_SCT_SPED_ORG_ID = "cnsg_sct_sped_org_id";
        public static final String CNSG_SCT_SPED_ORG_NAME_TXT = "cnsg_sct_sped_org_name_txt";
        public static final String CNSG_TEL_CMM_CMP_NUM_TXT = "cnsg_tel_cmm_cmp_num_txt";
        public static final String CNSG_PSTL_ADRS_LINE_ONE_TXT = "cnsg_pstl_adrs_line_one_txt";
        public static final String CNSG_PSTC_CD = "cnsg_pstc_cd";
        public static final String TRSP_RQR_PRTY_HEAD_OFF_ID = "trsp_rqr_prty_head_off_id";
        public static final String TRSP_RQR_PRTY_BRNC_OFF_ID = "trsp_rqr_prty_brnc_off_id";
        public static final String TRSP_RQR_PRTY_NAME_TXT = "trsp_rqr_prty_name_txt";
        public static final String TRSP_RQR_SCT_SPED_ORG_ID = "trsp_rqr_sct_sped_org_id";
        public static final String TRSP_RQR_SCT_SPED_ORG_NAME_TXT = "trsp_rqr_sct_sped_org_name_txt";
        public static final String TRSP_RQR_SCT_TEL_CMM_CMP_NUM_TXT = "trsp_rqr_sct_tel_cmm_cmp_num_txt";
        public static final String TRSP_RQR_PSTL_ADRS_LINE_ONE_TXT = "trsp_rqr_pstl_adrs_line_one_txt";
        public static final String TRSP_RQR_PSTC_CD = "trsp_rqr_pstc_cd";
        public static final String CNEE_PRTY_HEAD_OFF_ID = "cnee_prty_head_off_id";
        public static final String CNEE_PRTY_BRNC_OFF_ID = "cnee_prty_brnc_off_id";
        public static final String CNEE_PRTY_NAME_TXT = "cnee_prty_name_txt";
        public static final String CNEE_SCT_ID = "cnee_sct_id";
        public static final String CNEE_SCT_NAME_TXT = "cnee_sct_name_txt";
        public static final String CNEE_PRIM_CNT_PERS_NAME_TXT = "cnee_prim_cnt_pers_name_txt";
        public static final String CNEE_TEL_CMM_CMP_NUM_TXT = "cnee_tel_cmm_cmp_num_txt";
        public static final String CNEE_PSTL_ADRS_LINE_ONE_TXT = "cnee_pstl_adrs_line_one_txt";
        public static final String CNEE_PSTC_CD = "cnee_pstc_cd";
        public static final String LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID = "logs_srvc_prv_prty_head_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID = "logs_srvc_prv_prty_brnc_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_NAME_TXT = "logs_srvc_prv_prty_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_SPED_ORG_ID = "logs_srvc_prv_sct_sped_org_id";
        public static final String LOGS_SRVC_PRV_SCT_SPED_ORG_NAME_TXT = "logs_srvc_prv_sct_sped_org_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT = "logs_srvc_prv_sct_prim_cnt_pers_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT = "logs_srvc_prv_sct_tel_cmm_cmp_num_txt";
        public static final String TRSP_CLI_PRTY_HEAD_OFF_ID = "trsp_cli_prty_head_off_id";
        public static final String TRSP_CLI_PRTY_BRNC_OFF_ID = "trsp_cli_prty_brnc_off_id";
        public static final String TRSP_CLI_PRTY_NAME_TXT = "trsp_cli_prty_name_txt";
        public static final String ROAD_CARR_DEPA_SPED_ORG_ID = "road_carr_depa_sped_org_id";
        public static final String ROAD_CARR_DEPA_SPED_ORG_NAME_TXT = "road_carr_depa_sped_org_name_txt";
        public static final String TRSP_CLI_TEL_CMM_CMP_NUM_TXT = "trsp_cli_tel_cmm_cmp_num_txt";
        public static final String ROAD_CARR_ARR_SPED_ORG_ID = "road_carr_arr_sped_org_id";
        public static final String ROAD_CARR_ARR_SPED_ORG_NAME_TXT = "road_carr_arr_sped_org_name_txt";
        public static final String FRET_CLIM_TO_PRTY_HEAD_OFF_ID = "fret_clim_to_prty_head_off_id";
        public static final String FRET_CLIM_TO_PRTY_BRNC_OFF_ID = "fret_clim_to_prty_brnc_off_id";
        public static final String FRET_CLIM_TO_PRTY_NAME_TXT = "fret_clim_to_prty_name_txt";
        public static final String FRET_CLIM_TO_SCT_SPED_ORG_ID = "fret_clim_to_sct_sped_org_id";
        public static final String FRET_CLIM_TO_SCT_SPED_ORG_NAME_TXT = "fret_clim_to_sct_sped_org_name_txt";

    }

    public static final class TrspPlanMsgInfo {

        public static final String TABLE = "trsp_plan_msg_info";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String X_TRACKING = "x-tracking";
        public static final String MSG_ID = "msg_id";
        public static final String MSG_INFO_CLS_TYP_CD = "msg_info_cls_typ_cd";
        public static final String MSG_DATE_ISS_DTTM = "msg_date_iss_dttm";
        public static final String MSG_TIME_ISS_DTTM = "msg_time_iss_dttm";
        public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
        public static final String NOTE_DCPT_TXT = "note_dcpt_txt";
        public static final String TRSP_PLAN_STAS_CD = "trsp_plan_stas_cd";
    }

    public static final class CnsLineItem {

        public static final String TABLE = "cns_line_item";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_LINE_ITEM_ID = "trsp_plan_line_item_id";
        public static final String LINE_ITEM_NUM_ID = "line_item_num_id";
        public static final String SEV_ORD_NUM_ID = "sev_ord_num_id";
        public static final String CNSG_CRG_ITEM_NUM_ID = "cnsg_crg_item_num_id";
        public static final String BUY_ASSI_ITEM_CD = "buy_assi_item_cd";
        public static final String SELL_ASSI_ITEM_CD = "sell_assi_item_cd";
        public static final String WRHS_ASSI_ITEM_CD = "wrhs_assi_item_cd";
        public static final String ITEM_NAME_TXT = "item_name_txt";
        public static final String GODS_IDCS_IN_OTS_PCKE_NAME_TXT = "gods_idcs_in_ots_pcke_name_txt";
        public static final String NUM_OF_ISTD_UNTL_QUAN = "num_of_istd_untl_quan";
        public static final String NUM_OF_ISTD_QUAN = "num_of_istd_quan";
        public static final String SEV_NUM_UNT_CD = "sev_num_unt_cd";
        public static final String ISTD_PCKE_WEIG_MEAS = "istd_pcke_weig_meas";
        public static final String SEV_WEIG_UNT_CD = "sev_weig_unt_cd";
        public static final String ISTD_PCKE_VOL_MEAS = "istd_pcke_vol_meas";
        public static final String SEV_VOL_UNT_CD = "sev_vol_unt_cd";
        public static final String ISTD_QUAN_MEAS = "istd_quan_meas";
        public static final String CNTE_NUM_UNT_CD = "cnte_num_unt_cd";
        public static final String DCPV_TRPN_PCKG_TXT = "dcpv_trpn_pckg_txt";
        public static final String PCKE_FRM_CD = "pcke_frm_cd";
        public static final String PCKE_FRM_NAME_CD = "pcke_frm_name_cd";
        public static final String CRG_HND_TRMS_SPCL_ISRS_TXT = "crg_hnd_trms_spcl_isrs_txt";
        public static final String GLB_RETB_ASSE_ID = "glb_retb_asse_id";
        public static final String TOTL_RTI_QUAN_QUAN = "totl_rti_quan_quan";
        public static final String CHRG_OF_PCKE_CTRL_NUM_UNT_AMNT = "chrg_of_pcke_ctrl_num_unt_amnt";
    }

    public static final class ShipFromPrty {

        public static final String TABLE = "ship_from_prty";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_LINE_ITEM_ID = "trsp_plan_line_item_id";
        public static final String SHIP_FROM_PRTY_HEAD_OFF_ID = "ship_from_prty_head_off_id";
        public static final String SHIP_FROM_PRTY_BRNC_OFF_ID = "ship_from_prty_brnc_off_id";
        public static final String SHIP_FROM_PRTY_NAME_TXT = "ship_from_prty_name_txt";
        public static final String SHIP_FROM_SCT_ID = "ship_from_sct_id";
        public static final String SHIP_FROM_SCT_NAME_TXT = "ship_from_sct_name_txt";
        public static final String SHIP_FROM_TEL_CMM_CMP_NUM_TXT = "ship_from_tel_cmm_cmp_num_txt";
        public static final String SHIP_FROM_PSTL_ADRS_CTY_ID = "ship_from_pstl_adrs_cty_id";
        public static final String SHIP_FROM_PSTL_ADRS_ID = "ship_from_pstl_adrs_id";
        public static final String SHIP_FROM_PSTL_ADRS_LINE_ONE_TXT = "ship_from_pstl_adrs_line_one_txt";
        public static final String SHIP_FROM_PSTC_CD = "ship_from_pstc_cd";
        public static final String PLC_CD_PRTY_ID = "plc_cd_prty_id";
        public static final String GLN_PRTY_ID = "gln_prty_id";
        public static final String JPN_UPLC_CD = "jpn_uplc_cd";
        public static final String JPN_VAN_SRVC_CD = "jpn_van_srvc_cd";
        public static final String JPN_VAN_VANS_CD = "jpn_van_vans_cd";
    }

    public static final class ShipFromPrtyRqrm {

        public static final String TABLE = "ship_from_prty_rqrm";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_FROM_PRTY_ID = "ship_from_prty_id";
        public static final String TRMS_OF_CAR_SIZE_CD = "trms_of_car_size_cd";
        public static final String TRMS_OF_CAR_HGHT_MEAS = "trms_of_car_hght_meas";
        public static final String TRMS_OF_GTP_CERT_TXT = "trms_of_gtp_cert_txt";
        public static final String TRMS_OF_CLL_TXT = "trms_of_cll_txt";
        public static final String TRMS_OF_GODS_HND_TXT = "trms_of_gods_hnd_txt";
        public static final String ANC_WRK_OF_CLL_TXT = "anc_wrk_of_cll_txt";
        public static final String SPCL_WRK_TXT = "spcl_wrk_txt";
    }

    public static final class ShipToPrty {

        public static final String TABLE = "ship_to_prty";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_LINE_ITEM_ID = "trsp_plan_line_item_id";
        public static final String SHIP_TO_PRTY_HEAD_OFF_ID = "ship_to_prty_head_off_id";
        public static final String SHIP_TO_PRTY_BRNC_OFF_ID = "ship_to_prty_brnc_off_id";
        public static final String SHIP_TO_PRTY_NAME_TXT = "ship_to_prty_name_txt";
        public static final String SHIP_TO_SCT_ID = "ship_to_sct_id";
        public static final String SHIP_TO_SCT_NAME_TXT = "ship_to_sct_name_txt";
        public static final String SHIP_TO_PRIM_CNT_ID = "ship_to_prim_cnt_id";
        public static final String SHIP_TO_PRIM_CNT_PERS_NAME_TXT = "ship_to_prim_cnt_pers_name_txt";
        public static final String SHIP_TO_TEL_CMM_CMP_NUM_TXT = "ship_to_tel_cmm_cmp_num_txt";
        public static final String SHIP_TO_PSTL_ADRS_CTY_ID = "ship_to_pstl_adrs_cty_id";
        public static final String SHIP_TO_PSTL_ADRS_ID = "ship_to_pstl_adrs_id";
        public static final String SHIP_TO_PSTL_ADRS_LINE_ONE_TXT = "ship_to_pstl_adrs_line_one_txt";
        public static final String SHIP_TO_PSTC_CD = "ship_to_pstc_cd";
        public static final String PLC_CD_PRTY_ID = "plc_cd_prty_id";
        public static final String GLN_PRTY_ID = "gln_prty_id";
        public static final String JPN_UPLC_CD = "jpn_uplc_cd";
        public static final String JPN_VAN_SRVC_CD = "jpn_van_srvc_cd";
        public static final String JPN_VAN_VANS_CD = "jpn_van_vans_cd";
    }

    public static final class ShipToPrtyRqrm {

        public static final String TABLE = "ship_to_prty_rqrm";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_TO_PRTY_ID = "ship_to_prty_id";
        public static final String TRMS_OF_CAR_SIZE_CD = "trms_of_car_size_cd";
        public static final String TRMS_OF_CAR_HGHT_MEAS = "trms_of_car_hght_meas";
        public static final String TRMS_OF_GTP_CERT_TXT = "trms_of_gtp_cert_txt";
        public static final String TRMS_OF_DEL_TXT = "trms_of_del_txt";
        public static final String TRMS_OF_GODS_HND_TXT = "trms_of_gods_hnd_txt";
        public static final String ANC_WRK_OF_DEL_TXT = "anc_wrk_of_del_txt";
        public static final String SPCL_WRK_TXT = "spcl_wrk_txt";
    }

    public static final class ShipCutOffInfo {

        public static final String TABLE = "ship_cut_off_info";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_FROM_PRTY_ID = "ship_from_prty_id";
        public static final String CUT_OFF_TIME = "cut_off_time";
        public static final String CUT_OFF_FEE = "cut_off_fee";
    }

    public static final class ShipFreeTimeInfo {

        public static final String TABLE = "ship_free_time_info";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_TO_PRTY_ID = "ship_to_prty_id";
        public static final String FREE_TIME = "free_time";
        public static final String FREE_TIME_FEE = "free_time_fee";
    }

    public static final class ReqTrspPlanLineItem {

        public static final String TABLE = "req_trsp_plan_line_item";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_MSG_INFO_ID = "req_trsp_plan_msg_info_id";
        public static final String TRSP_INSTRUCTION_ID = "trsp_instruction_id";
        public static final String TRSP_INSTRUCTION_DATE_SUBM_DTTM = "trsp_instruction_date_subm_dttm";
        public static final String INV_NUM_ID = "inv_num_id";
        public static final String CMN_INV_NUM_ID = "cmn_inv_num_id";
        public static final String MIX_LOAD_NUM_ID = "mix_load_num_id";
        public static final String FREIGHT_RATE = "freight_rate";
        public static final String TRSP_MEANS_TYP_CD = "trsp_means_typ_cd";
        public static final String TRSP_SRVC_TYP_CD = "trsp_srvc_typ_cd";
        public static final String ROAD_CARR_SRVC_TYP_CD = "road_carr_srvc_typ_cd";
        public static final String TRSP_ROOT_PRIO_CD = "trsp_root_prio_cd";
        public static final String CAR_CLS_PRIO_CD = "car_cls_prio_cd";
        public static final String CLS_OF_CARG_IN_SRVC_RQRM_CD = "cls_of_carg_in_srvc_rqrm_cd";
        public static final String CLS_OF_PKG_UP_SRVC_RQRM_CD = "cls_of_pkg_up_srvc_rqrm_cd";
        public static final String PYR_CLS_SRVC_RQRM_CD = "pyr_cls_srvc_rqrm_cd";
        public static final String TRMS_OF_MIX_LOAD_CND_CD = "trms_of_mix_load_cnd_cd";
        public static final String DSED_CLL_FROM_DATE = "dsed_cll_from_date";
        public static final String DSED_CLL_TO_DATE = "dsed_cll_to_date";
        public static final String DSED_CLL_FROM_TIME = "dsed_cll_from_time";
        public static final String DSED_CLL_TO_TIME = "dsed_cll_to_time";
        public static final String DSED_CLL_TIME_TRMS_SRVC_RQRM_CD = "dsed_cll_time_trms_srvc_rqrm_cd";
        public static final String APED_ARR_FROM_DATE = "aped_arr_from_date";
        public static final String APED_ARR_TO_DATE = "aped_arr_to_date";
        public static final String APED_ARR_FROM_TIME_PRFM_DTTM = "aped_arr_from_time_prfm_dttm";
        public static final String APED_ARR_TO_TIME_PRFM_DTTM = "aped_arr_to_time_prfm_dttm";
        public static final String APED_ARR_TIME_TRMS_SRVC_RQRM_CD = "aped_arr_time_trms_srvc_rqrm_cd";
        public static final String TRMS_OF_MIX_LOAD_TXT = "trms_of_mix_load_txt";
        public static final String TRSP_SRVC_NOTE_ONE_TXT = "trsp_srvc_note_one_txt";
        public static final String TRSP_SRVC_NOTE_TWO_TXT = "trsp_srvc_note_two_txt";
        public static final String CAR_CLS_OF_SIZE_CD = "car_cls_of_size_cd";
        public static final String CAR_CLS_OF_SHP_CD = "car_cls_of_shp_cd";
        public static final String CAR_CLS_OF_TLG_LFTR_EXST_CD = "car_cls_of_tlg_lftr_exst_cd";
        public static final String CAR_CLS_OF_WING_BODY_EXST_CD = "car_cls_of_wing_body_exst_cd";
        public static final String CAR_CLS_OF_RFG_EXST_CD = "car_cls_of_rfg_exst_cd";
        public static final String TRMS_OF_LWR_TMP_MEAS = "trms_of_lwr_tmp_meas";
        public static final String TRMS_OF_UPP_TMP_MEAS = "trms_of_upp_tmp_meas";
        public static final String CAR_CLS_OF_CRN_EXST_CD = "car_cls_of_crn_exst_cd";
        public static final String CAR_RMK_ABOUT_EQPM_TXT = "car_rmk_about_eqpm_txt";
        public static final String DEL_NOTE_ID = "del_note_id";
        public static final String SHPM_NUM_ID = "shpm_num_id";
        public static final String DEL_CTRL_NUM_ID = "del_ctrl_num_id";
        public static final String ISTD_TOTL_PCKS_QUAN = "istd_totl_pcks_quan";
        public static final String NUM_UNT_CD = "num_unt_cd";
        public static final String ISTD_TOTL_WEIG_MEAS = "istd_totl_weig_meas";
        public static final String WEIG_UNT_CD = "weig_unt_cd";
        public static final String ISTD_TOTL_VOL_MEAS = "istd_totl_vol_meas";
        public static final String VOL_UNT_CD = "vol_unt_cd";
        public static final String ISTD_TOTL_UNTL_QUAN = "istd_totl_untl_quan";
        public static final String CNSG_PRTY_HEAD_OFF_ID = "cnsg_prty_head_off_id";
        public static final String CNSG_PRTY_BRNC_OFF_ID = "cnsg_prty_brnc_off_id";
        public static final String CNSG_PRTY_NAME_TXT = "cnsg_prty_name_txt";
        public static final String CNSG_SCT_SPED_ORG_ID = "cnsg_sct_sped_org_id";
        public static final String CNSG_SCT_SPED_ORG_NAME_TXT = "cnsg_sct_sped_org_name_txt";
        public static final String CNSG_TEL_CMM_CMP_NUM_TXT = "cnsg_tel_cmm_cmp_num_txt";
        public static final String CNSG_PSTL_ADRS_LINE_ONE_TXT = "cnsg_pstl_adrs_line_one_txt";
        public static final String CNSG_PSTC_CD = "cnsg_pstc_cd";
        public static final String TRSP_RQR_PRTY_HEAD_OFF_ID = "trsp_rqr_prty_head_off_id";
        public static final String TRSP_RQR_PRTY_BRNC_OFF_ID = "trsp_rqr_prty_brnc_off_id";
        public static final String TRSP_RQR_PRTY_NAME_TXT = "trsp_rqr_prty_name_txt";
        public static final String TRSP_RQR_SCT_SPED_ORG_ID = "trsp_rqr_sct_sped_org_id";
        public static final String TRSP_RQR_SCT_SPED_ORG_NAME_TXT = "trsp_rqr_sct_sped_org_name_txt";
        public static final String TRSP_RQR_SCT_TEL_CMM_CMP_NUM_TXT = "trsp_rqr_sct_tel_cmm_cmp_num_txt";
        public static final String TRSP_RQR_PSTL_ADRS_LINE_ONE_TXT = "trsp_rqr_pstl_adrs_line_one_txt";
        public static final String TRSP_RQR_PSTC_CD = "trsp_rqr_pstc_cd";
        public static final String CNEE_PRTY_HEAD_OFF_ID = "cnee_prty_head_off_id";
        public static final String CNEE_PRTY_BRNC_OFF_ID = "cnee_prty_brnc_off_id";
        public static final String CNEE_PRTY_NAME_TXT = "cnee_prty_name_txt";
        public static final String CNEE_SCT_ID = "cnee_sct_id";
        public static final String CNEE_SCT_NAME_TXT = "cnee_sct_name_txt";
        public static final String CNEE_PRIM_CNT_PERS_NAME_TXT = "cnee_prim_cnt_pers_name_txt";
        public static final String CNEE_TEL_CMM_CMP_NUM_TXT = "cnee_tel_cmm_cmp_num_txt";
        public static final String CNEE_PSTL_ADRS_LINE_ONE_TXT = "cnee_pstl_adrs_line_one_txt";
        public static final String CNEE_PSTC_CD = "cnee_pstc_cd";
        public static final String LOGS_SRVC_PRV_PRTY_HEAD_OFF_ID = "logs_srvc_prv_prty_head_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_BRNC_OFF_ID = "logs_srvc_prv_prty_brnc_off_id";
        public static final String LOGS_SRVC_PRV_PRTY_NAME_TXT = "logs_srvc_prv_prty_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_SPED_ORG_ID = "logs_srvc_prv_sct_sped_org_id";
        public static final String LOGS_SRVC_PRV_SCT_SPED_ORG_NAME_TXT = "logs_srvc_prv_sct_sped_org_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_PRIM_CNT_PERS_NAME_TXT = "logs_srvc_prv_sct_prim_cnt_pers_name_txt";
        public static final String LOGS_SRVC_PRV_SCT_TEL_CMM_CMP_NUM_TXT = "logs_srvc_prv_sct_tel_cmm_cmp_num_txt";
        public static final String TRSP_CLI_PRTY_HEAD_OFF_ID = "trsp_cli_prty_head_off_id";
        public static final String TRSP_CLI_PRTY_BRNC_OFF_ID = "trsp_cli_prty_brnc_off_id";
        public static final String TRSP_CLI_PRTY_NAME_TXT = "trsp_cli_prty_name_txt";
        public static final String ROAD_CARR_DEPA_SPED_ORG_ID = "road_carr_depa_sped_org_id";
        public static final String ROAD_CARR_DEPA_SPED_ORG_NAME_TXT = "road_carr_depa_sped_org_name_txt";
        public static final String TRSP_CLI_TEL_CMM_CMP_NUM_TXT = "trsp_cli_tel_cmm_cmp_num_txt";
        public static final String ROAD_CARR_ARR_SPED_ORG_ID = "road_carr_arr_sped_org_id";
        public static final String ROAD_CARR_ARR_SPED_ORG_NAME_TXT = "road_carr_arr_sped_org_name_txt";
        public static final String FRET_CLIM_TO_PRTY_HEAD_OFF_ID = "fret_clim_to_prty_head_off_id";
        public static final String FRET_CLIM_TO_PRTY_BRNC_OFF_ID = "fret_clim_to_prty_brnc_off_id";
        public static final String FRET_CLIM_TO_PRTY_NAME_TXT = "fret_clim_to_prty_name_txt";
        public static final String FRET_CLIM_TO_SCT_SPED_ORG_ID = "fret_clim_to_sct_sped_org_id";
        public static final String FRET_CLIM_TO_SCT_SPED_ORG_NAME_TXT = "fret_clim_to_sct_sped_org_name_txt";
        public static final String BRT_RSV_NEC_CD = "brt_rsv_nec_cd";
        public static final String BRT_RSV_STAS_CD = "brt_rsv_stas_cd";
        public static final String BRT_RSV_NUM_ID = "brt_rsv_num_id";

    }

    public static final class ReqTrspPlanMsgInfo {

        public static final String TABLE = "req_trsp_plan_msg_info";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String X_TRACKING = "x-tracking";
        public static final String MSG_ID = "msg_id";
        public static final String MSG_INFO_CLS_TYP_CD = "msg_info_cls_typ_cd";
        public static final String MSG_DATE_ISS_DTTM = "msg_date_iss_dttm";
        public static final String MSG_TIME_ISS_DTTM = "msg_time_iss_dttm";
        public static final String MSG_FN_STAS_CD = "msg_fn_stas_cd";
        public static final String NOTE_DCPT_TXT = "note_dcpt_txt";
        public static final String TRSP_PLAN_STAS_CD = "trsp_plan_stas_cd";
    }

    public static final class ReqCnsLineItem {

        public static final String TABLE = "req_cns_line_item";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String REQ_TRSP_PLAN_LINE_ITEM_ID = "req_trsp_plan_line_item_id";
        public static final String LINE_ITEM_NUM_ID = "line_item_num_id";
        public static final String SEV_ORD_NUM_ID = "sev_ord_num_id";
        public static final String CNSG_CRG_ITEM_NUM_ID = "cnsg_crg_item_num_id";
        public static final String BUY_ASSI_ITEM_CD = "buy_assi_item_cd";
        public static final String SELL_ASSI_ITEM_CD = "sell_assi_item_cd";
        public static final String WRHS_ASSI_ITEM_CD = "wrhs_assi_item_cd";
        public static final String ITEM_NAME_TXT = "item_name_txt";
        public static final String GODS_IDCS_IN_OTS_PCKE_NAME_TXT = "gods_idcs_in_ots_pcke_name_txt";
        public static final String NUM_OF_ISTD_UNTL_QUAN = "num_of_istd_untl_quan";
        public static final String NUM_OF_ISTD_QUAN = "num_of_istd_quan";
        public static final String SEV_NUM_UNT_CD = "sev_num_unt_cd";
        public static final String ISTD_PCKE_WEIG_MEAS = "istd_pcke_weig_meas";
        public static final String SEV_WEIG_UNT_CD = "sev_weig_unt_cd";
        public static final String ISTD_PCKE_VOL_MEAS = "istd_pcke_vol_meas";
        public static final String SEV_VOL_UNT_CD = "sev_vol_unt_cd";
        public static final String ISTD_QUAN_MEAS = "istd_quan_meas";
        public static final String CNTE_NUM_UNT_CD = "cnte_num_unt_cd";
        public static final String DCPV_TRPN_PCKG_TXT = "dcpv_trpn_pckg_txt";
        public static final String PCKE_FRM_CD = "pcke_frm_cd";
        public static final String PCKE_FRM_NAME_CD = "pcke_frm_name_cd";
        public static final String CRG_HND_TRMS_SPCL_ISRS_TXT = "crg_hnd_trms_spcl_isrs_txt";
        public static final String GLB_RETB_ASSE_ID = "glb_retb_asse_id";
        public static final String TOTL_RTI_QUAN_QUAN = "totl_rti_quan_quan";
        public static final String CHRG_OF_PCKE_CTRL_NUM_UNT_AMNT = "chrg_of_pcke_ctrl_num_unt_amnt";
        public static final String CLS_OF_KPNG_TMP_CD = "cls_of_kpng_tmp_cd";
        public static final String SELL_CTRL_NUM_ID = "sell_ctrl_num_id";
    }

    public static final class ReqShipFromPrty {

        public static final String TABLE = "req_ship_from_prty";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_LINE_ITEM_ID = "req_trsp_plan_line_item_id";
        public static final String SHIP_FROM_PRTY_HEAD_OFF_ID = "ship_from_prty_head_off_id";
        public static final String SHIP_FROM_PRTY_BRNC_OFF_ID = "ship_from_prty_brnc_off_id";
        public static final String SHIP_FROM_PRTY_NAME_TXT = "ship_from_prty_name_txt";
        public static final String SHIP_FROM_SCT_ID = "ship_from_sct_id";
        public static final String SHIP_FROM_SCT_NAME_TXT = "ship_from_sct_name_txt";
        public static final String SHIP_FROM_TEL_CMM_CMP_NUM_TXT = "ship_from_tel_cmm_cmp_num_txt";
        public static final String SHIP_FROM_PSTL_ADRS_CTY_ID = "ship_from_pstl_adrs_cty_id";
        public static final String SHIP_FROM_PSTL_ADRS_LINE_ONE_TXT = "ship_from_pstl_adrs_line_one_txt";
        public static final String SHIP_FROM_PSTC_CD = "ship_from_pstc_cd";
        public static final String FROM_PLC_CD_PRTY_ID = "from_plc_cd_prty_id";
        public static final String FROM_GLN_PRTY_ID = "from_gln_prty_id";
        public static final String FROM_JPN_UPLC_CD = "from_jpn_uplc_cd";
        public static final String FROM_JPN_VAN_SRVC_CD = "from_jpn_van_srvc_cd";
        public static final String FROM_JPN_VAN_VANS_CD = "from_jpn_van_vans_cd";
    }

    public static final class ReqShipFromPrtyRqrm {

        public static final String TABLE = "req_ship_from_prty_rqrm";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_FROM_PRTY_ID = "req_ship_from_prty_id";
        public static final String FROM_TRMS_OF_CAR_SIZE_CD = "from_trms_of_car_size_cd";
        public static final String FROM_TRMS_OF_CAR_HGHT_MEAS = "from_trms_of_car_hght_meas";
        public static final String FROM_TRMS_OF_GTP_CERT_TXT = "from_trms_of_gtp_cert_txt";
        public static final String TRMS_OF_CLL_TXT = "trms_of_cll_txt";
        public static final String FROM_TRMS_OF_GODS_HND_TXT = "from_trms_of_gods_hnd_txt";
        public static final String ANC_WRK_OF_CLL_TXT = "anc_wrk_of_cll_txt";
        public static final String FROM_SPCL_WRK_TXT = "from_spcl_wrk_txt";
    }

    public static final class ReqShipToPrty {

        public static final String TABLE = "req_ship_to_prty";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String TRSP_PLAN_LINE_ITEM_ID = "req_trsp_plan_line_item_id";
        public static final String SHIP_TO_PRTY_HEAD_OFF_ID = "ship_to_prty_head_off_id";
        public static final String SHIP_TO_PRTY_BRNC_OFF_ID = "ship_to_prty_brnc_off_id";
        public static final String SHIP_TO_PRTY_NAME_TXT = "ship_to_prty_name_txt";
        public static final String SHIP_TO_SCT_ID = "ship_to_sct_id";
        public static final String SHIP_TO_SCT_NAME_TXT = "ship_to_sct_name_txt";
        public static final String SHIP_TO_PRIM_CNT_ID = "ship_to_prim_cnt_id";
        public static final String SHIP_TO_PRIM_CNT_PERS_NAME_TXT = "ship_to_prim_cnt_pers_name_txt";
        public static final String SHIP_TO_TEL_CMM_CMP_NUM_TXT = "ship_to_tel_cmm_cmp_num_txt";
        public static final String SHIP_TO_PSTL_ADRS_CTY_ID = "ship_to_pstl_adrs_cty_id";
        public static final String SHIP_TO_PSTL_ADRS_ID = "ship_to_pstl_adrs_id";
        public static final String SHIP_TO_PSTL_ADRS_LINE_ONE_TXT = "ship_to_pstl_adrs_line_one_txt";
        public static final String SHIP_TO_PSTC_CD = "ship_to_pstc_cd";
        public static final String TO_PLC_CD_PRTY_ID = "to_plc_cd_prty_id";
        public static final String TO_GLN_PRTY_ID = "to_gln_prty_id";
        public static final String TO_JPN_UPLC_CD = "to_jpn_uplc_cd";
        public static final String TO_JPN_VAN_SRVC_CD = "to_jpn_van_srvc_cd";
        public static final String TO_JPN_VAN_VANS_CD = "to_jpn_van_vans_cd";
    }

    public static final class ReqShipToPrtyRqrm {

        public static final String TABLE = "req_ship_to_prty_rqrm";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String SHIP_TO_PRTY_ID = "req_ship_to_prty_id";
        public static final String TO_TRMS_OF_CAR_SIZE_CD = "to_trms_of_car_size_cd";
        public static final String TO_TRMS_OF_CAR_HGHT_MEAS = "to_trms_of_car_hght_meas";
        public static final String TO_TRMS_OF_GTP_CERT_TXT = "to_trms_of_gtp_cert_txt";
        public static final String TRMS_OF_DEL_TXT = "trms_of_del_txt";
        public static final String TO_TRMS_OF_GODS_HND_TXT = "to_trms_of_gods_hnd_txt";
        public static final String ANC_WRK_OF_DEL_TXT = "anc_wrk_of_del_txt";
        public static final String TO_SPCL_WRK_TXT = "to_spcl_wrk_txt";

    }

    public static final class TransPreOrder {

        public static final String TABLE = "t_trans_pre_order";
        public static final String ID = "id";
        public static final String CARRIER_OPERATOR_ID = "carrier_operator_id";
        public static final String SHIPPER_OPERATOR_ID = "shipper_operator_id";
        public static final String TRANS_MATCHING_ID = "trans_matching_id";
        public static final String TRANSPORT_PLAN_ID = "transport_plan_id";
        public static final String TRANSPORT_PLAN_ITEM_ID = "transport_plan_item_id";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String TRANSPORT_PLAN_ITEM_SNAPSHOT = "transport_plan_item_snapshot";
        public static final String VEHICLE_DIAGRAM_ITEM_SNAPSHOT = "vehicle_diagram_item_snapshot";

        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_SNAPSHOT = "vehicle_diagram_item_trailer_snapshot";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String COLLECTION_DATE = "collection_date";
        public static final String COLLECTION_PRICE = "collection_price";
        public static final String COLLECTION_TIME_FROM = "collection_time_from";
        public static final String COLLECTION_TIME_TO = "collection_time_to";
        public static final String STATUS = "status";
        public static final String MAPPED_BY = "transPreOrder";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String TRANSPORT_DATE = "transport_date";
        public static final String PRICE = "price";
    }

    public static final class TransMatching {

        public static final String TABLE = "t_trans_matching";
        public static final String MAPPED_BY = "transMatching";
        public static final String ID = "id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String CARRIER_OPERATOR_ID = "carrier_operator_id";
        public static final String SHIPPER_OPERATOR_ID = "shipper_operator_id";
        public static final String CARRIER_OPERATOR_CODE = "carrier_operator_code";
        public static final String SHIPPER_OPERATOR_CODE = "shipper_operator_code";
        public static final String CARRIER2_OPERATOR_ID = "carrier2_operator_id";
        public static final String CARRIER2_OPERATOR_CODE = "carrier2_operator_code";
        public static final String BATCH_ID = "batch_id";
        public static final String REQ_CNS_LINE_ITEM_ID = "req_cns_line_item_id";
        public static final String CNS_LINE_ITEM_ID = "cns_line_item_id";
        public static final String CNS_LINE_ITEM_BY_DATE_ID = "cns_line_item_by_date_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String VEHICLE_AVB_RESOURCE_ITEM_ID = "vehicle_avb_resource_item_id";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String TRANSPORT_DATE = "transport_date";
        public static final String COLLECTION_PRICE = "collection_price";
        public static final String CARRIER_PRICE = "carrier_price";
        public static final String STATUS = "status";
        public static final String CREATED_DATE = "created_date";
        public static final String REQUEST_SNAPSHOT = "request_snapshot";
        public static final String PROPOSE_SNAPSHOT = "propose_snapshot";
    }

    public static final class VehicleDiagram {

        public static final String TABLE = "t_vehicle_diagram";
        public static final String MAPPED_BY = "vehicleDiagram";
        public static final String OPERATOR_ID = "operator_id";
        public static final String DIAGRAM_HEAD_ID = "diagram_head_id";
        public static final String ROUND_TRIP_TYPE = "round_trip_type";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String DAY_WEEK = "day_week";
        public static final String ADJUSTMENT_PRICE = "adjustment_price";
        public static final String COMMON_PRICE = "common_price";
        public static final String CUT_OFF_PRICE = "cut_off_price";
        public static final String STATUS = "status";
    }

    public static final class VehicleDiagramItem {

        public static final String TABLE = "t_vehicle_diagram_item";

        public static final String MAPPED_BY = "vehicleDiagramItem";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String DAY = "day";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String PRICE = "price";
        public static final String STATUS = "status";
        public static final Integer STATUS_ON_SALE = 1;
    }

    public static final class VehicleDiagramHead {

        public static final String TABLE = "t_vehicle_diagram_head";
        public static final String MAPPED_BY = "vehicleDiagramHead";
        public static final String OPERATOR_ID = "operator_id";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String ONE_WAY_TIME = "one_way_time";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String REPEAT_DAY = "repeat_day";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String IS_ROUND_TRIP = "is_round_trip";
        public static final String ORIGIN_TYPE = "origin_type";
        public static final String IMPORT_ID = "import_id";
        public static final String STATUS = "status";
    }

    public static final class VehicleDiagramAllocation {

        public static final String TABLE = "t_vehicle_diagram_allocation";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String VEHICLE_INFO_ID = "vehicle_info_id";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String DISPLAY_ORDER = "display_order";
        public static final String ASSIGN_TYPE = "assign_type";
    }

    public static final class VehicleDiagramItemTrailer {

        public static final String TABLE = "t_vehicle_diagram_item_trailer";
        public static final String OPERATOR_ID = "operator_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";
        public static final String VEHICLE_DIAGRAM_ALLOCATION_ID = "vehicle_diagram_allocation_id";
        public static final String DAY = "day";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String ADJUSTMENT_PRICE = "adjustment_price";

        public static final String STATUS = "status";
        public static final Integer STATUS_ON_SALE = 1;
    }

    public static final class TransportPlan {

        public static final String TABLE = "t_transport_plan";
        public static final String MAPPED_BY = "transportPlan";

    }

    public static final class TransportPlanItem {

        public static final String TABLE = "t_transport_plan_item";
        public static final String TRANSPORT_PLAN_ID = "transport_plan_id";
        public static final String MAPPED_BY = "transportPlanItem";
        public static final Integer STATUS_ON_SALE = 1;
    }

    public static final class TransportPlanTrailer {

        public static final String TABLE = "transport_plan_trailer";
        public static final String TRANSPORT_PLAN_ID = "transport_plan_id";
        public static final String TRANSPORT_PLAN_ITEM_ID = "transport_plan_item_id";
    }

    public static final class VehicleInfo {

        public static final String TABLE = "t_vehicle_info";
        public static final String OPERATOR_ID = "operator_id";
        public static final String REGISTRATION_AREA_CODE = "registration_area_code";
        public static final String REGISTRATION_GROUP_NUMBER = "registration_group_number";
        public static final String REGISTRATION_CHARACTER = "registration_character";
        public static final String REGISTRATION_NUMBER_1 = "registration_number_1";
        public static final String REGISTRATION_NUMBER_2 = "registration_number_2";
        public static final String VEHICLE_CODE = "vehicle_code";
        public static final String VEHICLE_NAME = "vehicle_name";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String VEHICLE_SIZE = "vehicle_size";
        public static final String TEMPERATURE_RANGE = "temperature_range";
        public static final String MAX_PAYLOAD = "max_payload";
        public static final String TOTAL_LENGTH = "total_length";
        public static final String TOTAL_WIDTH = "total_width";
        public static final String TOTAL_HEIGHT = "total_height";
        public static final String GROUND_CLEARANCE = "ground_clearance";
        public static final String DOOR_HEIGHT = "door_height";
        public static final String BODY_SPECIFICATION = "body_specification";
        public static final String BODY_SHAPE = "body_shape";
        public static final String BODY_CONSTRUCTION = "body_construction";
        public static final String IMAGES = "images";
        public static final String STATUS = "status";
    }

    public static final class TransOrder {

        public static final String TABLE = "t_trans_order";
        public static final String ID = "id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String CARRIER_OPERATOR_CODE = "carrier_operator_code";
        public static final String CARRIER_OPERATOR_NAME = "carrier_operator_name";
        public static final String SHIPPER_OPERATOR_CODE = "shipper_operator_code";
        public static final String SHIPPER_OPERATOR_NAME = "shipper_operator_name";
        public static final String CARRIER2_OPERATOR_ID = "carrier2_operator_id";
        public static final String CARRIER2_OPERATOR_CODE = "carrier2_operator_code";
        public static final String CARRIER2_OPERATOR_NAME = "carrier2_operator_name";
        public static final String REQ_CNS_LINE_ITEM_ID = "req_cns_line_item_id";
        public static final String CNS_LINE_ITEM_ID = "cns_line_item_id";
        public static final String CNS_LINE_ITEM_BY_DATE_ID = "cns_line_item_by_date_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String VEHICLE_AVB_RESOURCE_ITEM_ID = "vehicle_avb_resource_item_id";
        public static final String CARRIER_OPERATOR_ID = "carrier_operator_id";
        public static final String SHIPPER_OPERATOR_ID = "shipper_operator_id";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String TRANSPORT_DATE = "transport_date";
        public static final String PRICE = "price";
        public static final String CONTRACT_FILE = "contract_file";
        public static final String STATUS = "status";
        public static final String REQUEST_COLLECTION_TIME_FROM = "request_collection_time_from";
        public static final String REQUEST_COLLECTION_TIME_TO = "request_collection_time_to";
        public static final String PROPOSE_PRICE = "propose_price";
        public static final String PROPOSE_DEPARTURE_TIME = "propose_departure_time";
        public static final String PROPOSE_ARRIVAL_TIME = "propose_arrival_time";
        public static final String REQUEST_PRICE = "request_price";
        public static final String SHIPPER_CONTRACT_FILE = "shipper_contract_file";
        public static final String CARRIER_CONTRACT_FILE = "carrier_contract_file";
        public static final String CARRIER2_CONTRACT_FILE = "carrier2_contract_file";
        public static final String REQUEST_SNAPSHOT = "request_snapshot";
        public static final String PROPOSE_SNAPSHOT = "propose_snapshot";
        public static final String PARENT_ORDER_ID = "parent_order_id ";
        public static final String TRANS_MATCHING_ID = "trans_matching_id";
        public static final String TRSP_INSTRUCTION_ID = "trsp_instruction_id";
        public static final String TRANSPORT_PLAN_ITEM_ID = "transport_plan_item_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String NEGOTIATION_DATA = "negotiation_data";
        public static final String SERVICE_NAME = "service_name";
        public static final String ITEM_NAME_TXT = "item_name_txt";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String VEHICLE_DIAGRAM_ITEM_ID = "vehicle_diagram_item_id";

        public static final class TransType {

            public static final Integer SHIPPER_REQUEST = 0;
            public static final Integer CARRIER_REQUEST = 1;
        }
    }

    public static final class CnsLineItemByDate {

        public static final String TABLE = "cns_line_item_by_date";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String OPERATOR_CODE = "operator_code";
        public static final String REQ_CNS_LINE_ITEM_ID = "req_cns_line_item_id";
        public static final String TRANS_PLAN_ID = "trans_plan_id";
        public static final String TRANSPORT_CODE = "transport_code";
        public static final String TRANSPORT_NAME = "transport_name";
        public static final String COLLECTION_DATE = "collection_date";
        public static final String COLLECTION_TIME_FROM = "collection_time_from";
        public static final String COLLECTION_TIME_TO = "collection_time_to";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String TRAILER_NUMBER = "trailer_number";
        public static final String TRAILER_NUMBER_REST = "trailer_number_rest";
        public static final String PRICE_PER_UNIT = "price_per_unit";
        public static final String VEHICLE_CONDITION = "vehicle_condition";
        public static final String OUTER_PACKAGE_CODE = "outer_package_code";
        public static final String TOTAL_LENGTH = "total_length";
        public static final String TOTAL_WIDTH = "total_width";
        public static final String TOTAL_HEIGHT = "total_height";
        public static final String WEIGHT = "weight";
        public static final String SPECIAL_INSTRUCTIONS = "special_instructions";
        public static final String STATUS = "status";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_ORDER_ID = "trans_order_id";
        public static final String CNS_LINE_ITEM_ID = "cns_line_item_id";
        public static final String TRSP_PLAN_ITEM_ID = "transport_plan_item_id";
        public static final String OPERATOR_NAME = "operator_name";

        public static final String TEMPERATURE_RANGE = "temperature_range";
    }

    public static final class VehicleAvbResourceItem {

        public static final String TABLE = "vehicle_avb_resource_item";
        public static final String ID = "id";
        public static final String OPERATOR_ID = "operator_id";
        public static final String OPERATOR_CODE = "operator_code";
        public static final String CAR_INFO_ID = "car_info_id";
        public static final String VEHICLE_AVB_RESOURCE_ID = "vehicle_avb_resource_id";
        public static final String VEHICLE_DIAGRAM_ID = "vehicle_diagram_id";
        public static final String DEPARTURE_FROM = "departure_from";
        public static final String ARRIVAL_TO = "arrival_to";
        public static final String DAY = "day";
        public static final String TRIP_NAME = "trip_name";
        public static final String DEPARTURE_TIME_MIN = "departure_time_min";
        public static final String DEPARTURE_TIME_MAX = "departure_time_max";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String ADJUSTMENT_PRICE = "adjustment_price";
        public static final String VEHICLE_TYPE = "vehicle_type";
        public static final String DISPLAY_ORDER = "display_order";
        public static final String ASSIGN_TYPE = "assign_type";
        public static final String MAX_PAYLOAD = "max_payload";
        public static final String TOTAL_LENGTH = "total_length";
        public static final String TOTAL_WIDTH = "total_width";
        public static final String TOTAL_HEIGHT = "total_height";
        public static final String VEHICLE_SIZE = "vehicle_size";
        public static final String CAR_MAX_LOAD_CAPACITY1_MEAS = "car_max_load_capacity1_meas";
        public static final String TEMPERATURE_RANGE = "temperature_range";
        public static final String PRICE = "price";
        public static final String STATUS = "status";
        public static final String GIAI = "giai";

        public static final String CUT_OFF_INFO_ID = "cut_off_info_id";
        public static final String CUT_OFF_TIME = "cut_off_time";
        public static final String CUT_OFF_FEE = "cut_off_fee";
        public static final String FREE_TIME_INFO_ID = "free_time_info_id";
        public static final String FREE_TIME_TIME = "free_time_time";
        public static final String FREE_TIME_FEE = "free_time_fee";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_ORDER_ID = "trans_order_id";
        public static final String VEHICLE_DIAGRAM_ITEM_TRAILER_ID = "vehicle_diagram_item_trailer_id";
        public static final String OPERATOR_NAME = "operator_name";
        public static final String VEHICLE_NAME = "vehicle_name";
        public static final String DEPARTURE_TIME = "departure_time";


        public static final class Status {

            public static final Integer INITIALIZED = 0;
            public static final Integer MARKET = 1;
            public static final Integer AUTOMATIC_MATCHING = 2;
            public static final Integer AWAITING_CONFIRMATION = 3;
            public static final Integer CONTRACT = 4;
            public static final Integer PAYMENT = 5;
            public static final Integer TRANSPORT = 6;
            public static final Integer COMPLETED = 7;
            public static final Integer SHIPPER_CANCEL = 8;
            public static final Integer CARRIER_CANCEL = 9;
        }
    }

    public static class CarrierOperator {

        public static final String TABLE = "c_carrier_operator";
        public static final String ID = "id";
        public static final String OPERATOR_CODE = "operator_code";
        public static final String OPERATOR_NAME = "operator_name";
        public static final String POSTAL_CODE = "postal_code";
        public static final String ADDRESS_1 = "address_1";
        public static final String ADDRESS_2 = "address_2";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String MANAGER_NAME = "manager_name";
        public static final String IMAGE_LOGO = "image_logo";
        public static final String NOTES = "notes";
        public static final String STATUS = "status";
        public static final String CREATED_DATE = "created_date";
        public static final String CREATED_USER = "created_user";
        public static final String UPDATED_DATE = "updated_date";
        public static final String UPDATED_USER = "updated_user";
        public static final String MAIL = "email";
    }

    public static final class ShipperOperator {

        public static final String TABLE = "s_shipper_operator";
        public static final String ID = "id";
        public static final String OPERATOR_CODE = "operator_code";
        public static final String OPERATOR_NAME = "operator_name";
        public static final String POSTAL_CODE = "postal_code";
        public static final String ADDRESS_1 = "address_1";
        public static final String ADDRESS_2 = "address_2";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String MANAGER_NAME = "manager_name";
        public static final String IMAGE_LOGO = "image_logo";
        public static final String OTHERS = "others";
        public static final String STATUS = "status";
    }
}
