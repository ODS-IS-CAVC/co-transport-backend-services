package jp.co.nlj.ix.dto.operationRequest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationRequest.OperationRequestReplyDTO;
import lombok.Data;


/**
 * <PRE>
 * 運送計画承認レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestReplyResponse {

    @JsonProperty("operation_request_reply")
    private OperationRequestReplyDTO operationRequestReplyDTO;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;


}
