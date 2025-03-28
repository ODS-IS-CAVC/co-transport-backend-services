package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.ParamConstant;

/**
 * <PRE>
 * 運送業者リスト検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierListOrderSearch {

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String page = ParamConstant.DEFAULT_PAGE_NO;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String limit = ParamConstant.DEFAULT_PAGE_LIMIT;
}
