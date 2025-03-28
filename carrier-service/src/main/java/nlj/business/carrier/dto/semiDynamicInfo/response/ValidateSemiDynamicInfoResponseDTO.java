package nlj.business.carrier.dto.semiDynamicInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * セミダイナミック情報の検証 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateSemiDynamicInfoResponseDTO {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
