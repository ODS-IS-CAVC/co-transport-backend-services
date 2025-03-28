package nlj.business.transaction.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.dto.trip.VehicleDiagramMobilityHubResponseDTO;
import nlj.business.transaction.repository.VehicleDiagramMobilityHubCustomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両図形モビリティハブのカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleDiagramMobilityHubCustomRepositoryImpl implements VehicleDiagramMobilityHubCustomRepository {

    private final EntityManager entityManager;

    /**
     * 操作IDと車両タイプに基づいて車両図形モビリティハブを検索する。
     *
     * @param operationId 操作ID
     * @param vehicleType 車両タイプ
     * @return 車両図形モビリティハブのリスト
     */
    @Override
    public List<VehicleDiagramMobilityHubResponseDTO> findAllByOperationIdAndVehicleType(Long operationId,
        Integer vehicleType) {
        String selectedField = "mh_reservation_id,type,mobility_hub_id";
        Query query = createQueryVehicleDiagramMobilityHub(operationId, vehicleType, selectedField);
        List<VehicleDiagramMobilityHubResponseDTO> responseDTOList = query.getResultList();
        return responseDTOList;
    }

    /**
     * 車両図形モビリティハブのクエリを作成する。
     *
     * @param operationId  操作ID
     * @param vehicleType  車両タイプ
     * @param selectFields 選択するフィールド
     * @return 車両図形モビリティハブのクエリ
     */
    private Query createQueryVehicleDiagramMobilityHub(Long operationId, Integer vehicleType, String selectFields) {
        String queryString = "SELECT " + selectFields + " FROM c_vehicle_diagram_mobility_hub cvdmh "
            + "WHERE cvdmh.vehicle_diagram_item_id = :operation_id " +
            "AND cvdmh.vehicle_type = :vehicle_type ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("operation_id", operationId);
        parameters.put("vehicle_type", vehicleType);
        Query query = entityManager.createNativeQuery(queryString, VehicleDiagramMobilityHubResponseDTO.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    /*
     * 車両図形アイテムIDに基づいて車両プレート番号を取得する。
     *
     * @param vehicleDiagramItemId 車両図形アイテムID
     * @return 車両プレート番号
     */
    @Transactional
    @Override
    public String getVehiclePlateNumberByDiagramItemId(Long vehicleDiagramItemId) {
        String stringQurey = "SELECT truck_id FROM c_vehicle_diagram_mobility_hub "
            + "WHERE vehicle_diagram_item_id = :vehicleDiagramItemId AND vehicle_type = 1 LIMIT 1";
        Query query = entityManager.createNativeQuery(stringQurey, String.class);
        query.setParameter("vehicleDiagramItemId", vehicleDiagramItemId);
        return query.getSingleResult() == null ? "" : query.getSingleResult().toString();
    }
}