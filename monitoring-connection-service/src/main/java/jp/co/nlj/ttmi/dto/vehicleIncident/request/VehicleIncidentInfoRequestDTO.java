package jp.co.nlj.ttmi.dto.vehicleIncident.request;

import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 車両事故情報リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleIncidentInfoRequestDTO {

    @ValidateField(notNull = true)
    private String dataModelType;
    @ValidateField(notNull = true)
    private TTMIVehicleIncidentInfoRequestYamatoDTO attribute;

}