package jp.co.nlj.ix.dto.reserve.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.reserve.ReserveResDTO;
import lombok.Data;

/**
 * <PRE>
 * 予約応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserveResponseDTO {

    @JsonProperty("reserve")
    private ReserveResDTO reserve;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
