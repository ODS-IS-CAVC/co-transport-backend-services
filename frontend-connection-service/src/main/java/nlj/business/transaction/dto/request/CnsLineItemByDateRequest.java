package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * コンテナラインアイテム日付リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
public class CnsLineItemByDateRequest {

    @ValidateField(notNull = true)
    private String id;

    private String orderId;

    private String vehicleAvbResourceItemId;
}
