package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.ParamConstant;

/**
 * <PRE>
 * 共通ページング検索DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonPagingSearch {

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0, positiveText = true)
    private String page = ParamConstant.DEFAULT_PAGE_NO;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0, positiveText = true)
    private String limit = ParamConstant.DEFAULT_PAGE_LIMIT;
}
