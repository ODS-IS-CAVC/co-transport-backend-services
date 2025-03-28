package nlj.business.transaction.dto.request;

import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション市場検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransMarketSearchRequest {

    @ValidateField(dateFormat = "yyyyMM", notNull = true)
    private String month;
}
