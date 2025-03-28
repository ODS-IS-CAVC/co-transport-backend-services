package nlj.business.carrier.dto.vehicleInfo.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両情報リストレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoListResponse {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<VehicleInfoResponse> dataList = new ArrayList<>();
}
