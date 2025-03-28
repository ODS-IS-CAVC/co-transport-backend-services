package nlj.business.transaction.repository;

import nlj.business.transaction.domain.carrier.VehicleDiagramItemCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面アイテムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemCarrierRepository extends JpaRepository<VehicleDiagramItemCarrier, Long> {

    VehicleDiagramItemCarrier findVehicleDiagramItemById(Long vehicleDiagramItemId);


}
