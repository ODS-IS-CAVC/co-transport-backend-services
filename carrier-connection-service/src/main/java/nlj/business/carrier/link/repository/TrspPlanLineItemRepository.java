package nlj.business.carrier.link.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanLineItemRepository extends JpaRepository<TrspPlanLineItem, Long>,
    TrspPlanLineItemCustomRepository {

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param trspInstructionIds 輸送計画明細項目検索
     * @return 輸送計画明細項目
     */
    List<TrspPlanLineItem> findAllByTrspInstructionIdIn(List<String> trspInstructionIds);

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param id 輸送計画明細項目検索
     * @return 輸送計画明細項目
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
