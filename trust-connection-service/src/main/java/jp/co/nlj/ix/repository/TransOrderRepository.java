package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.shipper.TransOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送注文リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderRepository extends JpaRepository<TransOrder, Long> {

}
