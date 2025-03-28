package nlj.business.transaction.dto.ix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * IX操作リクエスト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IXOperationRequest {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private String approval;

    @JsonProperty("from_cid")
    private String fromCid;

    @JsonProperty("to_cid")
    private String toCid;

    @JsonProperty("propose_id")
    private String proposeId;
}
