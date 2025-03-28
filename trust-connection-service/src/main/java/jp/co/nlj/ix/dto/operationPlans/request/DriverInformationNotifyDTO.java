package jp.co.nlj.ix.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;

/**
 * <PRE>
 * ドライバー情報 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DriverInformationNotifyDTO {

    @JsonProperty("drv_ctrl_num_id")
    private String drvCtrlNumId;

    @JsonProperty("drv_cls_of_drvg_license_cd")
    private String drvClsOfDrvgLicenseCd;

    @JsonProperty("drv_cls_of_fkl_license_exst_cd")
    private String drvClsOfFklLicenseExstCd;

    @JsonProperty("drv_rmk_about_drv_txt")
    private String drvRmkAboutDrvTxt;

    @JsonProperty("drv_cmpn_name_of_gtp_crtf_exst_txt")
    private String drvCmpnNameOfGtpCrtfExstTxt;

    @JsonProperty("drv_avb_time")
    private Set<DriverAvailabilityTimeNotifyDTO> driverAvailabilityTimeDTOS;
}
