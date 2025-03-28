package nlj.business.carrier.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * Vehicle Diagram Item Repository。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemRepository extends JpaRepository<VehicleDiagramItem, Long> {

    /**
     * 車両ダイアグラムで車両ダイアグラム明細を検索する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @return 車両ダイアグラム明細のリスト
     */
    List<VehicleDiagramItem> findAllByVehicleDiagram(VehicleDiagram vehicleDiagram);

    /**
     * 車両ダイアグラムIDで車両ダイアグラム明細を検索する。
     *
     * @param headId 車両ダイアグラムID
     * @return 車両ダイアグラム明細のリスト
     */
    List<VehicleDiagramItem> findVehicleDiagramItemsByVehicleDiagramId(Long headId);

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細を検索する。
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細
     */
    VehicleDiagramItem findVehicleDiagramItemById(Long id);

    /**
     * 車両ダイアグラムIDのリストで車両ダイアグラム明細を削除する。
     *
     * @param diagramIds 車両ダイアグラムIDのリスト
     * @return 削除された車両ダイアグラム明細の数
     */
    @Modifying
    @Query(value = "delete from vehicle_diagram_item where vehicle_diagram_id in :diagramIds", nativeQuery = true)
    int deleteByDiagramIds(@Param("diagramIds") List<Long> diagramIds);

    /**
     * 車両ダイアグラムIDとオペレータIDで車両ダイアグラム明細を検索する。
     *
     * @param headId     車両ダイアグラムID
     * @param operatorId オペレータID
     * @param pageable   ページング情報
     * @return 車両ダイアグラム明細のページ
     */
    Page<VehicleDiagramItem> findAllByVehicleDiagramIdAndOperatorId(Long headId, String operatorId, Pageable pageable);

    /**
     * 車両ダイアグラムで車両ダイアグラム明細を削除する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     */
    void deleteVehicleDiagramItemsByVehicleDiagram(VehicleDiagram vehicleDiagram);

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細を削除する。
     *
     * @param diagramId 車両ダイアグラム明細ID
     */
    @Modifying
    @Query("DELETE FROM VehicleDiagramItem v WHERE v.vehicleDiagram.id = :diagramId")
    void deleteByVehicleDiagramId(@Param("diagramId") Long diagramId);

    @Modifying
    @Query("DELETE FROM VehicleDiagramItem v WHERE v.id = :id")
    void deleteByVehicleDiagramItemId(@Param("id") Long id);

    /**
     * トリップ名と日付で車両ダイアグラム明細を検索する。
     *
     * @param binName       トリップ名
     * @param transportDate 日付
     * @return 車両ダイアグラム明細
     */
    Optional<VehicleDiagramItem> findFirstByTripNameAndDay(String binName, LocalDate transportDate);

    /**
     * 車両ダイアグラムと日付で車両ダイアグラム明細を検索する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @param date           日付
     * @return 車両ダイアグラム明細
     */
    VehicleDiagramItem findVehicleDiagramItemByVehicleDiagramAndDay(VehicleDiagram vehicleDiagram, LocalDate date);

    /**
     * 車両ダイアグラムと日付で車両ダイアグラム明細を削除する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @param date           日付
     */
    void deleteVehicleDiagramItemByVehicleDiagramAndDay(VehicleDiagram vehicleDiagram, LocalDate date);
}
