package nlj.business.transaction.service;


import java.util.List;
import nlj.business.transaction.dto.TransMarketDTO;

/**
 * <PRE>
 * 取引市場サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMarketService {

    /**
     * 指定された月における市場価格を検索する。
     *
     * @param month 月
     * @return 市場価格のリスト
     */
    List<TransMarketDTO> search(String month);
}
