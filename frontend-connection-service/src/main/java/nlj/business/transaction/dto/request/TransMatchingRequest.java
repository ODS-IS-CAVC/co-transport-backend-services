package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.dto.NegotiationDTORequest;

/**
 * <PRE>
 * 運送業者トランザクションマッチングリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransMatchingRequest {

    @JsonProperty("id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String id;

    @JsonProperty("negotiation")
    private NegotiationDTORequest negotiationDTORequest;

    @JsonProperty("shipper_code")
    private String shipperCode;

    @JsonProperty("carrier_code")
    private String carrierCode;

    private Boolean isNotIX;

    private String cid;
}
