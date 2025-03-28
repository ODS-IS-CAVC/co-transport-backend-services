package jp.co.nlj.ix.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;


/**
 * <PRE>
 * キャリアオペレータープラン更新応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperatorPlansUpdateResponse {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("trsp_op_trailer_id")
    private String trspOpTrailerId;

    @JsonProperty("giai_number")
    private String giaiNumber;

    @JsonProperty("req_freight_rate")
    private BigDecimal reqFreightRate;

    @JsonProperty("propose_id")
    private String proposeId;
}
