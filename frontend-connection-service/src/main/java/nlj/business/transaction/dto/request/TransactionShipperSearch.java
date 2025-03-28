package nlj.business.transaction.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者トランザクション検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionShipperSearch extends CommonPagingSearch {

    @ValidateField(transOrderSearchStatus = true)
    private String status;
    private String freeWord;
    private List<Integer> temperatureRange;
    private List<String> advanceStatus;
}
