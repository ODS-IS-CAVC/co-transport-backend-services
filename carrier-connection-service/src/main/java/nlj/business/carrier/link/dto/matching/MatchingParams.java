package nlj.business.carrier.link.dto.matching;

import java.math.BigDecimal;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * マッチングパラメータDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
public class MatchingParams {

    private Long departureFrom;
    private Long arrivalTo;
    private LocalTime collectionTimeFrom;
    private LocalTime collectionTimeTo;
    private LocalTime carrierDepartureTime;
    private LocalTime carrierArrivalTime;
    private BigDecimal maxPayload;
    private BigDecimal totalLength;
    private BigDecimal totalWidth;
    private BigDecimal totalHeight;
}
