package nlj.business.transaction.dto.shipperTrspCapacity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送業者運送能力行項目DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityLineItemDTO {

    @JsonProperty("car_info")
    private List<CarInfoDTO> carInfoDTOS;
}
