package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;

/**
 * <PRE>
 * 車両可用性リソース通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvailabilityResourceNotifyDTO {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String startAreaLineOneTxt;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private String startAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String startDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String startTime;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String endAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private String endAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String endDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String endTime;

    @JsonProperty("clb_area_txt")
    private String collaborationAreaTxt;

    @JsonProperty("trms_of_clb_area_cd")
    private String termsOfCollaborationAreaCd;

    @JsonProperty("avb_date_cll_date")
    private String availableCallDate;

    @JsonProperty("avb_from_time_of_cll_time")
    private String availableFromCallTime;

    @JsonProperty("avb_to_time_of_cll_time")
    private String availableToCallTime;

    @JsonProperty("delb_area_txt")
    private String deliveryAreaTxt;

    @JsonProperty("trms_of_delb_area_cd")
    private String termsOfDeliveryAreaCd;

    @JsonProperty("esti_del_date_prfm_dttm")
    private String estimatedDeliveryDateTime;

    @JsonProperty("avb_from_time_of_del_time")
    private String availableFromDeliveryTime;

    @JsonProperty("avb_to_time_of_del_time")
    private String availableToDeliveryTime;

    @JsonProperty("avb_load_cp_of_car_meas")
    private String availableLoadCapacity;

    @JsonProperty("avb_load_vol_of_car_meas")
    private String availableLoadVolume;

    @JsonProperty("pcke_frm_cd")
    private String packageFormCode;

    @JsonProperty("avb_num_of_retb_cntn_of_car_quan")
    private Integer availableNumberOfReturnableContainers;

    @JsonProperty("trk_bed_stas_txt")
    private String truckBedStatusTxt;

    @JsonProperty("cut_off_info")
    private Set<CutOffInfoNotifyDTO> cutOffInfoDTOS;

    @JsonProperty("free_time_info")
    private Set<FreeTimeInfoNotifyDTO> freeTimeInfoDTOS;
}
