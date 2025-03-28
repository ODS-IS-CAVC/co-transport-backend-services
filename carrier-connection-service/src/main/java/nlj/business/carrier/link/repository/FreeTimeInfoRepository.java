package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.FreeTimeInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 空き時間情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface FreeTimeInfoRepository extends JpaRepository<FreeTimeInfo, Long> {

    /**
     * 車両アビリティリソースで検索.<BR>
     *
     * @param vehicleAvailabilityResource 車両アビリティリソース
     * @return 空き時間情報
     */
    List<FreeTimeInfo> findCutOffInfoByVehicleAvailabilityResource(
        VehicleAvailabilityResource vehicleAvailabilityResource);
}
