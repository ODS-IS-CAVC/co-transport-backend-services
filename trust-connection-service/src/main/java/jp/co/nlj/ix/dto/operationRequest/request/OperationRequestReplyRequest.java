package jp.co.nlj.ix.dto.operationRequest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * <PRE>
 * 運送計画承認リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestReplyRequest {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private Boolean approval;


}
