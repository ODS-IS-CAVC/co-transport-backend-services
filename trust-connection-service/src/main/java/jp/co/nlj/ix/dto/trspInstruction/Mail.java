package jp.co.nlj.ix.dto.trspInstruction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * メールDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class Mail {

    @JsonProperty("to")
    private String to;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("content")
    private String content;
}
