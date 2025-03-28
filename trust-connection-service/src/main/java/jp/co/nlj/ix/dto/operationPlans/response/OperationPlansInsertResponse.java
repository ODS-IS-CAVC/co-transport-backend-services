package jp.co.nlj.ix.dto.operationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationPlans.OperationPlansDTO;
import lombok.Data;

/**
 * <PRE>
 * 運送計画挿入レスポンスDTO。<BR>
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
