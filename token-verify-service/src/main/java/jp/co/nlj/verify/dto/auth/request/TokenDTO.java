package jp.co.nlj.verify.dto.auth.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * トークンDTOクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {

    @JsonProperty("refreshToken")
    private String refreshToken;
    @JsonProperty("idToken")
    private String idToken;
}
