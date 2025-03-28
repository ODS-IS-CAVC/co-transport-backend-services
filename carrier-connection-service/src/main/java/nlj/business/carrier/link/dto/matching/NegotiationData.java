package nlj.business.carrier.link.dto.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * マッチングパラメータDTO.<BR>
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
