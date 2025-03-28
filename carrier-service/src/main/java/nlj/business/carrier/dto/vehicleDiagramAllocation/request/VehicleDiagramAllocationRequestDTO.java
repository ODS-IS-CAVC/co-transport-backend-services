package nlj.business.carrier.dto.vehicleDiagramAllocation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両ダイアグラム割り当てリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramAllocationRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("vehicle_info_id")
    @ValidateField(notNull = true)
    private Long vehicleInfoId;

    @JsonProperty("vehicle_type")
    @ValidateField(notNull = true, vehicleType = true)
    private Integer vehicleType;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("assign_type")
    @ValidateField(assignType = true)
    private Integer assignType;

}
