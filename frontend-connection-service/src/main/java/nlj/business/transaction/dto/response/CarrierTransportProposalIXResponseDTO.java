package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送業者運送提案IXレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierTransportProposalIXResponseDTO {

    //for IX-121 response
    @JsonProperty("propose_id")
    private BigInteger proposeId;

    //for IX-021 response
    @JsonProperty("reserve")
    private IXReserveDTO reserveDTO;
}
