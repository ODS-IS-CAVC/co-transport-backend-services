package nlj.business.carrier.link.dto.incident.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両インシデントリクエストDMPDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleIncidentRequestDmpDTO {

    private VehicleIncidentInfoRequestDmpDTO vehicleIncidentInfo;
}
