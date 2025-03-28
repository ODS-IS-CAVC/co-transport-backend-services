package jp.co.nlj.gateway.dto.vehicleIncidentInfo.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * IncidentRequestDTOクラスは、事故リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class IncidentRequestDTO {

    private List<String> vehicleControlIncident;

    private List<String> operationalBasicIncident;

    private List<String> accidentIncident;

    private List<String> weatherIncident;

    private List<String> trafficIncident;
}
