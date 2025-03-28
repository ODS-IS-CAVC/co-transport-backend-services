package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.CutOffInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 切断情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CutOffInfoRepository extends JpaRepository<CutOffInfo, Long> {

    /**
     * 車両アビリティリソースで検索.<BR>
     *
     * @param vehicleAvailabilityResource 車両アビリティリソース
     * @return 切断情報
     */
    List<CutOffInfo> findCutOffInfoByVehicleAvailabilityResource(
        VehicleAvailabilityResource vehicleAvailabilityResource);
}
