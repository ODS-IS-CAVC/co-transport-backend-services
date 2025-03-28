package jp.co.nlj.ix.dto.trspInstruction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 要求運送サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqTrspSrvcDTO {

    @JsonProperty("freight_rate")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true, notNull = true)
    private BigDecimal freightRate; // 希望運賃

    @ValidateField(maxLength = 2, textHalfWidth = true)
    @JsonProperty("trsp_means_typ_cd")
    private String trspMeansTypCd; // 運送手段コード

    @ValidateField(maxLength = 2, textHalfWidth = true)
    @JsonProperty("trsp_srvc_typ_cd")
    private String trspSrvcTypCd; // 運送サービスコード

    @ValidateField(maxLength = 9, textHalfWidth = true)
    @JsonProperty("road_carr_srvc_typ_cd")
    private String roadCarrSrvcTypCd; // 運送事業者サービスコード

    @ValidateField(maxLength = 50, textHalfWidth = true)
    @JsonProperty("trsp_root_prio_cd")
    private String trspRootPrioCd; // 運送ルートコード

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("car_cls_prio_cd")
    private String carClsPrioCd; // 車輌種別コード

    @ValidateField(maxLength = 1, textHalfWidth = true)
    @JsonProperty("cls_of_carg_in_srvc_rqrm_cd")
    private String clsOfCargInSrvcRqrmCd; // 持込区分コード

    @ValidateField(maxLength = 1, textHalfWidth = true)
    @JsonProperty("cls_of_pkg_up_srvc_rqrm_cd")
    private String clsOfPkgUpSrvcRqrmCd; // 引取区分コード

    @ValidateField(maxLength = 2, textHalfWidth = true)
    @JsonProperty("pyr_cls_srvc_rqrm_cd")
    private String pyrClsSrvcRqrmCd; // 元払着払種別コード

    @ValidateField(maxLength = 1, textHalfWidth = true)
    @JsonProperty("trms_of_mix_load_cnd_cd")
    private String trmsOfMixLoadCndCd; // 混載可否

    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT,
        endDateField = "dsedCllToDate", startTimeField = "dsedCllFromTime", endTimeField = "dsedCllToTime")
    @JsonProperty("dsed_cll_from_date")
    private String dsedCllFromDate; // 集荷希望日（から）

    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    @JsonProperty("dsed_cll_to_date")
    private String dsedCllToDate; // 集荷希望日（まで）

    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    @JsonProperty("dsed_cll_from_time")
    private String dsedCllFromTime; // 集荷希望時刻（から）

    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    @JsonProperty("dsed_cll_to_time")
    private String dsedCllToTime; // 集荷希望時刻（まで）

    @ValidateField(maxLength = 2, hourFormat = true, textHalfWidth = true)
    @JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
    private String dsedCllTimeTrmsSrvcRqrmCd; // 集荷希望時刻条件

    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT,
        endDateField = "apedArrToDate", startTimeField = "apedArrFromTimePrfmDttm",
        endTimeField = "apedArrToTimePrfmDttm")
    @JsonProperty("aped_arr_from_date")
    private String apedArrFromDate; // 着荷指定日（から）

    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    @JsonProperty("aped_arr_to_date")
    private String apedArrToDate; // 着荷指定日（まで）

    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    @JsonProperty("aped_arr_from_time_prfm_dttm")
    private String apedArrFromTimePrfmDttm; // 着荷指定時刻（から）

    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    @JsonProperty("aped_arr_to_time_prfm_dttm")
    private String apedArrToTimePrfmDttm; // 着荷指定時刻（まで）

    @ValidateField(maxLength = 2, hourFormat = true, textHalfWidth = true)
    @JsonProperty("aped_arr_time_trms_srvc_rqrm_cd")
    private String apedArrTimeTrmsSrvcRqrmCd; // 着荷指定時刻条件

    @ValidateField(maxLength = 100, textFullWidth = true)
    @JsonProperty("trms_of_mix_load_txt")
    private String trmsOfMixLoadTxt; // 混載条件

    @ValidateField(maxLength = 500, textFullWidth = true)
    @JsonProperty("trsp_srvc_note_one_txt")
    private String trspSrvcNoteOneTxt; // 運送サービス備考1

    @ValidateField(maxLength = 500, textFullWidth = true)
    @JsonProperty("trsp_srvc_note_two_txt")
    private String trspSrvcNoteTwoTxt; // 運送サービス備考2

}
