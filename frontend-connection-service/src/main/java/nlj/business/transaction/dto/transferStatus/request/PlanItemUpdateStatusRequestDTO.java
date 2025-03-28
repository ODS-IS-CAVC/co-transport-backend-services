package nlj.business.transaction.dto.transferStatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * プラン項目更新ステータスリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class PlanItemUpdateStatusRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private Integer status;
}
