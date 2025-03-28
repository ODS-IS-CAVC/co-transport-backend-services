package nlj.business.carrier.dto.semiDynamicInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * セミダイナミック情報 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemiDynamicInfoResponseDTO {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
