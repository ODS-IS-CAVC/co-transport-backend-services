package jp.co.nlj.gateway.dto.reservation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * Statusesクラスは、ステータスを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statuses {

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @JsonProperty("validFrom")
    private String validFrom;

    @JsonProperty("validUntil")
    private String validUntil;
}
