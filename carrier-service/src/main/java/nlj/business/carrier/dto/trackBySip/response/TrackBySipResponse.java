package nlj.business.carrier.dto.trackBySip.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * トラックバイSIPレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackBySipResponse {

    private int code;

    private String message;

    private String id;
}
