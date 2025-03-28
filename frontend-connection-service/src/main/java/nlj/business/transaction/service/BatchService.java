package nlj.business.transaction.service;

import nlj.business.transaction.domain.trans.TransMarket;

/**
 * <PRE>
 * バッチ処理を実行するサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface BatchService {

    /**
     * 市場価格統計を計算する。
     *
     * @return 市場価格統計
     */
    TransMarket calculateMarketPriceStatistics();
}
