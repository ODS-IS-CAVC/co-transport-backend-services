package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqShipFromPrty;
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
public interface ReqShipFromPrtyRepository extends JpaRepository<ReqShipFromPrty, Long> {

    /**
     * リクエスト輸送計画明細IDで検索.<BR>
     *
     * @param id リクエスト輸送計画明細ID
     * @return 配送場所
     */
    ReqShipFromPrty findByReqTrspPlanLineItem_Id(Long id);

    /**
     * リクエスト輸送計画明細IDのリストで検索.<BR>
     *
     * @param ids リクエスト輸送計画明細IDのリスト
     * @return 配送場所
     */
    List<ReqShipFromPrty> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);
}
