package nlj.business.carrier.link.dto.matching.request;

import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両可用性リソースアイテムリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvbResourceItemRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String id;
}
