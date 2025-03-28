package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 運送車両停止成功DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrspIsVehicleStopSucceededDTO {

    @JsonProperty("trsp_is_vehicle_stop_succeeded")
    @ValidateField(inputIsArrOrBoolean = false)
    private Boolean trspIsVehicleStopSucceeded;
}
