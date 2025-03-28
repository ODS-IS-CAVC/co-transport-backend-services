package jp.co.nlj.ttmi.dto.vehicleIncident.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspIsrDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspSrvcDTO;
import lombok.Data;

/**
 * <PRE>
 * 車両事故情報リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TTMIVehicleIncidentInfoRequestDTO {

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    @ValidateField(notNull = true)
    private TrspSrvcDTO trspSrvc;
}
