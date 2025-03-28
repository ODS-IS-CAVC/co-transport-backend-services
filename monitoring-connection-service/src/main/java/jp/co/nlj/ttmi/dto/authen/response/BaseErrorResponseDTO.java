package jp.co.nlj.ttmi.dto.authen.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 基底エラーレスポンスDTO。<BR>
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
