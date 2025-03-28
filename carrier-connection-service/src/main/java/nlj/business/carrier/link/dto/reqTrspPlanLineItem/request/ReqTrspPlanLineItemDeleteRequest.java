package nlj.business.carrier.link.dto.reqTrspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDeleteDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.MsgInfoDTO;

/**
 * <PRE>
 * リクエスト輸送計画品目登録リクエスト.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReqTrspPlanLineItemDeleteRequest {

    @JsonProperty("msg_info")
    private MsgInfoDTO msgInfo;
    @JsonProperty("trsp_plan_line_item")
    @ValidateField(notNull = true)
    private List<ReqTrspPlanLineItemDeleteDTO> reqTrspPlanLineItem;
}
