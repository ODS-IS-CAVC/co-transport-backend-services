package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramMobilityHubRepository extends JpaRepository<VehicleDiagramMobilityHub, Long> {

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラムモビリティハブを検索する。
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラムモビリティハブのリスト
     */
    List<VehicleDiagramMobilityHub> findAllByVehicleDiagramItemId(Long id);

    /**
     * 車両ダイアグラム明細IDとタイプで車両ダイアグラムモビリティハブを検索する。
     *
     * @param id   車両ダイアグラム明細ID
     * @param type タイプ
     * @return 車両ダイアグラムモビリティハブのリスト
     */
    @Query(value = "SELECT * FROM c_vehicle_diagram_mobility_hub WHERE vehicle_diagram_item_id = :id AND type = :type", nativeQuery = true)
    List<VehicleDiagramMobilityHub> findAllByVehicleDiagramItemIdAndType(@Param("id") Long id, @Param("type") int type);
}

