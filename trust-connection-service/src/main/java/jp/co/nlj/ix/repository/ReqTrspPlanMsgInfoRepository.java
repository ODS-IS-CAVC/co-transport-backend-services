package jp.co.nlj.ix.repository;

import java.math.BigDecimal;
import java.util.List;
import jp.co.nlj.ix.domain.ReqTrspPlanMsgInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 輸送計画メッセージ情報リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ReqTrspPlanMsgInfoRepository extends JpaRepository<ReqTrspPlanMsgInfo, Long> {

    /**
     * 指定されたメッセージIDに基づいて輸送計画メッセージ情報を取得する。
     *
     * @param msgId メッセージID
     * @return 輸送計画メッセージ情報
     */
    ReqTrspPlanMsgInfo findByMsgId(BigDecimal msgId);

    /**
     * 指定されたIDのリストに基づいてすべての輸送計画メッセージ情報を取得する。
     *
     * @param ids IDのリスト
     * @return 輸送計画メッセージ情報のリスト
     */
    List<ReqTrspPlanMsgInfo> findAllByIdIn(List<Long> ids);
}
