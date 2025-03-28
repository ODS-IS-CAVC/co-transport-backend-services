package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送能力属性DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {

    @JsonProperty("msg_info")
    @ValidateField
    private MessageInfoDTO messageInfoDTO = null;

    @JsonProperty("trsp_ability_line_item")
    private Set<TransportAbilityLineItemDTO> transportAbilityLineItemDTOS = new HashSet<>();
}
