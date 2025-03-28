package nlj.business.transaction.repository;

import java.util.List;

/**
 * <PRE>
 * トランザクション時間のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransactionTimeCustomRepository {

    String getMaxUpdatedDate(String target);

    List<Long> getUpdatedIds(String target, String lastUpdate);
}
