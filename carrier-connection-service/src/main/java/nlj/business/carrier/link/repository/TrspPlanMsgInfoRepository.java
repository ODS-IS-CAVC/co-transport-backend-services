package nlj.business.carrier.link.repository;

import java.math.BigDecimal;
import java.util.List;
import nlj.business.carrier.link.domain.TrspPlanMsgInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画メッセージ情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanMsgInfoRepository extends JpaRepository<TrspPlanMsgInfo, Long> {

    /**
     * メッセージID検索.<BR>
     *
     * @param msgId メッセージID
     * @return 輸送計画メッセージ情報
     */
    TrspPlanMsgInfo findByMsgId(BigDecimal msgId);

    /**
     * メッセージID検索.<BR>
     *
     * @param ids メッセージID
     * @return 輸送計画メッセージ情報
     */
    List<TrspPlanMsgInfo> findAllByIdIn(List<Long> ids);
}
