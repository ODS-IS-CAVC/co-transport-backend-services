package jp.co.nlj.ix.repository;

import java.util.List;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 輸送計画アイテムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvailabilityResourceRepository extends JpaRepository<VehicleAvailabilityResource, Long> {

    List<VehicleAvailabilityResource> findAllByCarInfoIdAndOperatorId(Long id, String operatorId);
}
