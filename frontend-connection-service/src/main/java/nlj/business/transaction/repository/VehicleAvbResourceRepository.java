package nlj.business.transaction.repository;

import nlj.business.transaction.domain.yamato.VehicleAvailabilityResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 車両利用可能リソースのリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvbResourceRepository extends JpaRepository<VehicleAvailabilityResource, Long> {

}
