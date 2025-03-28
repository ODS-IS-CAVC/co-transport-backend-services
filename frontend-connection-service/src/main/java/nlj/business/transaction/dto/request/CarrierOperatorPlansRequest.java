package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

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
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("service_no")
    @ValidateField(maxLength = 20)
    private String serviceNo;

    @JsonProperty("trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = "HHmm")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = "HHmm")
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("giai_number")
    @ValidateField
    private String giaiNumber;

    @JsonProperty("req_freight_rate")
    @ValidateField(precision = 10, scale = 0)
    private BigDecimal reqFreightRate;

    @JsonProperty("propose_id")
    private String proposeId;

    @JsonProperty("matching_id")
    private String matchingId;

    @JsonProperty("trsp_op_trailer_id")
    private String trspOpTrailerId; // システム内部のトレーラID

    @JsonProperty("negotiation")
    private CarrierNegotiationDTO negotiation;
}
