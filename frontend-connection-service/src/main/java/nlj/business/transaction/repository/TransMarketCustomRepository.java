package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.trans.TransMarket;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランザクションマーケットリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransMarketCustomRepository {

    List<TransMarket> search(String month);
}
