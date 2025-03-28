package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 荷送人輸送能力登録 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipperTransportCapacityRegisterDTO {

    @ValidateField(notNull = true)
    private String dataModelType;

    @JsonProperty("attribute")
    @ValidateField(notNull = true)
    private AttributeDTO attributeDTO;
}
