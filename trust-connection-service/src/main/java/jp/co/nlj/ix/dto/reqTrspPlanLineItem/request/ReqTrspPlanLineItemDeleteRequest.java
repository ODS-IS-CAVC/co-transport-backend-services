package jp.co.nlj.ix.dto.reqTrspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDeleteDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.MsgInfoDTO;
import lombok.Data;

/**
 * <PRE>
 * リクエスト輸送計画品目削除リクエスト。<BR>
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
