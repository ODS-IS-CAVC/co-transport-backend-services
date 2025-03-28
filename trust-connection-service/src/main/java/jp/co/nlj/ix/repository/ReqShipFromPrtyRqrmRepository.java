package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.ReqShipFromPrtyRqrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 出荷場所要件リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqShipFromPrtyRqrmRepository extends JpaRepository<ReqShipFromPrtyRqrm, Long> {

    /**
     * 指定された出荷場所要件IDに基づいて出荷場所要件を取得する。
     *
     * @param id 出荷場所要件ID
     * @return 出荷場所要件
     */
    ReqShipFromPrtyRqrm findByReqShipFromPrtyId(Long id);

    /**
     * 指定された出荷場所要件IDのリストに基づいて全ての出荷場所要件を削除する。
     *
     * @param ids 出荷場所要件IDのリスト
     */
    void deleteAllByReqShipFromPrtyIdIn(List<Long> ids);

    /**
     * 指定された出荷場所要件IDのリストに基づいて全ての出荷場所要件を取得する。
     *
     * @param ids 出荷場所要件IDのリスト
     * @return 出荷場所要件のリスト
     */
    List<ReqShipFromPrtyRqrm> findAllByReqShipFromPrtyIdIn(List<Long> ids);
}
