package jp.co.nlj.ix.dto.reqTrspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.MsgInfoDTO;
import lombok.Data;

/**
 * <PRE>
 * 要求輸送計画明細検索応答。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReqTrspPlanLineItemSearchResponse {

    @JsonProperty("msg_info")
    private MsgInfoDTO msgInfo = new MsgInfoDTO();

    @JsonProperty("trsp_plan_line_item")
    private List<ReqTrspPlanLineItemDTO> reqTrspPlanLineItem = new ArrayList<>();
}
