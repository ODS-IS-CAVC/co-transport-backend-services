package nlj.business.carrier.dto.vehicleDiagramItem.request;

import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムトラックステータス更新リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateTrackStatusRequest {

    private String status;
    private String message;
}
