package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * マッチング間隔レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchingIntervalResponse {

    @JsonProperty("matching_ok")
    private int matchingOK;
    @JsonProperty("matching_not_good")
    private int matchingNG;
}
