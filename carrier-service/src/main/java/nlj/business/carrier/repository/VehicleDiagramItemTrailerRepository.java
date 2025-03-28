package nlj.business.carrier.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細トレーラリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrailerRepository extends JpaRepository<VehicleDiagramItemTrailer, Long> {

    /**
     * 車両ダイアグラムで車両ダイアグラム明細トレーラを検索する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @return 車両ダイアグラム明細トレーラのリスト
     */
    List<VehicleDiagramItemTrailer> findVehicleDiagramItemTrailerByVehicleDiagram(VehicleDiagram vehicleDiagram);

    /**
     * 車両ダイアグラムと車両ダイアグラム明細で車両ダイアグラム明細トレーラを削除する。
     *
     * @param vehicleDiagram     車両ダイアグラム
     * @param vehicleDiagramItem 車両ダイアグラム明細
     */
    void deleteVehicleDiagramItemTrailerByVehicleDiagramAndVehicleDiagramItem(VehicleDiagram vehicleDiagram,
        VehicleDiagramItem vehicleDiagramItem);

    /**
     * 車両ダイアグラムで車両ダイアグラム明細トレーラを削除する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     */
    void deleteVehicleDiagramItemTrailersByVehicleDiagram(VehicleDiagram vehicleDiagram);

    /**
     * 車両ダイアグラムで車両ダイアグラム明細トレーラを検索する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @return 車両ダイアグラム明細トレーラのリスト
     */
    List<VehicleDiagramItemTrailer> findVehicleDiagramItemTrailersByVehicleDiagram(VehicleDiagram vehicleDiagram);

    /**
     * 車両ダイアグラム明細で車両ダイアグラム明細トレーラを検索する。
     *
     * @param vehicleDiagramItem 車両ダイアグラム明細
     * @return 車両ダイアグラム明細トレーラのリスト
     */
    List<VehicleDiagramItemTrailer> findVehicleDiagramItemTrailersByVehicleDiagramItem(
        VehicleDiagramItem vehicleDiagramItem);

    /**
     * 車両ダイアグラムと日付で車両ダイアグラム明細トレーラを検索する。
     *
     * @param vehicleDiagram 車両ダイアグラム
     * @param date           日付
     * @return 車両ダイアグラム明細トレーラのリスト
     */
    List<VehicleDiagramItemTrailer> findVehicleDiagramItemTrailersByVehicleDiagramAndDay(VehicleDiagram vehicleDiagram,
        LocalDate date);

    /**
     * 車両ダイアグラムIDで車両ダイアグラム明細トレーラを削除する。
     *
     * @param diagramId 車両ダイアグラムID
     */
    @Modifying
    @Query("DELETE FROM VehicleDiagramItemTrailer v WHERE v.vehicleDiagram.id = :diagramId")
    void deleteByVehicleDiagramId(@Param("diagramId") Long diagramId);

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細トレーラを検索する。
     *
     * @param itemId 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細トレーラのリスト
     */
    List<VehicleDiagramItemTrailer> findAllByVehicleDiagramItemId(Long itemId);

    /**
     * 車両ダイアグラム明細IDのリストで車両ダイアグラム明細トレーラを削除する。
     *
     * @param id 車両ダイアグラム明細IDのリスト
     */
    void deleteAllByIdIn(List<Long> id);

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細トレーラを削除する。
     *
     * @param id 車両ダイアグラム明細ID
     */
    @Modifying
    @Query("DELETE FROM VehicleDiagramItemTrailer v WHERE v.vehicleDiagramItem.id = :id")
    void deleteAllByVehicleDiagramItemId(@Param("id") Long id);

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細トレーラを検索する。
     *
     * @param itemId 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細トレーラ
     */
    VehicleDiagramItemTrailer findFirstByVehicleDiagramItemId(Long itemId);
}
