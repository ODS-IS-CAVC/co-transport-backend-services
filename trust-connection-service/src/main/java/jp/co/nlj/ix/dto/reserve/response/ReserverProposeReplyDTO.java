package jp.co.nlj.ix.dto.reserve.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 予約提案応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserverProposeReplyDTO {

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("approval")
    private Boolean approval;
}
