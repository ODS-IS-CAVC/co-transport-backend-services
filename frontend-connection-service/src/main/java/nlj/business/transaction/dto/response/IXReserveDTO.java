package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * IX予約DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IXReserveDTO {

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;

    @JsonProperty("propose_id")
    private BigInteger proposeId;
}
