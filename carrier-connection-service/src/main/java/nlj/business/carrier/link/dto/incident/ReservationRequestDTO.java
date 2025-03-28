package nlj.business.carrier.link.dto.incident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 予約リクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private String dataModelType;
    private AttributeRequestDTO attribute;
}
