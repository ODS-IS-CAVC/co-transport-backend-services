package nlj.business.transaction.repository;

import nlj.business.transaction.domain.trans.TransMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランザクションマーケットリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransMarketRepository extends JpaRepository<TransMarket, Long> {

}
