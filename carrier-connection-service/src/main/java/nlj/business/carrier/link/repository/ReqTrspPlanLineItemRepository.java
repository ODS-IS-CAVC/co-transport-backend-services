package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.ReqTrspPlanLineItem;
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
public interface ReqTrspPlanLineItemRepository extends JpaRepository<ReqTrspPlanLineItem, Long>,
    ReqTrspPlanLineItemCustomRepository {

    /**
     * 運送指示ID、コンググレーションプロパティ頭支店ID、コンググレーションプロパティ支店IDで検索.<BR>
     *
     * @param operatorId        運送指示ID
     * @param trspInstructionId 運送指示ID
     * @param cnsgPrtyHeadOffId コンググレーションプロパティ頭支店ID
     * @param cnsgPrtyBrncOffId コンググレーションプロパティ支店ID
     * @return 輸送計画明細
     */
    ReqTrspPlanLineItem findByOperatorIdAndTrspInstructionIdAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffId(
        String operatorId,
        String trspInstructionId,
        String cnsgPrtyHeadOffId,
        String cnsgPrtyBrncOffId
    );
}
