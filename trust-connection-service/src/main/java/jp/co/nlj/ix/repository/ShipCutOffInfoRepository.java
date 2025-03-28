package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipCutOffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 船舶遮断情報リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipCutOffInfoRepository extends JpaRepository<ShipCutOffInfo, Long> {

    /**
     * 指定された船舶発信パーティIDのリストに基づいて、すべての船舶遮断情報を取得する。
     *
     * @param ids 船舶発信パーティIDのリスト
     * @return 船舶遮断情報のリスト
     */
    List<ShipCutOffInfo> findAllByShipFromPrtyIdIn(List<Long> ids);

    /**
     * 指定された船舶発信パーティIDのリストに基づいて、すべての船舶遮断情報を削除する。
     *
     * @param ids 船舶発信パーティIDのリスト
     */
    void deleteAllByShipFromPrtyIdIn(List<Long> ids);
}
