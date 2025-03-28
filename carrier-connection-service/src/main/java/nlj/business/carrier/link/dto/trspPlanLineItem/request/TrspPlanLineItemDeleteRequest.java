package nlj.business.carrier.link.dto.trspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.dto.trspPlanLineItem.MsgInfoDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanLineItemDeleteDTO;

/**
 * <PRE>
 * 輸送計画品目登録リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemDeleteRequest {

    @JsonProperty("msg_info")
    private MsgInfoDTO msgInfo;
    @JsonProperty("trsp_plan")
    private TrspPlanDTO trspPlan;
    @JsonProperty("trsp_plan_line_item")
    @ValidateField(notNull = true)
    private List<TrspPlanLineItemDeleteDTO> trspPlanLineItem;
}
