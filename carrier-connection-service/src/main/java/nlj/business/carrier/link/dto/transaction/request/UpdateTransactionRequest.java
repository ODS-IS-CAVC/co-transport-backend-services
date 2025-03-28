package nlj.business.carrier.link.dto.transaction.request;

import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送依頼更新リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateTransactionRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String shipperId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String carrierId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String status;
}
