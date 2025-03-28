package nlj.business.carrier.link.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送事故情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspIncidentDTO {

    @ValidateField(maxLength = 30)
    @JsonProperty("trsp_incident_gs1_id")
    private String trspIncidentGs1Id;

    @JsonProperty("trsp_incident_category")
    private String trspIncidentCategory;

    @JsonProperty("trsp_incident_type")
    private String trspIncidentType;
}
