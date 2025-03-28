package nlj.business.shipper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 計画項目ステータス更新レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class PlanItemUpdateStatusResponse {

    @JsonProperty("status")
    private String status;
}
