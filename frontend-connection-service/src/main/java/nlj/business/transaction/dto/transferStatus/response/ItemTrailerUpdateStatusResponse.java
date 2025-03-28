package nlj.business.transaction.dto.transferStatus.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * トレイラ更新ステータスレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ItemTrailerUpdateStatusResponse {

    @JsonProperty("status")
    private String status;
}
