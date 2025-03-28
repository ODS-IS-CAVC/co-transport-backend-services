package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramRepository extends JpaRepository<VehicleDiagram, Long>,
    JpaSpecificationExecutor<VehicleDiagram> {

    /**
     * 車両ダイアグラムIDで車両ダイアグラムを検索する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両ダイアグラム
     */
    VehicleDiagram findVehicleDiagramById(Long vehicleDiagramId);

    /**
     * 車両ダイアグラムヘッドIDで車両ダイアグラムを検索する。
     *
     * @param headId 車両ダイアグラムヘッドID
     * @return 車両ダイアグラムのリスト
     */
    List<VehicleDiagram> findVehicleDiagramByVehicleDiagramHeadId(Long headId);

    /**
     * 車両ダイアグラムヘッドで車両ダイアグラムを削除する。
     *
     * @param vehicleDiagramHead 車両ダイアグラムヘッド
     */
    void deleteAllByVehicleDiagramHead(VehicleDiagramHead vehicleDiagramHead);

}
