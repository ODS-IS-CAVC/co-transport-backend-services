package nlj.business.carrier.service;

import java.util.List;
import nlj.business.carrier.dto.vehicleDiagramAllocation.request.VehicleDiagramAllocationRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResponseDTO;

/**
 * <PRE>
 * 車両図面割り当てサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramAllocationService {

    /**
     * 車両ダイアグラム割り当てを保存する。
     *
     * @param vehicleDiagramId                   車両ダイアグラムID
     * @param vehicleDiagramAllocationRequestDTO 車両ダイアグラム割り当てリクエストDTOのリスト
     */
    void saveVehicleDiagramAllocation(Long vehicleDiagramId,
        List<VehicleDiagramAllocationRequestDTO> vehicleDiagramAllocationRequestDTO);

    /**
     * 車両ダイアグラム割り当てを取得する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両ダイアグラム割り当てレスポンスDTOのリスト
     */
    List<VehicleDiagramAllocationResponseDTO> getVehicleDiagramAllocation(Long vehicleDiagramId);
}
