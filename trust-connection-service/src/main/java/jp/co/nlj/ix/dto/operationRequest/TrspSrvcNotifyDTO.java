package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送サービス通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspSrvcNotifyDTO {

    @JsonProperty("service_no")
    private String serviceNo; // 便・ダイヤ番号

    @JsonProperty("service_name")
    private String serviceName; // 便・ダイヤ名称

    @JsonProperty("service_strt_date")
    private String serviceStrtDate; // 便の運行日

    @JsonProperty("service_strt_time")
    private String serviceStrtTime; // 便の運行時刻

    @JsonProperty("service_end_date")
    private String serviceEndDate; // 便の運行終了日

    @JsonProperty("service_end_time")
    private String serviceEndTime; // 便の運行終了時刻

    @JsonProperty("freight_rate")
    private BigDecimal freightRate; // 希望運賃

    @JsonProperty("trsp_means_typ_cd")
    private String trspMeansTypCd; // 運送手段コード

    @JsonProperty("trsp_srvc_typ_cd")
    private String trspSrvcTypCd; // 運送サービスコード

    @JsonProperty("road_carr_srvc_typ_cd")
    private String roadCarrSrvcTypCd; // 運送事業者サービスコード

    @JsonProperty("trsp_root_prio_cd")
    private String trspRootPrioCd; // 運送ルートコード

    @JsonProperty("car_cls_prio_cd")
    private String carClsPrioCd; // 車輌種別コード

    @JsonProperty("cls_of_carg_in_srvc_rqrm_cd")
    private String clsOfCargInSrvcRqrmCd; // 持込区分コード

    @JsonProperty("cls_of_pkg_up_srvc_rqrm_cd")
    private String clsOfPkgUpSrvcRqrmCd; // 引取区分コード

    @JsonProperty("pyr_cls_srvc_rqrm_cd")
    private String pyrClsSrvcRqrmCd; // 元払着払種別コード

    @JsonProperty("trms_of_mix_load_cnd_cd")
    private String trmsOfMixLoadCndCd; // 混載可否

    @JsonProperty("dsed_cll_from_date")
    private String dsedCllFromDate; // 集荷希望日（から）

    @JsonProperty("dsed_cll_to_date")
    private String dsedCllToDate; // 集荷希望日（まで）

    @JsonProperty("dsed_cll_from_time")
    private String dsedCllFromTime; // 集荷希望時刻（から）

    @JsonProperty("dsed_cll_to_time")
    private String dsedCllToTime; // 集荷希望時刻（まで）

    @JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
    private String dsedCllTimeTrmsSrvcRqrmCd; // 集荷希望時刻条件

    @JsonProperty("aped_arr_from_date")
    private String apedArrFromDate; // 着荷指定日（から）

    @JsonProperty("aped_arr_to_date")
    private String apedArrToDate; // 着荷指定日（まで）

    @JsonProperty("aped_arr_from_time_prfm_dttm")
    private String apedArrFromTimePrfmDttm; // 着荷指定時刻（から）

    @JsonProperty("aped_arr_to_time_prfm_dttm")
    private String apedArrToTimePrfmDttm; // 着荷指定時刻（まで）

    @JsonProperty("aped_arr_time_trms_srvc_rqrm_cd")
    private String apedArrTimeTrmsSrvcRqrmCd; // 着荷指定時刻条件

    @JsonProperty("trms_of_mix_load_txt")
    private String trmsOfMixLoadTxt; // 混載条件

    @JsonProperty("trsp_srvc_note_one_txt")
    private String trspSrvcNoteOneTxt; // 運送サービス備考1

    @JsonProperty("trsp_srvc_note_two_txt")
    private String trspSrvcNoteTwoTxt; // 運送サービス備考2

}
