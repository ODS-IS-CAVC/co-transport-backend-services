package nlj.business.shipper.repository;

import nlj.business.shipper.domain.ShipperOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * シッパーコーポレートリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipperOperatorRepository extends JpaRepository<ShipperOperator, String> {

    ShipperOperator getShipperOperatorById(String id);

    @Query("SELECT MAX(operatorCode) FROM ShipperOperator")
    Long getLastOperatorCode();
} 