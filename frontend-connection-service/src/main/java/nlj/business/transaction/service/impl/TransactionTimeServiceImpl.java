package nlj.business.transaction.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.repository.TransactionTimeCustomRepository;
import nlj.business.transaction.service.TransactionTimeService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * トランザクション時間サービスインプル。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransactionTimeServiceImpl implements TransactionTimeService {

    private final TransactionTimeCustomRepository transactionTimeCustomRepository;

    /**
     * 指定されたターゲットの最大更新日を取得する。
     *
     * @param target ターゲット
     * @return 最大更新日
     */
    @Override
    public String getTransactionTime(String target) {
        return transactionTimeCustomRepository.getMaxUpdatedDate(target);
    }

    /**
     * 指定されたターゲットと最終更新日から更新されたIDのリストを取得する。
     *
     * @param target     ターゲット
     * @param lastUpdate 最終更新日
     * @return 更新されたIDのリスト
     */
    @Override
    public List<Long> getUpdatedIds(String target, String lastUpdate) {
        return transactionTimeCustomRepository.getUpdatedIds(target, lastUpdate);
    }
}
