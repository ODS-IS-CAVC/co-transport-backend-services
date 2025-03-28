package nlj.business.carrier.repository;

import nlj.business.carrier.domain.VehicleDiagramImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面インポートリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramImportRepository extends JpaRepository<VehicleDiagramImport, Long> {

}
