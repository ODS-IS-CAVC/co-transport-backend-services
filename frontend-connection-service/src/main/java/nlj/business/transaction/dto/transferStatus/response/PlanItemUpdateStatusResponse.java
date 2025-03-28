package nlj.business.transaction.dto.transferStatus.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * プラン項目更新ステータスレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class PlanItemUpdateStatusResponse {

    @JsonProperty("status")
    private String status;
}
