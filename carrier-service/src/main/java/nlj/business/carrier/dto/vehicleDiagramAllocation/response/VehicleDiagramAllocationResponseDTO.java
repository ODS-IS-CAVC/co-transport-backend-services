package nlj.business.carrier.dto.vehicleDiagramAllocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両ダイアグラム割り当てレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramAllocationResponseDTO {

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("vehicle_info_id")
    private Long vehicleInfoId;

    @JsonProperty("vehicle_type")
    private Integer vehicleType;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("assign_type")
    private Integer assignType;

}
