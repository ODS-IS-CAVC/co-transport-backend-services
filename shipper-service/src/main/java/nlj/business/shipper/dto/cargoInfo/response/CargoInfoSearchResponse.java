package nlj.business.shipper.dto.cargoInfo.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.dto.cargoInfo.CargoInfoResponseDTO;

/**
 * <PRE>
 * 荷物情報検索レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CargoInfoSearchResponse {

    private Long totalItem;
    private Integer itemPerPage;
    private Integer currentPage;
    private Integer totalPages;
    private String companyName;
    List<CargoInfoResponseDTO> dataList = new ArrayList<>();
}
