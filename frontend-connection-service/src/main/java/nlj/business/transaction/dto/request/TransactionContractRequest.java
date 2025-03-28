package nlj.business.transaction.dto.request;

import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション契約リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransactionContractRequest {

    @ValidateField(notNull = true)
    private boolean approval;

}
