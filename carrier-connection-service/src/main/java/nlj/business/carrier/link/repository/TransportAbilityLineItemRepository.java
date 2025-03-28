package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportAbilityLineItemRepository extends JpaRepository<TransportAbilityLineItem, Long> {

    /**
     * 本社 ID (trspCliPrtyHeadOffId) と支店 ID (trspCliPrtyBrncOffId) で最初の輸送能力明細項目を取得します
     *
     * @param trspCliPrtyHeadOffId 本社 ID
     * @param trspCliPrtyBrncOffId 支店 ID
     * @return 輸送能力明細項目
     */
    TransportAbilityLineItem findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffId(String trspCliPrtyHeadOffId,
        String trspCliPrtyBrncOffId);

    /**
     * 指定された本社ID、支社ID、およびオペレーターIDに一致する 最初の輸送能力ラインアイテムを検索します。
     *
     * @param trspCliPrtyHeadOffId 本社ID
     * @param trspCliPrtyBrncOffId 支社ID
     * @param operatorId           オペレーターID
     * @return 一致する最初の輸送能力ラインアイテム、存在しない場合はnull
     */
    TransportAbilityLineItem findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffIdAndOperatorId(
        String trspCliPrtyHeadOffId, String trspCliPrtyBrncOffId, String operatorId);

    /**
     * 指定された本社ID、支社ID、およびオペレーターIDに一致する 最初の輸送能力ラインアイテムを検索します。
     *
     * @param trspCliPrtyHeadOffId 本社ID
     * @param trspCliPrtyBrncOffId 支社ID
     * @param operatorId           オペレーターID
     * @return 一致する最初の輸送能力ラインアイテム、存在しない場合はnull
     */
    List<TransportAbilityLineItem> findAllByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffIdAndOperatorId(
        String trspCliPrtyHeadOffId, String trspCliPrtyBrncOffId, String operatorId);
}
