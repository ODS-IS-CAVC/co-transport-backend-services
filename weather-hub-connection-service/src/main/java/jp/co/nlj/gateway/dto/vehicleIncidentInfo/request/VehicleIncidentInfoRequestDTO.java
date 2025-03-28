package jp.co.nlj.gateway.dto.vehicleIncidentInfo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * VehicleIncidentInfoRequestDTOクラスは、車両事故情報リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleIncidentInfoRequestDTO {

    private String dataModelType;

    private AttributeRequestDTO attribute;
}
