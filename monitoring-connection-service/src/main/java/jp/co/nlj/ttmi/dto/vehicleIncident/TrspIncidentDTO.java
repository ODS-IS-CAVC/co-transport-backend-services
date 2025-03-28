package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送事故DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspIncidentDTO {

    @JsonProperty("trsp_incident_gs1_id")
    private String trspIncidentGs1Id;

    @JsonProperty("trsp_incident_category")
    private String trspIncidentCategory;

    @JsonProperty("trsp_incident_type")
    private String trspIncidentType;
}
