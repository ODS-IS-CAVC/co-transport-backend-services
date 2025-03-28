package nlj.business.carrier.link.dto.incident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * リクエスト説明DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDescriptionDTO {

    private String mobilityHubId;
    private String freightId;
    private String truckId;
    private String sizeClass;
}
