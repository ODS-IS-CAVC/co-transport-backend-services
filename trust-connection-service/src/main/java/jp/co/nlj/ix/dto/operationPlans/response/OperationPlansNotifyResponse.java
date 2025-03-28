package jp.co.nlj.ix.dto.operationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationPlans.OperationPlansNotifyDTO;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知レスポンスDTO。<BR>
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
