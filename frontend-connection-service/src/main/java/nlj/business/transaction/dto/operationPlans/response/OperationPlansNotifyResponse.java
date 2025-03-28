package nlj.business.transaction.dto.operationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.operationPlans.OperationPlansNotifyDTO;

/**
 * <PRE>
 * 操作プラン通知レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansNotifyResponse {

    @JsonProperty("operation_plan")
    private OperationPlansNotifyDTO operationPlansNotifyDTO;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;
}
