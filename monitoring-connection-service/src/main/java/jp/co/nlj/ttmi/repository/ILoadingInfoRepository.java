package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.ILoadingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 積込情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Repository
public interface ILoadingInfoRepository extends JpaRepository<ILoadingInfo, String> {

    @Query(value = "SELECT TRUNK_LINE_ALLOCATION_KEY FROM (SELECT TRUNK_LINE_ALLOCATION_KEY FROM I_LOADING_INFO WHERE TRUNK_LINE_ALLOCATION_KEY LIKE '%KTL%' ORDER BY CREATE_DT DESC, TRUNK_LINE_ALLOCATION_KEY DESC) WHERE ROWNUM = 1", nativeQuery = true)
    String getLastTrunkLineAllocationKey();

}
