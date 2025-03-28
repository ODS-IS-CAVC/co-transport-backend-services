package nlj.business.carrier.link.service;

/**
 * <PRE>
 * トランザクションサービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransactionService {

    /**
     * ステータスを更新.<BR>
     *
     * @param shipperId シッパーID
     * @param carrierId キャリアID
     * @param status    ステータス
     */
    void updateStatusTransaction(Long shipperId, Long carrierId, Integer status);
}
