package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * シッパー注文ID売上リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
public class ShipperOrderIdSaleRequest {

    @ValidateField(notNull = true)
    private String vehicleAvbResourceId;
}
