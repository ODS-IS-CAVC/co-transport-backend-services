package nlj.business.carrier.link.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.dto.trspPlanLineItem.MsgInfoDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanLineItemDTO;

/**
 * <PRE>
 * 輸送計画明細検索応答DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemSearchResponse {

    @JsonProperty("msg_info")
    private MsgInfoDTO msgInfo = null;
    @JsonProperty("trsp_plan")
    private TrspPlanDTO trspPlan = null;
    @JsonProperty("trsp_plan_line_item")
    private List<TrspPlanLineItemDTO> trspPlanLineItem = new ArrayList<>();
}
