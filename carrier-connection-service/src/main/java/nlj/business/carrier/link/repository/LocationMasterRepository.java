package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.LocationMaster;
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
public interface LocationMasterRepository extends JpaRepository<LocationMaster, String> {

    /**
     * コードで検索.<BR>
     *
     * @param code コード
     * @return 車両図面割り当て
     */
    LocationMaster findLocationMasterByCode(String code);
}
