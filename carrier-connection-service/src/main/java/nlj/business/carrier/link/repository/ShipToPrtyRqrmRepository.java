package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipToPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷届先要件リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipToPrtyRqrmRepository extends JpaRepository<ShipToPrtyRqrm, Long> {

    /**
     * 荷届先IDのリストで削除.<BR>
     *
     * @param ids 荷届先IDのリスト
     */
    void deleteAllByShipToPrtyIdIn(List<Long> ids);

    /**
     * 荷届先IDのリストで検索.<BR>
     *
     * @param ids 荷届先IDのリスト
     * @return 荷届先要件
     */
    List<ShipToPrtyRqrm> findAllByShipToPrtyIdIn(List<Long> ids);
}
