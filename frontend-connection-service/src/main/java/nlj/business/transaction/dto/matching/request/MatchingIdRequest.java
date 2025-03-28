package nlj.business.transaction.dto.matching.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * マッチングIDリクエスト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingIdRequest {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE)
    private String id;
}
