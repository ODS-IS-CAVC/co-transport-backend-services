package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送計画承認DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestReplyDTO {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private Boolean approval;
    @JsonProperty("from_cid")
    private String fromCid;
    @JsonProperty("to_cid")
    private String toCid;
}
