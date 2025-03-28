package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipFromPrty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 配送場所リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFromPrtyRepository extends JpaRepository<ShipFromPrty, Long> {

    /**
     * 輸送計画明細項目IDのリストで検索.<BR>
     *
     * @param ids 輸送計画明細項目IDのリスト
     * @return 配送場所
     */
    List<ShipFromPrty> findAllByTrspPlanLineItem_IdIn(List<Long> ids);
}
