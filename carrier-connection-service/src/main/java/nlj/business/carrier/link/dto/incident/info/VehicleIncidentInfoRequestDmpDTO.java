package nlj.business.carrier.link.dto.incident.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両インシデント情報リクエストDMPDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleIncidentInfoRequestDmpDTO {

    private String dataModelType;

    private AttributeRequestDmpDTO attribute;
}
