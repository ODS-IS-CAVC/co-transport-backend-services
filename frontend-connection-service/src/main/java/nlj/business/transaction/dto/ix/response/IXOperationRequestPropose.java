package nlj.business.transaction.dto.ix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * IX操作リクエスト提案。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IXOperationRequestPropose {

    @JsonProperty("operation_request")
    private IXOperationRequest ixOperationRequest;

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
