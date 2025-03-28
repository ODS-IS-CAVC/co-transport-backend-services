package jp.co.nlj.ix.repository;

import java.util.Optional;
import jp.co.nlj.ix.domain.TransportAbilityLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力明細リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportAbilityLineItemRepository extends JpaRepository<TransportAbilityLineItem, Long> {

    /**
     * 運送能力管理IDで運送能力明細を検索する
     *
     * @param id 運送能力管理ID
     * @return 運送能力明細
     */
    Optional<TransportAbilityLineItem> findById(Long id);

    /**
     * 指定された本社ID、支社ID、およびオペレーターIDに一致する 最初の輸送能力ラインアイテムを検索します。
     *
     * @param trspCliPrtyHeadOffId 本社ID
     * @param trspCliPrtyBrncOffId 支社ID
     * @return 一致する最初の輸送能力ラインアイテム、存在しない場合はnull
     */
    TransportAbilityLineItem findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffId(String trspCliPrtyHeadOffId,
        String trspCliPrtyBrncOffId);
}
