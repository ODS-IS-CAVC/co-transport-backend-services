package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipFreeTimeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 船の空き時間情報リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFreeTimeInfoRepository extends JpaRepository<ShipFreeTimeInfo, Long> {

    /**
     * 指定された船のIDリストに基づいて、すべての空き時間情報を削除します。
     *
     * @param ids 削除する船のIDリスト
     */
    void deleteAllByShipToPrtyIdIn(List<Long> ids);

    /**
     * 指定された船のIDリストに基づいて、すべての空き時間情報を取得します。
     *
     * @param ids 取得する船のIDリスト
     * @return 船の空き時間情報のリスト
     */
    List<ShipFreeTimeInfo> findAllByShipToPrtyIdIn(List<Long> ids);
}
