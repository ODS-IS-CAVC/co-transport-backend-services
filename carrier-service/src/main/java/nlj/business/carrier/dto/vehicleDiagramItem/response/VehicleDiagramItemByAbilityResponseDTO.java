package nlj.business.carrier.dto.vehicleDiagramItem.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両図面アイテム検索レスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramItemByAbilityResponseDTO {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<VehicleDiagramItemByAbilityDetailsDTO> dataList = new ArrayList<>();
}
