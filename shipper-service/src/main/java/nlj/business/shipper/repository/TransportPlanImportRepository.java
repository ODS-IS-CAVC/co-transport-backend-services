package nlj.business.shipper.repository;

import nlj.business.shipper.domain.TransportPlanImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画インポートリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanImportRepository extends JpaRepository<TransportPlanImport, Long> {

}