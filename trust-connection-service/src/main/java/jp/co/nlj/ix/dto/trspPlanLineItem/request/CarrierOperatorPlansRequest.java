package jp.co.nlj.ix.dto.trspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Data;


/**
 * <PRE>
 * キャリアオペレータープランリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperatorPlansRequest {

    @JsonProperty("trsp_plan_id")
    //@ValidateField(notNull = true)
    private String trspPlanId;

    @JsonProperty("operation_id")
    //@ValidateField(notNull = true)
    private String operationId;

    @JsonProperty("service_no")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String serviceNo;

    @JsonProperty("trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "trspOpDateTrmEndDate")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("trsp_op_trailer_id")
    @ValidateField
    private String trspOpTrailerId;

    @JsonProperty("giai_number")
    @ValidateField
    private String giaiNumber;

    @JsonProperty("req_freight_rate")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private BigDecimal reqFreightRate;
}
