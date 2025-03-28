package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション2リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCarrier2Request {

    @JsonProperty("cns_line_item_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String cnsLineItemId;

    @JsonProperty("cns_line_item_by_date_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String cnsLineItemByDateId;

    @JsonProperty("vehicle_avb_resource_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceId;

    @JsonProperty("vehicle_avb_resource_item_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceItemId;

    @JsonProperty("negotiation")
    @ValidateField(notNull = true)
    private ShipperNegotiationDTO negotiationDTO;
}
