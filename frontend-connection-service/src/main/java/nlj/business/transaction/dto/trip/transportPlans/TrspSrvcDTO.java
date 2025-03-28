package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspSrvcDTO {

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
    private String dsedCllFromDate;

    @JsonProperty("dsed_cll_to_date")
    private String dsedCllToDate;

    @JsonProperty("dsed_cll_from_time")
    private String dsedCllFromTime;

    @JsonProperty("dsed_cll_to_time")
    private String dsedCllToTime;

    @JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @JsonProperty("aped_arr_from_date")
    private String apedArrFromDate;

    @JsonProperty("aped_arr_to_date")
    private String apedArrToDate;

    @JsonProperty("aped_arr_from_time_prfm_dttm")
    private String apedArrFromTimePrfmDttm;

    @JsonProperty("aped_arr_time_trms_srvc_rqrm_cd")
    private String apedArrTimeTrmsSrvcRqrmCd;

    @JsonProperty("aped_arr_to_time_prfm_dttm")
    private String apedArrToTimePrfmDttm;

    @JsonProperty("trms_of_mix_load_txt")
    private String trmsOfMixLoadTxt;

    @JsonProperty("trsp_srvc_note_one_txt")
    private String trspSrvcNoteOneTxt;

    @JsonProperty("trsp_srvc_note_two_txt")
    private String trspSrvcNoteTwoTxt;

    @JsonProperty("freight_rate")
    private String freightRate;
} 