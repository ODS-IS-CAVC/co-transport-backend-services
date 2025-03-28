package nlj.business.carrier.link.dto.trspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 輸送計画品目検索リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemSearchRequest {

    @JsonProperty("service_no")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String serviceNo; // 便・ダイヤ番号

    @JsonProperty("service_name")
    @ValidateField(maxLength = 24, textFullWidth = true)
    private String serviceName; // 便・ダイヤ名称

    @JsonProperty("car_max_load_capacity1_meas")
    @ValidateField(precision = 6, scale = 0, textHalfWidth = true)
    private BigDecimal carMaxLoadCapacity1Meas; // 最大積載量1

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String trspOpStrtAreaLineOneTxt; // 運行開始地域

    @JsonProperty("trsp_op_end_area_line_one_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String trspOpEndAreaLineOneTxt; // 運行終了地域

    @JsonProperty("max_trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String maxTrspOpDateTrmStrtDate; // 運行開始日最大値

    @JsonProperty("min_trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true, endDateField = "maxTrspOpDateTrmStrtDate")
    private String minTrspOpDateTrmStrtDate; // 運行開始日最小値

    @JsonProperty("max_trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String maxTrspOpDateTrmEndDate; // 運行終了日最大値

    @JsonProperty("min_trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true, endDateField = "maxTrspOpDateTrmEndDate")
    private String minTrspOpDateTrmEndDate; // 運行終了日最小値

    @JsonProperty("max_trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String maxTrspOpPlanDateTrmStrtTime; // 運行開始希望時刻最大値

    @JsonProperty("min_trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String minTrspOpPlanDateTrmStrtTime; // 運行開始希望時刻最小値

    @JsonProperty("max_trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String maxTrspOpPlanDateTrmEndTime; // 運行終了希望時刻最大値

    @JsonProperty("min_trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String minTrspOpPlanDateTrmEndTime; // 運行終了希望時刻最小値

}
