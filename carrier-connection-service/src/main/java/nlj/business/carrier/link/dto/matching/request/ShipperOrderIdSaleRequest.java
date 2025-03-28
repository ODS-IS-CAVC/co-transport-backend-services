package nlj.business.carrier.link.dto.matching.request;

import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 出荷者注文ID売上リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperOrderIdSaleRequest {

    @ValidateField(notNull = true)
    private String vehicleAvbResourceId;
}
