package jp.co.nlj.ix.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspPlanLineItemDTO;
import lombok.Data;


/**
 * <PRE>
 * 輸送計画明細検索応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemSearchResponse {

    @JsonProperty("trsp_plan_line_item")
    private List<TrspPlanLineItemDTO> trspPlanLineItem = new ArrayList<>();
}
