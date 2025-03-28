package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 属性DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {

    @JsonProperty("trsp_ability_line_item")
    private List<TransportAbilityLineItemDTO> transportAbilityLineItemDTOS;
}
