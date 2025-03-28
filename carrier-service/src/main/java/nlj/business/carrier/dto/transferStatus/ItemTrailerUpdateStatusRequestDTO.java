package nlj.business.carrier.dto.transferStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * アイテムトレーラー更新ステータスリクエストDTO.<BR>
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
