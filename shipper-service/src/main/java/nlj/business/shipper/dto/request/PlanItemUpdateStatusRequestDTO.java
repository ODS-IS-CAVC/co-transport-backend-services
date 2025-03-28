package nlj.business.shipper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 計画項目ステータス更新リクエストDTO。<BR>
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
