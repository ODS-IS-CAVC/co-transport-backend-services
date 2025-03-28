package nlj.business.carrier.link.dto.trspAbilityLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送能力ラインアイテムレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspAbilityLineItemResponseDTO {

    @JsonProperty("message")
    private String message;
}
