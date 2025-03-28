package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 注文情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IOrderInfoRepository extends JpaRepository<IOrderInfo, String> {

    IOrderInfo getFirstByOrderByManagementNumberDesc();
}
