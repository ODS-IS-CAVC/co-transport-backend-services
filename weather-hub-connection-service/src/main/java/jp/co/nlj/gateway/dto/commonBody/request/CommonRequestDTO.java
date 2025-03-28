package jp.co.nlj.gateway.dto.commonBody.request;

import jp.co.nlj.gateway.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * CommonRequestDTOクラスは、共通リクエストDTOを定義するためのクラスです。<BR>
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
