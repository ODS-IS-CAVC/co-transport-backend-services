package nlj.business.transaction.dto.transferStatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * トレイラ更新ステータスリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ItemTrailerUpdateStatusRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private Integer status;
}
