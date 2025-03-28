package nlj.business.transaction.repository;

import java.time.LocalDateTime;
import java.util.List;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両利用可能リソース品目のリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvbResourceItemRepository extends JpaRepository<VehicleAvbResourceItem, Long> {

    List<VehicleAvbResourceItem> findByVehicleDiagramId(Long vehicleDiagramId);

    List<VehicleAvbResourceItem> findByVehicleAvbResourceId(Long vehicleAvbResourceId);

    @Modifying
    @Transactional
    @Query("UPDATE VehicleAvbResourceItem ve SET ve.status = :status WHERE ve.id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    @Modifying
    @Transactional
    @Query("UPDATE VehicleAvbResourceItem ve SET ve.status = :status, ve.updatedDate = CURRENT_TIMESTAMP WHERE ve.vehicleAvbResourceId = :vehicleAvbResourceId")
    void updateStatusByVehicleAvbResourceId(@Param("vehicleAvbResourceId") Long id, @Param("status") Integer status);

    @Query(value = "SELECT ve FROM VehicleAvbResourceItem ve WHERE ve.status IN :statuses " +
        "AND (ve.createdDate >= :lastInserted OR ve.updatedDate >= :lastInserted) " +
        "ORDER BY ve.vehicleAvbResourceId DESC")
    List<VehicleAvbResourceItem> findAllByStatusAndLastCreated(@Param("statuses") List<Integer> statuses,
        @Param("lastInserted") LocalDateTime lastInserted);

    VehicleAvbResourceItem findByIdAndOperatorId(Long id, String operatorId);
}
