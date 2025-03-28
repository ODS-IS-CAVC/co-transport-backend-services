package nlj.business.transaction.dto.shipperTrspCapacity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoDTO {

    @JsonProperty("vehicle_avb_resource")
    private List<VehicleAvailabilityResourceDTO> vehicleAvailabilityResourceDTOS;

    @JsonProperty("service_strt_date")
    private String serviceStrtDate;

    @JsonProperty("service_strt_time")
    private String serviceStrtTime;

    @JsonProperty("service_end_date")
    private String serviceEndDate;

    @JsonProperty("service_end_time")
    private String serviceEndTime;

    @JsonProperty("freight_rate")
    private String freightRate;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("car_ctrl_num_id")
    private String carCtrlNumId;

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("car_body_num_cd")
    private String carBodyNumCd;

    @JsonProperty("car_cls_of_size_cd")
    private String carClsOfSizeCd;

    @JsonProperty("tractor_idcr")
    private String tractorIdcr;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("car_weig_meas")
    private String carWeigMeas;

    @JsonProperty("car_lngh_meas")
    private String carLnghMeas;

    @JsonProperty("car_wid_meas")
    private String carWidMeas;

    @JsonProperty("car_hght_meas")
    private String carHghtMeas;

    @JsonProperty("car_max_load_capacity1_meas")
    private String carMaxLoadCapacity1Meas;

    @JsonProperty("car_max_load_capacity2_meas")
    private String carMaxLoadCapacity2Meas;

    @JsonProperty("car_vol_of_hzd_item_meas")
    private String carVolOfHzdItemMeas;

    @JsonProperty("car_spc_grv_of_hzd_item_meas")
    private String carSpcGrvOfHzdItemMeas;

    @JsonProperty("car_trk_bed_hght_meas")
    private String carTrkBedHghtMeas;

    @JsonProperty("car_trk_bed_wid_meas")
    private String carTrkBedWidMeas;

    @JsonProperty("car_trk_bed_lngh_meas")
    private String carTrkBedLnghMeas;

    @JsonProperty("car_trk_bed_grnd_hght_meas")
    private String carTrkBedGrndHghtMeas;

    @JsonProperty("car_max_load_vol_meas")
    private String carMaxLoadVolMeas;

    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd;

    @JsonProperty("pcke_frm_name_cd")
    private String pckeFrmNameCd;

    @JsonProperty("car_max_untl_cp_quan")
    private String carMaxUntlCpQuan;

    @JsonProperty("car_cls_of_shp_cd")
    private String carClsOfShpCd;

    @JsonProperty("car_cls_of_tlg_lftr_exst_cd")
    private String carClsOfTlgLftrExstCd;

    @JsonProperty("car_cls_of_wing_body_exst_cd")
    private String carClsOfWingBodyExstCd;

    @JsonProperty("car_cls_of_rfg_exst_cd")
    private String carClsOfRfgExstCd;

    @JsonProperty("trms_of_lwr_tmp_meas")
    private String trmsOfLwrTmpMeas;

    @JsonProperty("trms_of_upp_tmp_meas")
    private String trmsOfUppTmpMeas;

    @JsonProperty("car_cls_of_crn_exst_cd")
    private String carClsOfCrnExstCd;

    @JsonProperty("car_rmk_about_eqpm_txt")
    private String carRmkAboutEqpmTxt;

    @JsonProperty("car_cmpn_name_of_gtp_crtf_exst_txt")
    private String carCmpnNameOfGtpCrfExstTxt;
}
