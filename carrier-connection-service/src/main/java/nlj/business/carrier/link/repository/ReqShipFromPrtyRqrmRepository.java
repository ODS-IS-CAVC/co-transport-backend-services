package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqShipFromPrtyRqrm;
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
public interface ReqShipFromPrtyRqrmRepository extends JpaRepository<ReqShipFromPrtyRqrm, Long> {

    /**
     * 出荷場所IDで検索.<BR>
     *
     * @param id 出荷場所ID
     * @return 出荷場所要件
     */
    ReqShipFromPrtyRqrm findByReqShipFromPrtyId(Long id);

    /**
     * 出荷場所IDのリストで検索.<BR>
     *
     * @param ids 出荷場所IDのリスト
     */
    void deleteAllByReqShipFromPrtyIdIn(List<Long> ids);

    /**
     * 出荷場所IDのリストで検索.<BR>
     *
     * @param ids 出荷場所IDのリスト
     * @return 出荷場所要件
     */
    List<ReqShipFromPrtyRqrm> findAllByReqShipFromPrtyIdIn(List<Long> ids);
}
