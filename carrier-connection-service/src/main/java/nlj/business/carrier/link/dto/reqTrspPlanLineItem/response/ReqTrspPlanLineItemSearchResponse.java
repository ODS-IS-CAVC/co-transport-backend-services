package nlj.business.carrier.link.dto.reqTrspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.MsgInfoDTO;

/**
 * <PRE>
 * 要求輸送計画明細検索応答.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReqTrspPlanLineItemSearchResponse {

    @JsonProperty("msg_info")
    private MsgInfoDTO msgInfo = null;

    @JsonProperty("trsp_plan_line_item")
    private List<ReqTrspPlanLineItemDTO> reqTrspPlanLineItem = new ArrayList<>();
}
