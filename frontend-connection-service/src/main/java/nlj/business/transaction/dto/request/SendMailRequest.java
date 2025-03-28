package nlj.business.transaction.dto.request;

import lombok.Data;

/**
 * <PRE>
 * メール送信リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class SendMailRequest {

    private Long vehicleAvbResourceItemId;
    private Long cnsLineItemByDateId;
}
