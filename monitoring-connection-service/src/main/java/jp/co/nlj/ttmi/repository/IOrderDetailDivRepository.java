package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IOrderDetailDiv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 注文明細区分リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IOrderDetailDivRepository extends JpaRepository<IOrderDetailDiv, String> {

    IOrderDetailDiv findFirstByOrderByOrderDetailDivKeyDesc();
}
