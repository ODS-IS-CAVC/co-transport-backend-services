package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqShipToPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷届先要件リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqShipToPrtyRqrmRepository extends JpaRepository<ReqShipToPrtyRqrm, Long> {

    /**
     * 指定されたIDのリストに基づいて、すべてのReqShipToPrtyRqrmエンティティを削除します。
     *
     * @param ids 削除するReqShipToPrtyRqrmエンティティのIDリスト
     */
    void deleteAllByReqShipToPrtyIdIn(List<Long> ids);

    /**
     * 指定されたIDに基づいて、単一のReqShipToPrtyRqrmエンティティを検索します。
     *
     * @param id 検索するReqShipToPrtyRqrmエンティティのID
     * @return 検索されたReqShipToPrtyRqrmエンティティ
     */
    ReqShipToPrtyRqrm findByReqShipToPrtyId(Long id);

    /**
     * 指定されたIDのリストに基づいて、すべてのReqShipToPrtyRqrmエンティティを検索します。
     *
     * @param ids 検索するReqShipToPrtyRqrmエンティティのIDリスト
     * @return 検索されたReqShipToPrtyRqrmエンティティのリスト
     */
    List<ReqShipToPrtyRqrm> findAllByReqShipToPrtyIdIn(List<Long> ids);
}
