package nlj.business.carrier.dto.vehicleDiagramItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 輸送注文検索レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransOrderSearchResponse {

    @JsonProperty("data")
    private String data;
}
