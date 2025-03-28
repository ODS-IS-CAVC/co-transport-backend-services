package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 危険物情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class HazardousMaterialInfoDTO {

    @JsonProperty("hazardous_material_item_code")
    @ValidateField(maxLength = 2, textHalfWidth = true)
    private String hazardousMaterialItemCode;

    @JsonProperty("hazardous_material_text_info")
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String hazardousMaterialTextInfo;
}
