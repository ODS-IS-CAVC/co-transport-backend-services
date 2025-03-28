package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送状況リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IShipmentStatusRepository extends JpaRepository<IShipmentStatus, String> {

}
