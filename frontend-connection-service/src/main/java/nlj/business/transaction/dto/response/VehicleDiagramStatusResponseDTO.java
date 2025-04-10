package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両図表ステータス DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramStatusResponseDTO {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
