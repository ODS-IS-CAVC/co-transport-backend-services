package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 出発地DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipFromPrtyDTO {

    @JsonProperty("ship_from_prty_brnc_off_id")
    private String shipFromPrtyBrncOffId;
}
