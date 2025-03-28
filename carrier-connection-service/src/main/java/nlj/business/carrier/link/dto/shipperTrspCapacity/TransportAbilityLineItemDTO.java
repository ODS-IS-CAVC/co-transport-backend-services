package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 輸送能力ラインアイテム dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityLineItemDTO {

    @JsonProperty("road_carr")
    @ValidateField(notNull = true)
    private RoadCarrDTO roadCarrDTO;

    @JsonProperty("logs_srvc_prv")
    private LogisticsServiceProviderDTO logisticsServiceProviderDTO;

    @JsonProperty("car_info")
    @ValidateField(notNull = true)
    private Set<CarInfoDTO> carInfoDTOS;

    @JsonProperty("drv_info")
    private Set<DriverInformationDTO> driverInformationDTOS;
}
