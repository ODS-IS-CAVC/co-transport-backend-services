package nlj.business.carrier.dto.vehicleDiagramItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムトラッキング詳細レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrackingDetailsResponseDTO {

    @JsonProperty("operation_date")
    private String operationDate;

    @JsonProperty("operation_time")
    private String operationTime;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("label")
    private String label;

    @JsonProperty("message")
    private String message;

    public void setMessage(String message) {
        this.message = message != null ? message.replace("\\n", "<br>") : null;
    }
}
