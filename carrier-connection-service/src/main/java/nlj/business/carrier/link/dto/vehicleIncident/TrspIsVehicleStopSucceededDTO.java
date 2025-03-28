package nlj.business.carrier.link.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送車両停止成功DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspIsVehicleStopSucceededDTO {

    @JsonProperty("trsp_is_vehicle_stop_succeeded")
    @ValidateField(inputIsArrOrBoolean = false)
    private Boolean trspIsVehicleStopSucceeded;
}
