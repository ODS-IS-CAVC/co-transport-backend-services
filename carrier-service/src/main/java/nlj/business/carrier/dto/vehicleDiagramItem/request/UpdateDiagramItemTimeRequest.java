package nlj.business.carrier.dto.vehicleDiagramItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両図面アイテム時間更新リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateDiagramItemTimeRequest {

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, endTimeField = "arrivalTime")
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;
}
