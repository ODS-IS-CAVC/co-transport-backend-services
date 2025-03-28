package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ShipToPrty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷届先リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipToPrtyRepository extends JpaRepository<ShipToPrty, Long> {

    /**
     * 指定された輸送計画明細項目IDのリストに基づいて、すべての荷届先を取得する。
     *
     * @param ids 輸送計画明細項目IDのリスト
     * @return 荷届先のリスト
     */
    List<ShipToPrty> findAllByTrspPlanLineItem_IdIn(List<Long> ids);
}
