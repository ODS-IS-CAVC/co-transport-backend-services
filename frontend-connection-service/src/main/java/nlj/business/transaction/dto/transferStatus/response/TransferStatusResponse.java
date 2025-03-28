package nlj.business.transaction.dto.transferStatus.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 転送ステータスレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransferStatusResponse {

    @JsonProperty("trailer_update_status")
    private String trailerUpdateStatus;
    @JsonProperty("plan_item_update_status")
    private String planItemUpdateStatus;
}
