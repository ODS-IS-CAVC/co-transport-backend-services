package jp.co.nlj.ix.repository;

import java.math.BigDecimal;
import java.util.List;
import jp.co.nlj.ix.domain.TrspPlanMsgInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 輸送計画メッセージ情報リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrspPlanMsgInfoRepository extends JpaRepository<TrspPlanMsgInfo, Long> {

    /**
     * 指定されたメッセージIDに基づいて輸送計画メッセージ情報を取得する。
     *
     * @param msgId メッセージID
     * @return 輸送計画メッセージ情報
     */
    TrspPlanMsgInfo findByMsgId(BigDecimal msgId);

    /**
     * 指定されたIDのリストに基づいてすべての輸送計画メッセージ情報を取得する。
     *
     * @param ids IDのリスト
     * @return 輸送計画メッセージ情報のリスト
     */
    List<TrspPlanMsgInfo> findAllByIdIn(List<Long> ids);
}
