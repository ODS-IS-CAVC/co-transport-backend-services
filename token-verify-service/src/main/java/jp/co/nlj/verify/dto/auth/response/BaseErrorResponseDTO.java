package jp.co.nlj.verify.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 基底エラーレスポンスDTOクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseErrorResponseDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("detail")
    private String detail;
}
