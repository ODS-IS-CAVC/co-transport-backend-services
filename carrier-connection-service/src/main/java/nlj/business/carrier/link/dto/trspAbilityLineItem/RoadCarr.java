package nlj.business.carrier.link.dto.trspAbilityLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送能力ラインアイテムリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class RoadCarr {

    @JsonProperty("trsp_cli_prty_head_off_id")
    @ValidateField(length = "13")
    private String trspCliPrtyHeadOffId;

    @JsonProperty("trsp_cli_prty_brnc_off_id")
    @ValidateField(length = "17")
    private String trspCliPrtyBrncOffId;
}
