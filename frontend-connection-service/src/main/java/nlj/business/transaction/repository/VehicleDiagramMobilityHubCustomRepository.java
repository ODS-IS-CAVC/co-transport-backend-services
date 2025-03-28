package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.dto.trip.VehicleDiagramMobilityHubResponseDTO;

/**
 * <PRE>
 * 車両図面モビリティハブのカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramMobilityHubCustomRepository {

    List<VehicleDiagramMobilityHubResponseDTO> findAllByOperationIdAndVehicleType(Long operationId,
        Integer vehicleType);

    String getVehiclePlateNumberByDiagramItemId(Long diagramItemId);
}
