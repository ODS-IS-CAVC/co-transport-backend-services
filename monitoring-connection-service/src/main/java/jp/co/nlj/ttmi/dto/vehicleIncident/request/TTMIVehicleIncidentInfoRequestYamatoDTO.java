package jp.co.nlj.ttmi.dto.vehicleIncident.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspIsrDTO;
import jp.co.nlj.ttmi.dto.vehicleIncident.TrspSrvcYamatoDTO;
import lombok.Data;

/**
 * <PRE>
 * 車両事故情報リクエストヤマトDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TTMIVehicleIncidentInfoRequestYamatoDTO {

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    @ValidateField(notNull = true)
    private TrspSrvcYamatoDTO trspSrvc;
}
