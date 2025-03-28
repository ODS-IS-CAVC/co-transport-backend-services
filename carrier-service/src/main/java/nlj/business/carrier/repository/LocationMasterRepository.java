package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.LocationMaster;
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
     * 場所マスタをコードで検索する。<BR>
     *
     * @param code 場所マスタコード
     * @return 場所マスタ
     */
    LocationMaster findLocationMasterByCode(String code);

    /**
     * エリアIDで場所マスタを検索する。<BR>
     *
     * @param areaId エリアID
     * @return 場所マスタ
     */
    List<LocationMaster> findLocationMastersByAreaId(Long areaId);
}
