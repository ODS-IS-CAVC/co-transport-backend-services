package jp.co.nlj.ix.dto.reserve.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.reserve.ReserveProposeResDTO;
import lombok.Data;

/**
 * <PRE>
 * 予約提案応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserveProposeResponseDTO {

    @JsonProperty("reserve")
    private ReserveProposeResDTO reserve;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
