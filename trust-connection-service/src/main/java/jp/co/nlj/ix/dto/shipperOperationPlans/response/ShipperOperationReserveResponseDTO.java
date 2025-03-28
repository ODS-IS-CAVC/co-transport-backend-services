package jp.co.nlj.ix.dto.shipperOperationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 荷主向け運行申し込みレスポンスDTO
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperOperationReserveResponseDTO {

    @JsonProperty("propose_id")
    private String proposeId;
}

