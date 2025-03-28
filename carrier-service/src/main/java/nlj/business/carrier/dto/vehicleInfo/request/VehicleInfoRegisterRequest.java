package nlj.business.carrier.dto.vehicleInfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.dto.vehicleInfo.VehicleInfoDTO;
import nlj.business.carrier.dto.vehicleInfo.VehicleNoAvailableDTO;

/**
 * <PRE>
 * 車両情報登録リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoRegisterRequest {

    @JsonProperty("vehicle_info")
    private VehicleInfoDTO vehicleInfo;
    @JsonProperty("vehicle_no_available")
    private List<VehicleNoAvailableDTO> vehicleNoAvailable;
}
