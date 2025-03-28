package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipToPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷届先要件リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipToPrtyRqrmRepository extends JpaRepository<ShipToPrtyRqrm, Long> {

    /**
     * 指定されたIDのリストに基づいて、すべてのShipToPrtyRqrmエンティティを削除します。
     *
     * @param ids 削除するShipToPrtyRqrmエンティティのIDリスト
     */
    void deleteAllByShipToPrtyIdIn(List<Long> ids);

    /**
     * 指定されたIDのリストに基づいて、すべてのShipToPrtyRqrmエンティティを検索します。
     *
     * @param ids 検索するShipToPrtyRqrmエンティティのIDリスト
     * @return 検索されたShipToPrtyRqrmエンティティのリスト
     */
    List<ShipToPrtyRqrm> findAllByShipToPrtyIdIn(List<Long> ids);
}
