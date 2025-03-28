package nlj.business.carrier.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * 出発地プロパティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipFromPrty {

    @JsonProperty("ship_from_prty_brnc_off_id")
    @ValidateField(maxLength = 17)
    private String shipFromPrtyBrncOffId;
}
