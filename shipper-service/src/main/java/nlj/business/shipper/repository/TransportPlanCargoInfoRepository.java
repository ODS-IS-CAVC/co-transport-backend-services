package nlj.business.shipper.repository;

import java.util.List;
import nlj.business.shipper.domain.TransportPlanCargoInfo;
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
public interface TransportPlanCargoInfoRepository extends JpaRepository<TransportPlanCargoInfo, Long> {

    TransportPlanCargoInfo getTransportPlanById(Long id);

    List<TransportPlanCargoInfo> findByTransportPlanId(Long transportPlanId);

    void deleteAllByTransportPlanId(Long transportPlanId);

    TransportPlanCargoInfo findFirstByCargoInfoIdOrderByIdDesc(Long cargoInfoId);

    List<TransportPlanCargoInfo> findByCargoInfoIdInOrderByIdDesc(List<Long> cargoInfoIds);
}