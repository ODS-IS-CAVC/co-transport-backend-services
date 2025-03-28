package nlj.business.shipper.dto.transportPlanItem.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.dto.TransportPlanItemDTO;

/**
 * <PRE>
 * 輸送計画レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanItemPagingResponse {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<TransportPlanItemDTO> dataList = new ArrayList<>();
}
