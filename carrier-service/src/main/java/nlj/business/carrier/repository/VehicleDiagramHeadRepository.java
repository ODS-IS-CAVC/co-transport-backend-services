package nlj.business.carrier.repository;

import nlj.business.carrier.domain.VehicleDiagramHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * Vehicle Diagram Head Repository。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramHeadRepository extends JpaRepository<VehicleDiagramHead, Long> {

    /**
     * 車両ダイアグラム明細IDで車両ダイアグラム明細を検索する。<BR>
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細
     */
    VehicleDiagramHead findVehicleDiagramHeadById(Long id);
} 