package nlj.business.carrier.dto.vehicleDiagram.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 輸送車両図形リストレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramListResponse {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<VehicleDiagramResponseDTO> dataList = new ArrayList<>();
}
