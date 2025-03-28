package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
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
public interface ReqTrspPlanLineItemRepository extends JpaRepository<ReqTrspPlanLineItem, Long>,
    ReqTrspPlanLineItemCustomRepository {

    /**
     * 指定された条件に基づいて、輸送計画明細項目を検索する。
     *
     * @param operatorId        オペレータID
     * @param trspInstructionId 輸送指示ID
     * @param cnsgPrtyHeadOffId コンセンサスプロパティヘッドオフィスID
     * @param cnsgPrtyBrncOffId コンセンサスプロパティブランチオフィスID
     * @return 検索結果
     */
    ReqTrspPlanLineItem findByOperatorIdAndTrspInstructionIdAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffId(
        String operatorId,
        String trspInstructionId,
        String cnsgPrtyHeadOffId,
        String cnsgPrtyBrncOffId
    );
}
