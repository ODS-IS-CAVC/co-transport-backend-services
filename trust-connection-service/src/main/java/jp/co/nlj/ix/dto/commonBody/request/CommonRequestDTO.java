package jp.co.nlj.ix.dto.commonBody.request;

import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 共通リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CommonRequestDTO {

    @ValidateField(notNull = true)
    private String dataModelType;
    @ValidateField(notNull = true)
    private Object attribute;
}
