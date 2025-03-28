package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipFromPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 出荷場所要件リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFromPrtyRqrmRepository extends JpaRepository<ShipFromPrtyRqrm, Long> {

    /**
     * 指定された出荷場所IDのリストに基づいて、すべての出荷場所要件を削除します。
     *
     * @param ids 削除する出荷場所IDのリスト
     */
    void deleteAllByShipFromPrtyIdIn(List<Long> ids);

    /**
     * 指定された出荷場所IDのリストに基づいて、すべての出荷場所要件を取得します。
     *
     * @param ids 取得する出荷場所IDのリスト
     * @return 出荷場所要件のリスト
     */
    List<ShipFromPrtyRqrm> findAllByShipFromPrtyIdIn(List<Long> ids);
}
