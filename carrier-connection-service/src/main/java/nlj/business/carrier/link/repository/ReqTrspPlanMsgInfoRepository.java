package nlj.business.carrier.link.repository;

import java.math.BigDecimal;
import java.util.List;
import nlj.business.carrier.link.domain.ReqTrspPlanMsgInfo;
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
public interface ReqTrspPlanMsgInfoRepository extends JpaRepository<ReqTrspPlanMsgInfo, Long> {

    /**
     * メッセージIDで検索.<BR>
     *
     * @param msgId メッセージID
     * @return 輸送計画メッセージ情報
     */
    ReqTrspPlanMsgInfo findByMsgId(BigDecimal msgId);

    /**
     * メッセージIDのリストで検索.<BR>
     *
     * @param ids メッセージIDのリスト
     * @return 輸送計画メッセージ情報
     */
    List<ReqTrspPlanMsgInfo> findAllByIdIn(List<Long> ids);
}
