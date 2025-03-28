package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 到着地DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipToPrtyDTO {

    @JsonProperty("ship_to_prty_brnc_off_id")
    private String shipToPrtyBrncOffId;
}
