package jp.co.nlj.ix.dto.shipperOperationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 荷主向け運行申し込み諾否回答通知レスポンスDTO
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperationApprovalResponseDTO {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private Boolean approval;

    @JsonProperty("propose_id")
    private String proposeId;
}
