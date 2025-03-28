package nlj.business.carrier.dto.semiDynamicInfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * セミダイナミック情報リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class SemiDynamicInfoRequestDTO {

    @JsonProperty("status")
    private String status;

    @JsonProperty("vehicle_diagram_item_id")
    @ValidateField(notNull = true)
    private String vehicleDiagramItemId;

}