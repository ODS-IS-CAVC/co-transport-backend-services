package jp.co.nlj.ix.dto.shipperOperationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.Data;

/**
 * <PRE>
 * 荷主向け運行申し込み更新リクエストDTO
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperOperationUpdateRequestDTO {

    @JsonProperty("trsp_plan_id")
    //@ValidateField(notNull = true)
    private String trspPlanId; // 内部の運送能力管理ID

    @JsonProperty("operation_id")
    //@ValidateField(notNull = true)
    private String operationId; // システム内部の運送能力管理ID

    @JsonProperty("service_no")
    @ValidateField(textHalfWidth = true)
    private String serviceNo; // 便・ダイヤ番号

    @JsonProperty("trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true, endDateField = "trspOpDateTrmStrtDate", startTimeField = "reqAvbFromTimeOfCllTime", endTimeField = "reqAvbToTimeOfCllTime")
    private String trspOpDateTrmStrtDate; // 運行開始日

    @JsonProperty("trsp_op_trailer_id")
    @ValidateField(inputIsArrOrBoolean = true)
    private String trspOpTrailerId; // システム内部のトレーラID

    @JsonProperty("giai_number")
    @ValidateField(inputIsArrOrBoolean = true)
    private String giaiNumber; // GIAI番号

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String trspOpPlanDateTrmStrtTime; // 運行開始予定時刻

    @JsonProperty("req_avb_from_time_of_cll_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String reqAvbFromTimeOfCllTime; // 申し込み集荷開始時間

    @JsonProperty("req_avb_to_time_of_cll_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String reqAvbToTimeOfCllTime; // 申し込み集荷終了時間

    @JsonProperty("req_freight_rate")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private BigDecimal reqFreightRate;  // 申し込み希望運賃

}
