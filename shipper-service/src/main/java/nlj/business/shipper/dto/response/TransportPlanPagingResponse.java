package nlj.business.shipper.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanPagingResponse {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPage;
    private String companyName;
    private List<TransportPlanResponseDTO> dataList = new ArrayList<>();
}
