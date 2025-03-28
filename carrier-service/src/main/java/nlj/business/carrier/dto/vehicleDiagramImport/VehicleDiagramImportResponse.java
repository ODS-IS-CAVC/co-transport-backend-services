package nlj.business.carrier.dto.vehicleDiagramImport;

import java.util.List;
import lombok.Data;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;

/**
 * <PRE>
 * 車両図面インポートレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramImportResponse {

    private VehicleDiagramImportDTO vehicleDiagramImportDTO;
    private List<VehicleDiagramHeadDTO> vehicleDiagramHeadDTOS;
}
