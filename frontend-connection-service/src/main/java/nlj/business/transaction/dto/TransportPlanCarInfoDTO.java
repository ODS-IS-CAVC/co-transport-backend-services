package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;

/**
 * <PRE>
 * 運送計画車両情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportPlanCarInfoDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd;

    @JsonProperty("car_wid_meas")
    private Double carWidMeas;

    @JsonProperty("freight_rate")
    private Double freightRate;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("tractor_idcr")
    private String tractorIdcr;

    @JsonProperty("car_hght_meas")
    private Double carHghtMeas;

    @JsonProperty("car_lngh_meas")
    private Double carLnghMeas;

    @JsonProperty("car_weig_meas")
    private Double carWeigMeas;

    @JsonProperty("car_body_num_cd")
    private String carBodyNumCd;

    @JsonProperty("car_ctrl_num_id")
    private String carCtrlNumId;

    @JsonProperty("pcke_frm_name_cd")
    private String pckeFrmNameCd;

    @JsonProperty("service_end_date")
    private String serviceEndDate;

    @JsonProperty("service_end_time")
    private String serviceEndTime;

    @JsonProperty("car_cls_of_shp_cd")
    private String carClsOfShpCd;

    @JsonProperty("service_strt_date")
    private String serviceStrtDate;

    @JsonProperty("service_strt_time")
    private String serviceStrtTime;

    @JsonProperty("car_cls_of_size_cd")
    private String carClsOfSizeCd;

    @JsonProperty("car_max_untl_cp_quan")
    private Double carMaxUntlCpQuan;

    @JsonProperty("car_trk_bed_wid_meas")
    private Double carTrkBedWidMeas;

    @JsonProperty("trms_of_lwr_tmp_meas")
    private Double trmsOfLwrTmpMeas;

    @JsonProperty("trms_of_upp_tmp_meas")
    private Double trmsOfUppTmpMeas;

    @JsonProperty("car_max_load_vol_meas")
    private Double carMaxLoadVolMeas;

    @JsonProperty("car_trk_bed_hght_meas")
    private Double carTrkBedHghtMeas;

    @JsonProperty("car_trk_bed_lngh_meas")
    private Double carTrkBedLnghMeas;

    @JsonProperty("car_cls_of_crn_exst_cd")
    private String carClsOfCrnExstCd;

    @JsonProperty("car_cls_of_rfg_exst_cd")
    private String carClsOfRfgExstCd;

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("car_rmk_about_eqpm_txt")
    private String carRmkAboutEqpmTxt;

    @JsonProperty("car_vol_of_hzd_item_meas")
    private Double carVolOfHzdItemMeas;

    @JsonProperty("car_trk_bed_grnd_hght_meas")
    private Double carTrkBedGrndHghtMeas;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("car_cls_of_tlg_lftr_exst_cd")
    private String carClsOfTlgLftrExstCd;

    @JsonProperty("car_max_load_capacity1_meas")
    private Double carMaxLoadCapacity1Meas;

    @JsonProperty("car_max_load_capacity2_meas")
    private Double carMaxLoadCapacity2Meas;

    @JsonProperty("car_cls_of_wing_body_exst_cd")
    private String carClsOfWingBodyExstCd;

    @JsonProperty("car_spc_grv_of_hzd_item_meas")
    private Double carSpcGrvOfHzdItemMeas;

    @JsonProperty("car_cmpn_name_of_gtp_crtf_exst_txt")
    private String carCmpnNameOfGtpCrftExstTxt;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("order_status")
    private Integer orderStatus;

}
