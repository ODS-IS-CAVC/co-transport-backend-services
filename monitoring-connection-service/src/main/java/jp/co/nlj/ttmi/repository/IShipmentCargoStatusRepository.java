package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IShipmentCargoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 出荷貨物状態リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Repository
public interface IShipmentCargoStatusRepository extends JpaRepository<IShipmentCargoStatus, String> {

}
