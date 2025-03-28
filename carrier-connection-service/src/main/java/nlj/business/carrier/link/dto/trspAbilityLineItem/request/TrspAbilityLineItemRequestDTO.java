package nlj.business.carrier.link.dto.trspAbilityLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.dto.trspAbilityLineItem.CarInfo;
import nlj.business.carrier.link.dto.trspAbilityLineItem.RoadCarr;
import nlj.business.carrier.link.dto.trspAbilityLineItem.VehicleAvbResource;

/**
 * <PRE>
 * 運送能力ラインアイテムリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspAbilityLineItemRequestDTO {

    @JsonProperty("road_carr")
    private RoadCarr roadCarr;

    @JsonProperty("car_info")
    private CarInfo carInfo;

    @JsonProperty("vehicle_avb_resource")
    private VehicleAvbResource vehicleAvbResource;
}
