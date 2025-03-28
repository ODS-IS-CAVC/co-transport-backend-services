package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipCutOffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 船舶遮断情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipCutOffInfoRepository extends JpaRepository<ShipCutOffInfo, Long> {

    /**
     * 荷主IDのリストで検索.<BR>
     *
     * @param ids 荷主IDのリスト
     * @return 船舶遮断情報
     */
    List<ShipCutOffInfo> findAllByShipFromPrtyIdIn(List<Long> ids);

    /**
     * 荷主IDのリストで削除.<BR>
     *
     * @param ids 荷主IDのリスト
     */
    void deleteAllByShipFromPrtyIdIn(List<Long> ids);
}
