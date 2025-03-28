package nlj.business.carrier.link.dto.vehicleIncident.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.dto.vehicleIncident.TrspIsrDTO;
import nlj.business.carrier.link.dto.vehicleIncident.TrspSrvcDTO;

/**
 * <PRE>
 * 車両事故情報リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleIncidentInfoRequestDTO {

    @JsonProperty("trsp_isr")
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    @ValidateField(notNull = true)
    private TrspSrvcDTO trspSrvc;
}
