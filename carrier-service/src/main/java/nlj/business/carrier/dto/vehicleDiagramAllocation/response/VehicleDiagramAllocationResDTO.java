package nlj.business.carrier.dto.vehicleDiagramAllocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponseDTO;

/**
 * <PRE>
 * 車両ダイアグラム割り当てレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramAllocationResDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("vehicle_info")
    private VehicleInfoResponseDTO vehicleInfoResponseDTO;

    @JsonProperty("vehicle_type")
    private Integer vehicleType;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("assign_type")
    private Integer assignType;
}
