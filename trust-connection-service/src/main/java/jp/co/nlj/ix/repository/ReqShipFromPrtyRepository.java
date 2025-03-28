package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqShipFromPrty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 配送場所リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqShipFromPrtyRepository extends JpaRepository<ReqShipFromPrty, Long> {

    /**
     * 指定されたIDに基づいて配送場所を取得する。
     *
     * @param id 配送場所のID
     * @return 配送場所
     */
    ReqShipFromPrty findByReqTrspPlanLineItem_Id(Long id);

    /**
     * 指定されたIDのリストに基づいてすべての配送場所を取得する。
     *
     * @param ids 配送場所のIDのリスト
     * @return 配送場所のリスト
     */
    List<ReqShipFromPrty> findAllByReqTrspPlanLineItem_IdIn(List<Long> ids);
}
