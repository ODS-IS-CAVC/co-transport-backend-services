package nlj.business.carrier.dto.vehicleInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両情報レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoResponse {

    @JsonProperty("vehicle_info")
    private VehicleInfoResponseDTO vehicleInfo;
    @JsonProperty("vehicle_no_available")
    private List<VehicleNoAvailableResponseDTO> vehicleNoAvailable;
}
