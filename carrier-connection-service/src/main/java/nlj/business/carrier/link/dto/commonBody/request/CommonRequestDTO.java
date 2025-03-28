package nlj.business.carrier.link.dto.commonBody.request;

import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 共通リクエストDTO.<BR>
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
