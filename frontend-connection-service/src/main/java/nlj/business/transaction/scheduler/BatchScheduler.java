package nlj.business.transaction.scheduler;

import nlj.business.transaction.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * バッチジョブのスケジューラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
public class BatchScheduler {

    @Autowired
    private final BatchService batchService;

    public BatchScheduler(BatchService batchService) {
        this.batchService = batchService;
    }

    //    @Scheduled(cron = "0 1 0 * * *") // 毎日00:01:00に実行される
    public void runMarketPriceStatisticsBatch() {
        System.out.println("バッチジョブは00:01:00に開始されました");
        try {
            batchService.calculateMarketPriceStatistics();
            System.out.println("バッチジョブが正常に完了しました！");
        } catch (Exception e) {
            System.err.println("バッチジョブが失敗しました：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
