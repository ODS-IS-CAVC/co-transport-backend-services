package nlj.business.shipper.repository;

import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画リポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

    TransportPlan getTransportPlanById(Long id);

    Page<TransportPlan> findAllByShipperOperator(ShipperOperator shipperOperator, Pageable pageable);
}