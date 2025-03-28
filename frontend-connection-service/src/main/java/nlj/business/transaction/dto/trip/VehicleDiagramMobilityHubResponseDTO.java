package nlj.business.transaction.dto.trip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両図形モビリティハブレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramMobilityHubResponseDTO {

    @JsonProperty("slot_id")
    private String slotId;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("mobility_hub_id")
    private String mobilityHubId;
}
