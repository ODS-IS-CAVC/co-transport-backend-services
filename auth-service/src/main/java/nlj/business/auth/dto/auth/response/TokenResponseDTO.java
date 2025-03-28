package nlj.business.auth.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * トークンレスポンスDTO<BR>
 * </PRE>
 * 
 * @author Next Logistics Japan
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponseDTO extends BaseErrorResponseDTO {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("detail")
    private String detail;
}
