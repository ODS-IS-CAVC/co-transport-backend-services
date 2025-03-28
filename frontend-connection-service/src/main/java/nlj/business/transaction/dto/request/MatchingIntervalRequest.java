package nlj.business.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.ParamConstant;

/**
 * <PRE>
 * マッチング間隔リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingIntervalRequest {

    @ValidateField(notNull = true, allowValue = {
        ParamConstant.MatchingInterval.SHIPPER,
        ParamConstant.MatchingInterval.CARRIER,
        ParamConstant.MatchingInterval.CARRIER2,
        ParamConstant.MatchingInterval.CARRIER2_EMERGENCY
    })
    private String target;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String interval;
}
