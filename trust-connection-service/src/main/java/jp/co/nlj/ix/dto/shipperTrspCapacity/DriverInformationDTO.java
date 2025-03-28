package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * ドライバー情報 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DriverInformationDTO {

    @JsonProperty("drv_ctrl_num_id")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String drvCtrlNumId;

    @JsonProperty("drv_cls_of_drvg_license_cd")
    @ValidateField(maxLength = 15, textHalfWidth = true)
    private String drvClsOfDrvgLicenseCd;

    @JsonProperty("drv_cls_of_fkl_license_exst_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String drvClsOfFklLicenseExstCd;

    @JsonProperty("drv_rmk_about_drv_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String drvRmkAboutDrvTxt;

    @JsonProperty("drv_cmpn_name_of_gtp_crtf_exst_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String drvCmpnNameOfGtpCrtfExstTxt;

    @JsonProperty("drv_avb_time")
    @ValidateField
    private Set<DriverAvailabilityTimeDTO> driverAvailabilityTimeDTOS;
}
