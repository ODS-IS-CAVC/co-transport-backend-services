package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者操作承認リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperationApprovalRequestDTO {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private boolean approval;

    @JsonProperty("propose_id")
    private String proposeId;
}