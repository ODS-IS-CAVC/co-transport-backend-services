package jp.co.nlj.ix.dto.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 予約応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserveResDTO {

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;

    @JsonProperty("propose_id")
    private String proposeId;
}