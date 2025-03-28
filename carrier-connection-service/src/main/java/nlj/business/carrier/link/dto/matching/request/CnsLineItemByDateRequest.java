package nlj.business.carrier.link.dto.matching.request;

import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 日付別行単位リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CnsLineItemByDateRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String id;

    private String orderId;

    private String vehicleAvbResourceItemId;
}
