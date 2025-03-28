package nlj.business.transaction.dto.ix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.dto.ix.IXShipperOperations;

/**
 * <PRE>
 * IX発注者操作レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IXShipperOperationsResponse {

    @JsonProperty("shipper_operations_list")
    private List<IXShipperOperations> shipperOperationsList;

    @JsonProperty("total_num")
    private Integer totalNum;

    private Integer offset;

    private Integer num;

    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;
}
