package nlj.business.transaction.dto.request;

import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション売上リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransactionOnSaleRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE)
    private Long id;
}
