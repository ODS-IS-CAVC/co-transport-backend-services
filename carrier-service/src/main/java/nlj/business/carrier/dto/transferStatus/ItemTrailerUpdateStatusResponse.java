package nlj.business.carrier.dto.transferStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * アイテムトレーラー更新ステータスレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ItemTrailerUpdateStatusResponse {

    @JsonProperty("status")
    private String status;
}
