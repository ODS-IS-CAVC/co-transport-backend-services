package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nlj.business.transaction.dto.TrspAbilityMatchingDetailDTO;

/**
 * <PRE>
 * マッチング能力詳細レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
public class TransMatchingAbilityDetailResponse {

    @JsonProperty("trailer")
    TransMatchingTrailerIdResponse trailer;

    @JsonProperty("matching_list")
    List<TrspAbilityMatchingDetailDTO> matching;
}
