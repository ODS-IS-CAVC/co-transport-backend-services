package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者プラン挿入レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperatorPlansInsertResponse {

    @JsonProperty("propose_id")
    private String proposeId;
}
