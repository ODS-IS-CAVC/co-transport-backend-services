package nlj.business.transaction.dto.operationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.operationPlans.OperationPlansDTO;

/**
 * <PRE>
 * 操作プラン挿入レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansInsertResponse {

    @JsonProperty("operation_plans")
    private OperationPlansDTO operationPlans;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;
}
