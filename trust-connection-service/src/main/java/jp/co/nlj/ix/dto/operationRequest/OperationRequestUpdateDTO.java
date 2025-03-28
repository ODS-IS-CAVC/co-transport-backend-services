package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 運送計画更新DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestUpdateDTO {

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
    @JsonProperty("from_cid")
    private String fromCid;
    @JsonProperty("to_cid")
    private String toCid;
}
