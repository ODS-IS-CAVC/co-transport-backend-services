package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面アイテムトレーラーのリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrailerRepository extends JpaRepository<VehicleDiagramItemTrailer, Long>,
    VehicleDiagramItemTrailerCustomRepository {

    List<VehicleDiagramItemTrailer> findByStatus(Integer status);

    List<VehicleDiagramItemTrailer> findByVehicleDiagramId(Long vehicleDiagramId);

    VehicleDiagramItemTrailer findFirstByStatusOrderByIdAsc(Integer status);
}
