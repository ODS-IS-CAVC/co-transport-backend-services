package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.Data;

/**
 * <PRE>
 * ドライバー利用可能時間 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DriverAvailabilityTimeDTO {

    @JsonProperty("drv_avb_from_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "drvAvbToDate", startTimeField = "drvAvbFromTimeOfWrkgTime", endTimeField = "drvAvbToTimeOfWrkgTime", textHalfWidth = true)
    private String drvAvbFromDate;

    @JsonProperty("drv_avb_from_time_of_wrkg_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String drvAvbFromTimeOfWrkgTime;

    @JsonProperty("drv_avb_to_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String drvAvbToDate;

    @JsonProperty("drv_avb_to_time_of_wrkg_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String drvAvbToTimeOfWrkgTime;

    @JsonProperty("drv_wrkg_trms_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String drvWrkgTrmsTxt;

    @JsonProperty("drv_frmr_optg_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String drvFrmrOptgDate;

    @JsonProperty("drv_frmr_op_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String drvFrmrOpEndTime;
}
