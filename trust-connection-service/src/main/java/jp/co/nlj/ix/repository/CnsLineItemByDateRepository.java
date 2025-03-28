package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.CnsLineItemByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運行計画明細リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CnsLineItemByDateRepository extends JpaRepository<CnsLineItemByDate, Long> {

}
