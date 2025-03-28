package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクションシッパー承認DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierTransactionShipperApproval {

    @JsonProperty("cns_line_item_id")
    @ValidateField(notNull = false)
    private String cnsLineItemId;

    @JsonProperty("vehicle_avb_resource_id")
    @ValidateField(notNull = false)
    private String vehicleAvbResourceId;

    @JsonProperty("approval")
    @ValidateField(notNull = true)
    private boolean approval;

    @JsonProperty("isNotIX")
    private boolean isNotIX;
}
