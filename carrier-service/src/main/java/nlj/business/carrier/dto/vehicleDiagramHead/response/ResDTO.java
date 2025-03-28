package nlj.business.carrier.dto.vehicleDiagramHead.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両ダイアグラムヘッダレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ResDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private int code;

}
