package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両稼働資源リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleAvailabilityResourceRepository extends JpaRepository<VehicleAvailabilityResource, Long> {

    /**
     * 車両稼働資源検索.<BR>
     *
     * @param carInfo 車両稼働資源検索
     * @return 車両稼働資源
     */
    List<VehicleAvailabilityResource> findByCarInfo(CarInfo carInfo);

}