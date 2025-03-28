package jp.co.nlj.ix.dto.reserve.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.reserve.ReserveProposeNotifyResDTO;
import lombok.Data;

/**
 * <PRE>
 * 予約提案通知応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserveProposeNotifyDTO {

    @JsonProperty("reserve")
    private ReserveProposeNotifyResDTO reserve;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
