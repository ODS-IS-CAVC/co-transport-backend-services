package jp.co.nlj.verify.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * トークン検証レスポンスDTOクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyTokenResponseDTO extends BaseErrorResponseDTO {

    @JsonProperty("operatorId")
    private String operatorId;

    @JsonProperty("active")
    private boolean active;
}
