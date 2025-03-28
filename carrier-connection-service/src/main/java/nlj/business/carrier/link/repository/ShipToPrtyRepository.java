package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipToPrty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷届先リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipToPrtyRepository extends JpaRepository<ShipToPrty, Long> {

    /**
     * 輸送計画明細項目IDのリストで検索.<BR>
     *
     * @param ids 輸送計画明細項目IDのリスト
     * @return 荷届先
     */
    List<ShipToPrty> findAllByTrspPlanLineItem_IdIn(List<Long> ids);
}
