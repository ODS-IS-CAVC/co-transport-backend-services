package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqShipToPrtyRqrm;
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
public interface ReqShipToPrtyRqrmRepository extends JpaRepository<ReqShipToPrtyRqrm, Long> {

    /**
     * 荷届先IDのリストで検索.<BR>
     *
     * @param ids 荷届先IDのリスト
     */
    void deleteAllByReqShipToPrtyIdIn(List<Long> ids);

    /**
     * 荷届先IDで検索.<BR>
     *
     * @param id 荷届先ID
     * @return 荷届先要件
     */
    ReqShipToPrtyRqrm findByReqShipToPrtyId(Long id);

    /**
     * 荷届先IDで検索.<BR>
     *
     * @param ids 荷届先IDs
     * @return 荷届先要件
     */
    List<ReqShipToPrtyRqrm> findAllByReqShipToPrtyIdIn(List<Long> ids);
}
