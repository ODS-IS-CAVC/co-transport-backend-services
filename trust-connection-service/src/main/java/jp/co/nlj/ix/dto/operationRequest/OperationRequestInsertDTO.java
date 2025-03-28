package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送計画挿入DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestInsertDTO {

    @JsonProperty("from_cid")
    private String fromCid;
    @JsonProperty("to_cid")
    private String toCid;
    @JsonProperty("propose_id")
    private String proposeId;
}
