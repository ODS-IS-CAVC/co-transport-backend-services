package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.MOperatingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運行サービスリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Repository
public interface MOperatingServiceRepository extends JpaRepository<MOperatingService, String> {

    @Query(value = "SELECT OPE_SERVICE_KEY FROM (SELECT OPE_SERVICE_KEY FROM M_OPERATING_SERVICE WHERE OPE_SERVICE_KEY LIKE '%LOP%' ORDER BY CREATE_DT DESC, OPE_SERVICE_KEY DESC) WHERE ROWNUM = 1", nativeQuery = true)
    String getLastOpeServiceKey();

}
