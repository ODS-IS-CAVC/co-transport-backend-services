package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車情報 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarInfoDTO {

    @JsonProperty("vehicle_avb_resource")
    @ValidateField
    private Set<VehicleAvailabilityResourceDTO> vehicleAvailabilityResourceDTOS;

    @JsonProperty("service_strt_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "serviceEndDate", startTimeField = "serviceStrtTime", endTimeField = "serviceEndTime", textHalfWidth = true)
    private String serviceStrtDate;

    @JsonProperty("service_strt_time")
    @ValidateField(notNull = true, timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String serviceStrtTime;

    @JsonProperty("service_end_date")
    @ValidateField(notNull = false, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String serviceEndDate;

    @JsonProperty("service_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String serviceEndTime;

    @JsonProperty("freight_rate")
    @ValidateField(notNull = true, precision = 10, scale = 0, textHalfWidth = true)
    private String freightRate;

    @JsonProperty("giai")
    @ValidateField(notNull = true, maxLength = 50, textHalfWidth = true)
    private String giai;

    @JsonProperty("service_no")
    @ValidateField(notNull = true, maxLength = 20, textHalfWidth = true)
    private String serviceNo;

    @JsonProperty("service_name")
    @ValidateField(notNull = true, maxLength = 24, textFullWidth = true)
    private String serviceName;

    @JsonProperty("car_ctrl_num_id")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String carCtrlNumId;

    @JsonProperty("car_license_plt_num_id")
    @ValidateField(maxLength = 24, textFullWidth = true)
    private String carLicensePltNumId;

    @JsonProperty("car_body_num_cd")
    @ValidateField(maxLength = 42, textHalfWidth = true)
    private String carBodyNumCd;

    @JsonProperty("car_cls_of_size_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vehicleType = true)
    private String carClsOfSizeCd;

    @JsonProperty("tractor_idcr")
    @ValidateField(maxLength = 1, textHalfWidth = true, tractorType = true)
    private String tractorIdcr;

    @JsonProperty("trailer_license_plt_num_id")
    @ValidateField(maxLength = 24, textFullWidth = true)
    private String trailerLicensePltNumId;

    @JsonProperty("car_weig_meas")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String carWeigMeas;

    @JsonProperty("car_lngh_meas")
    @ValidateField(notNull = true, precision = 10, scale = 0, textHalfWidth = true)
    private String carLnghMeas;

    @JsonProperty("car_wid_meas")
    @ValidateField(notNull = true, precision = 10, scale = 0, textHalfWidth = true)
    private String carWidMeas;

    @JsonProperty("car_hght_meas")
    @ValidateField(notNull = true, precision = 10, scale = 0, textHalfWidth = true)
    private String carHghtMeas;

    @JsonProperty("car_max_load_capacity1_meas")
    @ValidateField(notNull = true, precision = 10, scale = 0, textHalfWidth = true)
    private String carMaxLoadCapacity1Meas;

    @JsonProperty("car_max_load_capacity2_meas")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String carMaxLoadCapacity2Meas;

    @JsonProperty("car_vol_of_hzd_item_meas")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String carVolOfHzdItemMeas;

    @JsonProperty("car_spc_grv_of_hzd_item_meas")
    @ValidateField(precision = 10, scale = 3, textHalfWidth = true)
    private String carSpcGrvOfHzdItemMeas;

    @JsonProperty("car_trk_bed_hght_meas")
    @ValidateField(precision = 15, scale = 0, textHalfWidth = true)
    private String carTrkBedHghtMeas;

    @JsonProperty("car_trk_bed_wid_meas")
    @ValidateField(precision = 15, scale = 0, textHalfWidth = true)
    private String carTrkBedWidMeas;

    @JsonProperty("car_trk_bed_lngh_meas")
    @ValidateField(precision = 15, scale = 0, textHalfWidth = true)
    private String carTrkBedLnghMeas;

    @JsonProperty("car_trk_bed_grnd_hght_meas")
    @ValidateField(precision = 15, scale = 0, textHalfWidth = true)
    private String carTrkBedGrndHghtMeas;

    @JsonProperty("car_max_load_vol_meas")
    @ValidateField(precision = 11, scale = 4, textHalfWidth = true)
    private String carMaxLoadVolMeas;

    @JsonProperty("pcke_frm_cd")
    @ValidateField(maxLength = 3, textHalfWidth = true, packingCode = true)
    private String pckeFrmCd;

    @JsonProperty("pcke_frm_name_cd")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String pckeFrmNameCd;

    @JsonProperty("car_max_untl_cp_quan")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String carMaxUntlCpQuan;

    @JsonProperty("car_cls_of_shp_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vanBodyType = true)
    private String carClsOfShpCd;

    @JsonProperty("car_cls_of_tlg_lftr_exst_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vanBodyType = true)
    private String carClsOfTlgLftrExstCd;

    @JsonProperty("car_cls_of_wing_body_exst_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vanBodyType = true)
    private String carClsOfWingBodyExstCd;

    @JsonProperty("car_cls_of_rfg_exst_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vanBodyType = true)
    private String carClsOfRfgExstCd;

    @JsonProperty("trms_of_lwr_tmp_meas")
    @ValidateField(precision = 10, scale = 2, textHalfWidth = true, positiveNumber = false, upperTemperatureField = "trmsOfUppTmpMeas")
    private String trmsOfLwrTmpMeas;

    @JsonProperty("trms_of_upp_tmp_meas")
    @ValidateField(precision = 10, scale = 2, textHalfWidth = true, positiveNumber = false)
    private String trmsOfUppTmpMeas;

    @JsonProperty("car_cls_of_crn_exst_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, vanBodyType = true)
    private String carClsOfCrnExstCd;

    @JsonProperty("car_rmk_about_eqpm_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String carRmkAboutEqpmTxt;

    @JsonProperty("car_cmpn_name_of_gtp_crtf_exst_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String carCmpnNameOfGtpCrfExstTxt;
}
