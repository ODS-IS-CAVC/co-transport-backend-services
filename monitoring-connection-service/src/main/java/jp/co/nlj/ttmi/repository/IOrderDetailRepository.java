package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 注文明細リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IOrderDetailRepository extends JpaRepository<IOrderDetail, String> {

}
