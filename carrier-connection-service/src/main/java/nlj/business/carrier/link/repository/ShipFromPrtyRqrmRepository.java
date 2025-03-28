package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ShipFromPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 出荷場所要件リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipFromPrtyRqrmRepository extends JpaRepository<ShipFromPrtyRqrm, Long> {

    /**
     * 出荷場所IDのリストで削除.<BR>
     *
     * @param ids 出荷場所IDのリスト
     */
    void deleteAllByShipFromPrtyIdIn(List<Long> ids);

    /**
     * 出荷場所IDのリストで検索.<BR>
     *
     * @param ids 出荷場所IDのリスト
     * @return 出荷場所要件
     */
    List<ShipFromPrtyRqrm> findAllByShipFromPrtyIdIn(List<Long> ids);

}