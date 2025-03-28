package jp.co.nlj.ix.dto.operationRequest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationPlans.request.RoadCarrNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CneePrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsLineItemNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsgPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.DelInfoNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.FretClimToPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.LogsSrvcPrvNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.OperationRequestNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.ShipFromPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.ShipToPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspIsrNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspRqrPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspSrvcNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspVehicleTrmsNotifyDTO;
import lombok.Data;


/**
 * <PRE>
 * 運送計画通知レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestNotifyResponse {

    @JsonProperty("operation_request")
    private OperationRequestNotifyDTO operationRequestNotifyDTO;
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("error_msg")
    private String errorMsg;


}
