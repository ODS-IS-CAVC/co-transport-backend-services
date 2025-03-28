package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 注文緊急更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEmergencyUpdateRequest {

    @JsonProperty("id")
    @ValidateField(notNull = true)
    private List<Long> listId;

    @JsonProperty("remove")
    private boolean remove;
}
