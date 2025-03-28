package nlj.business.transaction.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.response.OperationRequestListWrapper;

/**
 * <PRE>
 * 運送計画品目検索レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestSearchResponse {

    @JsonProperty("operation_request_list")
    private OperationRequestListWrapper operationRequestResponses;
}
