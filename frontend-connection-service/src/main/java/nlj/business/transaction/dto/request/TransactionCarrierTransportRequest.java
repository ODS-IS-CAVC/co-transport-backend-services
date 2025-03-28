package nlj.business.transaction.dto.request;

import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション運送業者リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransactionCarrierTransportRequest {

    @ValidateField(notNull = true)
    private String status;
}
