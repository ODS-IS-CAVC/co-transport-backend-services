package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;

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
public class TransportAbilityPublicIXResponseDTO extends TransportAbilityPublicResponseDTO {

    @JsonProperty("propose_trsp_plan")
    private Object proposeTrspPlan;
}
