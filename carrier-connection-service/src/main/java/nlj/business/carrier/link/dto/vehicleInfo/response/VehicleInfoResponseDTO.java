package nlj.business.carrier.link.dto.vehicleInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import nlj.business.carrier.link.dto.vehicleInfo.HazardousMaterialInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MaxCarryingCapacityDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MotasInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleDetailsDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleInfoDTO;

/**
 * <PRE>
 * 車両情報レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleInfoResponseDTO {

    @JsonProperty("vehicle_info")
    private VehicleInfoDTO vehicleInfo = null;

    @JsonProperty("motas_info")
    private MotasInfoDTO motasInfo = null;

    @JsonProperty("vehicle_details")
    private VehicleDetailsDTO vehicleDetails = null;

    @JsonProperty("max_carrying_capacity")
    private List<MaxCarryingCapacityDTO> maxCarryingCapacity = new ArrayList<>();

    @JsonProperty("hazardous_material_info")
    private List<HazardousMaterialInfoDTO> hazardousMaterialInfo = new ArrayList<>();
}
