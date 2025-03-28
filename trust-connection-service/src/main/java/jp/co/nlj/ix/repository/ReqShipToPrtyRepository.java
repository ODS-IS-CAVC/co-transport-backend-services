package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqShipToPrty;
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
public interface ReqShipToPrtyRepository extends JpaRepository<ReqShipToPrty, Long> {

    /**
     * 指定されたIDのリストに基づいて、すべてのReqShipToPrtyエンティティを取得します。
     *
     * @param ids ReqShipToPrtyを取得するためのIDのリスト
     * @return 指定されたIDに関連するReqShipToPrtyのリスト
     */
    List<ReqShipToPrty> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);

    /**
     * 指定されたIDに基づいて、単一のReqShipToPrtyエンティティを取得します。
     *
     * @param id ReqShipToPrtyを取得するためのID
     * @return 指定されたIDに関連するReqShipToPrtyエンティティ
     */
    ReqShipToPrty findByReqTrspPlanLineItem_Id(Long id);
}
