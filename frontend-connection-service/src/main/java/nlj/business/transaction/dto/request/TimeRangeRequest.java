package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 時間範囲リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TimeRangeRequest {

    @JsonProperty("range")
    private Long range;
}
