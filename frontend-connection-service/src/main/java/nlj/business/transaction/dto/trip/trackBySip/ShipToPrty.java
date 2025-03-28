package nlj.business.transaction.dto.trip.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 到着地プライマリDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipToPrty {

    @JsonProperty("ship_to_prty_brnc_off_id")
    @ValidateField(maxLength = 17)
    private String shipToPrtyBrncOffId;
}
