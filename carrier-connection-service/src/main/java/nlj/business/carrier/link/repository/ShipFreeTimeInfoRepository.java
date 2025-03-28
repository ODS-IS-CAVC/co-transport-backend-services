package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipFreeTimeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 船の空き時間情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFreeTimeInfoRepository extends JpaRepository<ShipFreeTimeInfo, Long> {

    /**
     * 荷主IDのリストで削除.<BR>
     *
     * @param ids 荷主IDのリスト
     */
    void deleteAllByShipToPrtyIdIn(List<Long> ids);

    /**
     * 荷主IDのリストで検索.<BR>
     *
     * @param ids 荷主IDのリスト
     * @return 船の空き時間情報
     */
    List<ShipFreeTimeInfo> findAllByShipToPrtyIdIn(List<Long> ids);
}
