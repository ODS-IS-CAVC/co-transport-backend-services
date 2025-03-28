package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 図形アイテムステータス更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DiagramItemUpdateStatusRequest {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private Integer status;
}
