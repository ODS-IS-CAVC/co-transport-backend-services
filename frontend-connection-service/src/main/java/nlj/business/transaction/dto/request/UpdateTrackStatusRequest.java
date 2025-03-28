package nlj.business.transaction.dto.request;

import lombok.Data;

/**
 * <PRE>
 * 運送業者トランザクショントラックステータス更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class UpdateTrackStatusRequest {

    private String status;
    private String message;
}
