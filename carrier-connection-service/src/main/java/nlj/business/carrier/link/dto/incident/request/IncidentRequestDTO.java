package nlj.business.carrier.link.dto.incident.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

/**
 * <PRE>
 * インシデントリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class IncidentRequestDTO {

    @JsonProperty("instruction_id")
    private String instructionId;

    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("incident_json")
    private Map<String, Object> incidentJson;

}
