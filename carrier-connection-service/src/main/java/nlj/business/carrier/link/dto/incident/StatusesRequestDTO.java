package nlj.business.carrier.link.dto.incident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * ステータスリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusesRequestDTO {

    private RequestDescriptionDTO requestObject;
    private String value;
    private String validFrom;
    private String validUntil;
}
