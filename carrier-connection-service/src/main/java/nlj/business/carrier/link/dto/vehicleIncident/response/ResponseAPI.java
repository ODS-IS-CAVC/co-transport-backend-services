package nlj.business.carrier.link.dto.vehicleIncident.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAPI {

    private int code;
    private String message;

}
