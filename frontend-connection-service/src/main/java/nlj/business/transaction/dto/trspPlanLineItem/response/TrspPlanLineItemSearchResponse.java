package nlj.business.transaction.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.transaction.dto.trspPlanLineItem.TrspPlanLineItemDTO;


/**
 * <PRE>
 * 輸送計画明細検索応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrspPlanLineItemSearchResponse {

    @JsonProperty("trsp_plan_line_item")
    private List<TrspPlanLineItemDTO> trspPlanLineItem;
}
