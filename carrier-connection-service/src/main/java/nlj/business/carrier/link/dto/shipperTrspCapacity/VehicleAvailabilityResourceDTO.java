package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両可用性リソース dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvailabilityResourceDTO {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    @ValidateField(notNull = true, maxLength = 20)
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    @ValidateField(notNull = true, maxLength = 5, textHalfWidth = true)
    private String trspOpStrtAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "trspOpDateTrmEndDate", startTimeField = "trspOpPlanDateTrmStrtTime", endTimeField = "trspOpPlanDateTrmEndTime", textHalfWidth = true)
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    @ValidateField(notNull = true, timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    @ValidateField(notNull = true, maxLength = 20)
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    @ValidateField(notNull = true, maxLength = 5, textHalfWidth = true)
    private String trspOpEndAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_end_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    @ValidateField(notNull = true, timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("clb_area_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String clbAreaTxt;

    @JsonProperty("trms_of_clb_area_cd")
    @ValidateField(maxLength = 5, textHalfWidth = true)
    private String trmsOfClbAreaCd;

    @JsonProperty("avb_date_cll_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "avbDateCllDate", textHalfWidth = true, startTimeField = "avbFromTimeOfCllTime", endTimeField = "avbToTimeOfCllTime")
    private String avbDateCllDate;

    @JsonProperty("avb_from_time_of_cll_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String avbFromTimeOfCllTime;

    @JsonProperty("avb_to_time_of_cll_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String avbToTimeOfCllTime;

    @JsonProperty("delb_area_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String delbAreaTxt;

    @JsonProperty("trms_of_delb_area_cd")
    @ValidateField(maxLength = 5, textHalfWidth = true)
    private String trmsOfDelbAreaCd;

    @JsonProperty("esti_del_date_prfm_dttm")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true, endDateField = "estiDelDatePrfmDttm", startTimeField = "avbFromTimeOfDelTime", endTimeField = "avbToTimeOfDelTime")
    private String estiDelDatePrfmDttm;

    @JsonProperty("avb_from_time_of_del_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String avbFromTimeOfDelTime;

    @JsonProperty("avb_to_time_of_del_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String avbToTimeOfDelTime;

    @JsonProperty("avb_load_cp_of_car_meas")
    @ValidateField(precision = 14, scale = 3, textHalfWidth = true)
    private String avbLoadCpOfCarMeas;

    @JsonProperty("avb_load_vol_of_car_meas")
    @ValidateField(precision = 11, scale = 4, textHalfWidth = true)
    private String avbLoadVolOfCarMeas;

    @JsonProperty("pcke_frm_cd")
    @ValidateField(maxLength = 3, textHalfWidth = true, packingCode = true)
    private String pckeFrmCd;

    @JsonProperty("avb_num_of_retb_cntn_of_car_quan")
    @ValidateField(precision = 2, scale = 0, textHalfWidth = true)
    private String avbNumOfRetbCntnOfCarQuan;

    @JsonProperty("trk_bed_stas_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String trkBedStasTxt;

    @JsonProperty("cut_off_info")
    private Set<CutOffInfoDTO> cutOffInfoDTOS;

    @JsonProperty("free_time_info")
    private Set<FreeTimeInfoDTO> freeTimeInfoDTOS;
}
