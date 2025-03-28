package nlj.business.carrier.dto.semiDynamicInfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.ParamConstant;

/**
 * <PRE>
 * セミダイナミック情報の検証リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ValidateSemiDynamicInfoRequestDTO {

    @JsonProperty("vehicle_diagram_item_id")
    @ValidateField(notNull = true)
    private String vehicleDiagramItemId;

    @JsonProperty("type")
    @ValidateField(notNull = true, allowedEnumValues = {
        ParamConstant.ValidateSemiDynamicInfoType.TYPE_1,
        ParamConstant.ValidateSemiDynamicInfoType.TYPE_2,
        ParamConstant.ValidateSemiDynamicInfoType.TYPE_3,
        ParamConstant.ValidateSemiDynamicInfoType.TYPE_4
    })
    private String type;
}