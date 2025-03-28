package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipFromPrty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 配送場所リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFromPrtyRepository extends JpaRepository<ShipFromPrty, Long> {

    /**
     * 指定された輸送計画明細項目IDのリストに基づいて、すべての配送場所を取得する。
     *
     * @param ids 輸送計画明細項目IDのリスト
     * @return 配送場所のリスト
     */
    List<ShipFromPrty> findAllByTrspPlanLineItem_IdIn(List<Long> ids);
}
