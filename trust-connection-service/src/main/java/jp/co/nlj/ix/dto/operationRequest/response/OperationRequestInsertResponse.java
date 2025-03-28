package jp.co.nlj.ix.dto.operationRequest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationRequest.OperationRequestInsertDTO;
import lombok.Data;

/**
 * <PRE>
 * 運送計画挿入レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestInsertResponse {

    @JsonProperty("operation_request")
    private OperationRequestInsertDTO operationRequestInsertDTO;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;
}
