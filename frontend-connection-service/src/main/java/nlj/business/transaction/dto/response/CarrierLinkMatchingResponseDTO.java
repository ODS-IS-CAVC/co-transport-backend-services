package nlj.business.transaction.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.dto.TransMatchingDTO;

/**
 * <PRE>
 * 運送業者リンクマッチングレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierLinkMatchingResponseDTO {

    private List<TransMatchingDTO> data;
}
