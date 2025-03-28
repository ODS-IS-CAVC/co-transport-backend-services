package nlj.business.transaction.service;

import java.util.List;

/**
 * <PRE>
 * トランザクション時間サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransactionTimeService {

    /**
     * 指定されたターゲットのトランザクション時間を取得する。
     *
     * @param target ターゲット
     * @return トランザクション時間
     */
    String getTransactionTime(String target);

    /**
     * 指定されたターゲットと最終更新日時に基づいて更新されたIDのリストを取得する。
     *
     * @param target      ターゲット
     * @param lastUpdated 最終更新日時
     * @return 更新されたIDのリスト
     */
    List<Long> getUpdatedIds(String target, String lastUpdated);
}
