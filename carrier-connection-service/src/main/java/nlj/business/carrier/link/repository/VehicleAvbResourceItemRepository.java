package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両稼働資源項目リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleAvbResourceItemRepository extends JpaRepository<VehicleAvbResourceItem, Long> {

    /**
     * 車両稼働資源ID検索.<BR>
     *
     * @param id 車両稼働資源ID
     * @return 車両稼働資源項目
     */
    List<VehicleAvbResourceItem> findByVehicleAvailabilityResourceId(Long id);

    /**
     * 車両稼働資源ID検索.<BR>
     *
     * @param id 車両稼働資源ID
     * @return 車両稼働資源項目
     */
    List<VehicleAvbResourceItem> findByVehicleDiagramId(Long id);

    /**
     * 車両稼働資源ID検索.<BR>
     *
     * @param transType 車両稼働資源ID
     * @return 車両稼働資源項目
     */
    List<VehicleAvbResourceItem> findByTransTypeAndTransOrderIdAndStatusIn(Integer transType, Long transOrderId,
        List<Integer> status);
}
