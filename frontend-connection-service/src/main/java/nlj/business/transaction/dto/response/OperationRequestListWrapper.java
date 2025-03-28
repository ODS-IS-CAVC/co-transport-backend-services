package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.transaction.dto.trspPlanLineItem.TrspPlanLineItemDTO;

/**
 * <PRE>
 * 運送業者プラン行項目リストラッパーDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestListWrapper {

    @JsonProperty("trsp_plan_line_item")
    private List<TrspPlanLineItemDTO> trspPlanLineItems;
}
