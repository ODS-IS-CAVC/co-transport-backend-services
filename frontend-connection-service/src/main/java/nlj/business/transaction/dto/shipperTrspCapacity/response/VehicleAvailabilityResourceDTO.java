package nlj.business.transaction.dto.shipperTrspCapacity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両可用性リソースDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAvailabilityResourceDTO {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private String trspOpStrtAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private String trspOpEndAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("clb_area_txt")
    private String clbAreaTxt;

    @JsonProperty("trms_of_clb_area_cd")
    private String trmsOfClbAreaCd;

    @JsonProperty("avb_date_cll_date")
    private String avbDateCllDate;

    @JsonProperty("avb_from_time_of_cll_time")
    private String avbFromTimeOfCllTime;

    @JsonProperty("avb_to_time_of_cll_time")
    private String avbToTimeOfCllTime;

    @JsonProperty("delb_area_txt")
    private String delbAreaTxt;

    @JsonProperty("trms_of_delb_area_cd")
    private String trmsOfDelbAreaCd;

    @JsonProperty("esti_del_date_prfm_dttm")
    private String estiDelDatePrfmDttm;

    @JsonProperty("avb_from_time_of_del_time")
    private String avbFromTimeOfDelTime;

    @JsonProperty("avb_to_time_of_del_time")
    private String avbToTimeOfDelTime;

    @JsonProperty("avb_load_cp_of_car_meas")
    private String avbLoadCpOfCarMeas;

    @JsonProperty("avb_load_vol_of_car_meas")
    private String avbLoadVolOfCarMeas;

    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd;

    @JsonProperty("avb_num_of_retb_cntn_of_car_quan")
    private String avbNumOfRetbCntnOfCarQuan;

    @JsonProperty("trk_bed_stas_txt")
    private String trkBedStasTxt;
}
