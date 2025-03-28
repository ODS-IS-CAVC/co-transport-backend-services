package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者トランザクションIDリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransactionIdRequest {

    @JsonProperty("last_update")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String last_update;
}
