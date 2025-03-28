package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション計画マッチングリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlanMatchingRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String id;
}
