package nlj.business.shipper.repository;

import nlj.business.shipper.domain.CargoInfoImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷物情報インポートリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CargoInfoImportRepository extends JpaRepository<CargoInfoImport, Long> {

}
