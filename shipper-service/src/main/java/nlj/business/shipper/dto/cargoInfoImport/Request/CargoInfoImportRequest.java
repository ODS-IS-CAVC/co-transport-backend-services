package nlj.business.shipper.dto.cargoInfoImport.Request;

import java.util.List;
import lombok.Data;
import nlj.business.shipper.dto.cargoInfo.request.CargoInfoRequest;
import nlj.business.shipper.dto.cargoInfoImport.CargoInfoImportDTO;

/**
 * <PRE>
 * 荷物情報インポートリクエスト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CargoInfoImportRequest {

    private CargoInfoImportDTO cargoInfoImportDTO;
    private List<CargoInfoRequest> cargoInfoRequestList;
}
