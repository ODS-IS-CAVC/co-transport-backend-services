package nlj.business.carrier.domain.opt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 時間範囲.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

    private Integer startTime;
    private Integer endTime;
    private Integer adjustment;
}
