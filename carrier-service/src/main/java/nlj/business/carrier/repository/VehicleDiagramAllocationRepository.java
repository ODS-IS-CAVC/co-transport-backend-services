package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面割り当てリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramAllocationRepository extends JpaRepository<VehicleDiagramAllocation, Long> {

    /**
     * 車両ダイアグラムIDで車両図面割り当てを検索する。<BR>
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両図面割り当て
     */
    VehicleDiagramAllocation findByVehicleDiagramId(Long vehicleDiagramId);

    /**
     * 車両ダイアグラムIDで車両図面割り当てを検索する。<BR>
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両図面割り当て
     */
    List<VehicleDiagramAllocation> findAllByVehicleDiagramId(Long vehicleDiagramId);

    /**
     * 車両ダイアグラムIDと車両タイプで車両図面割り当てを検索する。<BR>
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @param vehicleType      車両タイプ
     * @return 車両図面割り当て
     */
    List<VehicleDiagramAllocation> findAllByVehicleDiagramIdAndVehicleType(Long vehicleDiagramId, int vehicleType);

    /**
     * 車両情報IDで車両図面割り当てを検索する。<BR>
     *
     * @param id 車両情報ID
     * @return 車両図面割り当て
     */
    List<VehicleDiagramAllocation> findAllByVehicleInfoId(Long id);

    /**
     * 車両図面割り当てIDで車両図面割り当てを検索する。<BR>
     *
     * @param id 車両図面割り当てID
     * @return 車両図面割り当て
     */
    VehicleDiagramAllocation findVehicleDiagramAllocationById(Long id);

    /**
     * 車両ダイアグラムIDで車両図面割り当てを削除する。<BR>
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     */
    void deleteVehicleDiagramAllocationsByVehicleDiagramId(Long vehicleDiagramId);

    /**
     * 車両図面割り当てIDのリストで車両図面割り当てを削除する。<BR>
     *
     * @param ids 車両図面割り当てIDのリスト
     */
    void deleteAllByIdIn(List<Long> ids);

    /**
     * 車両ダイアグラムIDで車両図面割り当てを削除する。<BR>
     *
     * @param diagramId 車両ダイアグラムID
     */
    @Modifying
    @Query("DELETE FROM VehicleDiagramAllocation v WHERE v.vehicleDiagramId = :diagramId")
    void deleteByVehicleDiagramId(@Param("diagramId") Long diagramId);

}
