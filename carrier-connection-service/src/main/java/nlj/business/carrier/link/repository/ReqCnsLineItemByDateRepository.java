package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.ReqCnsLineItemByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 貨物明細リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqCnsLineItemByDateRepository extends JpaRepository<ReqCnsLineItemByDate, Long> {

}
