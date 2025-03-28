package jp.co.nlj.ix.dto.operationRequest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationRequest.OperationRequestInsertDTO;
import jp.co.nlj.ix.dto.operationRequest.OperationRequestUpdateDTO;
import lombok.Data;


/**
 * <PRE>
 * 運送計画更新レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestUpdateResponse {

    @JsonProperty("operation_request")
    private OperationRequestUpdateDTO operationRequestUpdateDTO;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;
}
