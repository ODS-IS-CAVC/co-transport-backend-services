package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 車両情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleBodyDTO {

    @JsonProperty("vehicle_info")
    private VehicleInfoDTO vehicleInfo;

    @JsonProperty("motas_info")
    private MotasInfoDTO motasInfo;

    @JsonProperty("vehicle_details")
    private VehicleDetailsDTO vehicleDetails;

    @JsonProperty("max_carrying_capacity")
    private List<MaxCarryingCapacityDTO> maxCarryingCapacity;

    @JsonProperty("hazardous_material_info")
    private List<HazardousMaterialInfoDTO> hazardousMaterialInfo;
}
