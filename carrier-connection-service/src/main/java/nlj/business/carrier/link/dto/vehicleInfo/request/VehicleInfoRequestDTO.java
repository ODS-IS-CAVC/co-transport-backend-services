package nlj.business.carrier.link.dto.vehicleInfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.dto.vehicleInfo.HazardousMaterialInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MaxCarryingCapacityDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MotasInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleDetailsDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleInfoDTO;

/**
 * <PRE>
 * 車両情報リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleInfoRequestDTO {

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
