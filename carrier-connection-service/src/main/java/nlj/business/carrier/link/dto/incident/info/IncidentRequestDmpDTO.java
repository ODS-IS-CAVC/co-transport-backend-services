package nlj.business.carrier.link.dto.incident.info;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * インシデントリクエストDMPDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class IncidentRequestDmpDTO {

    private List<String> vehicleControlIncident;

    private List<String> operationalBasicIncident;

    private List<String> accidentIncident;

    private List<String> weatherIncident;

    private List<String> trafficIncident;
}
