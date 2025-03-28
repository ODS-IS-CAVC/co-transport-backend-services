package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション承認リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
public class TransactionShipperApprovalRequest {

    @JsonProperty("id")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String pathId;

    @JsonProperty("vehicle_avb_resource_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceId;

    @ValidateField(notNull = true)
    private Boolean approval;

    private Boolean isNotIX;
}
