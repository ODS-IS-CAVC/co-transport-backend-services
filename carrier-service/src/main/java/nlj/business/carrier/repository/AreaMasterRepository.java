package nlj.business.carrier.repository;

import nlj.business.carrier.domain.AreaMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面割り当てリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface AreaMasterRepository extends JpaRepository<AreaMaster, String> {

}
