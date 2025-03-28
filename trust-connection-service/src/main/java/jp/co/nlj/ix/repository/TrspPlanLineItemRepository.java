package jp.co.nlj.ix.repository;

import java.time.LocalDate;
import java.util.List;
import jp.co.nlj.ix.domain.TrspPlanLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanLineItemRepository extends JpaRepository<TrspPlanLineItem, Long>,
    TrspPlanLineItemCustomRepository {

    /**
     * 輸送指示IDのリストに基づいてすべての輸送計画明細を検索します。
     *
     * @param trspInstructionIds 輸送指示IDのリスト
     * @return 輸送計画明細のリスト
     */
    List<TrspPlanLineItem> findAllByTrspInstructionIdIn(List<String> trspInstructionIds);

    /**
     * 輸送指示IDに基づいて輸送計画明細を検索します。
     *
     * @param id 輸送指示ID
     * @return 一致する輸送計画明細
     */
    TrspPlanLineItem findByTrspInstructionId(String id);

    /**
     * 輸送計画明細を特定の条件に基づいて検索します。
     *
     * @param operatorId           オペレーターID
     * @param trspInstructionId    輸送指示ID
     * @param serviceNo            サービス番号
     * @param serviceStrtDate      サービス開始日
     * @param cnsgPrtyHeadOffId    荷主本社ID
     * @param cnsgPrtyBrncOffId    荷主支社ID
     * @param trspRqrPrtyHeadOffId 輸送要求本社ID
     * @param trspRqrPrtyBrncOffId 輸送要求支社ID
     * @return 一致する輸送計画明細
     */
    TrspPlanLineItem findByOperatorIdAndTrspInstructionIdAndServiceNoAndServiceStrtDateAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffIdAndTrspRqrPrtyHeadOffIdAndTrspRqrPrtyBrncOffId(
        String operatorId,
        String trspInstructionId,
        String serviceNo,
        LocalDate serviceStrtDate,
        String cnsgPrtyHeadOffId,
        String cnsgPrtyBrncOffId,
        String trspRqrPrtyHeadOffId,
        String trspRqrPrtyBrncOffId
    );
}
