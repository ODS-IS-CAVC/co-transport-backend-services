package nlj.business.carrier.dto.vehicleInfo.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.dto.vehicleInfo.VehicleInfoImportDTO;

/**
 * <PRE>
 * 車両情報インポートリクエストDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoImportRequest {

    private VehicleInfoImportDTO vehicleInfoImportDTO;
    private List<VehicleInfoRegisterRequest> vehicleInfoRegisterRequests;
}
