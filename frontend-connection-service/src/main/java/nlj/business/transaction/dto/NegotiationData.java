package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 交渉データDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NegotiationData {

    private ShipperNegotiation shipper;
    private CarrierNegotiation carrier;
}
