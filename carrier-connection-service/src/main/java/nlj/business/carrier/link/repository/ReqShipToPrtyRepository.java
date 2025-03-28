package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.ReqShipToPrty;
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
public interface ReqShipToPrtyRepository extends JpaRepository<ReqShipToPrty, Long> {

    /**
     * リクエスト輸送計画明細IDのリストで検索.<BR>
     *
     * @param ids リクエスト輸送計画明細IDのリスト
     * @return 荷届先
     */
    List<ReqShipToPrty> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);

    /**
     * リクエスト輸送計画明細IDで検索.<BR>
     *
     * @param id リクエスト輸送計画明細ID
     * @return 荷届先
     */
    ReqShipToPrty findByReqTrspPlanLineItem_Id(Long id);
}