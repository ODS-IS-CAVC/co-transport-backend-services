package nlj.business.transaction.dto.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.dto.shipperTrspCapacity.response.CarInfoDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.response.TransportAbilityLineItemDTO;

/**
 * <PRE>
 * IX発注者操作。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
public class IXShipperOperations {

    private String cid;

    @JsonProperty("trsp_ability_line_item")
    private List<TransportAbilityLineItemDTO> transportAbilityLineItemDTOS;

    @JsonProperty("car_info")
    private List<CarInfoDTO> carInfoDTOS;
}
